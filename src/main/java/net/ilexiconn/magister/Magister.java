/*
 * Copyright (c) 2015 iLexiconn
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.ilexiconn.magister.adapter.*;
import net.ilexiconn.magister.container.*;
import net.ilexiconn.magister.container.sub.Privilege;
import net.ilexiconn.magister.exeption.PrivilegeException;
import net.ilexiconn.magister.util.AndroidUtil;
import net.ilexiconn.magister.util.HttpUtil;
import net.ilexiconn.magister.util.LogUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Magister {
    public static final String VERSION = "0.1.0-develop";

    public Gson gson = new GsonBuilder()
            .registerTypeAdapter(Profile.class, new ProfileAdapter())
            .registerTypeAdapter(Study[].class, new StudyAdapter())
            .registerTypeAdapter(Contact[].class, new ContactAdapter())
            .registerTypeAdapter(Appointment[].class, new AppointmentAdapter())
            .registerTypeAdapter(Presence[].class, new PresenceAdapter())
            .registerTypeAdapter(Grade[].class, new GradeAdapter())
            .registerTypeAdapter(MessageFolder[].class, new MessageFolderAdapter())
            .registerTypeAdapter(Message[].class, new MessageAdapter())
            .registerTypeAdapter(SingleMessage[].class, new SingleMessageAdapter())
            .create();

    public School school;

    public Version version;
    public Session session;
    public Profile profile;
    public Study[] studies;
    public Study currentStudy;

    public static Magister login(School school, String username, String password) throws Exception {
        Magister magister = new Magister();
        AndroidUtil.isRunningOnAndroid();
        magister.school = school;
        magister.version = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/versie"), Version.class);
        HttpUtil.httpDelete(school.url + "/api/sessies/huidige");
        HashMap<String, String> nameValuePairMap = new HashMap<String, String>();
        nameValuePairMap.put("Gebruikersnaam", username);
        nameValuePairMap.put("Wachtwoord", password);
        magister.session = magister.gson.fromJson(HttpUtil.httpPost(school.url + "/api/sessies", nameValuePairMap), Session.class);
        if (!magister.session.state.equals("active")) {
            LogUtil.printError("Invalid credentials", new InvalidParameterException());
            return null;
        }
        magister.profile = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/account"), Profile.class);
        magister.studies = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + magister.profile.id + "/aanmeldingen"), Study[].class);
        DateFormat format = new SimpleDateFormat("y-m-d", Locale.ENGLISH);
        Date now = new Date();
        for (Study study : magister.studies) {
            if (format.parse(study.endDate.substring(0, 10)).after(now)) {
                magister.currentStudy = study;
            }
        }
        return magister;
    }

    public boolean hasPrivilege(String privilege) {
        for (Privilege p : profile.privileges) {
            if (p.name.equals(privilege)) {
                return true;
            }
        }
        return false;
    }

    public Contact[] getPupilInfo(String name) throws IOException, PrivilegeException {
        return getContactInfo(name, "Leerling");
    }

    public Contact[] getTeacherInfo(String name) throws IOException, PrivilegeException {
        return getContactInfo(name, "Personeel");
    }

    public Contact[] getContactInfo(String name, String type) throws IOException, PrivilegeException {
        if (!hasPrivilege("Contactpersonen")) {
            throw new PrivilegeException();
        }
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/contactpersonen?contactPersoonType=" + type + "&q=" + name), Contact[].class);
    }

    public Appointment[] getAppointments(Date from, Date until) throws IOException, PrivilegeException {
        if (!hasPrivilege("Afspraken")) {
            throw new PrivilegeException();
        }
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateNow = format.format(from);
        String dateFrom = format.format(until);
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/afspraken?status=0&van=" + dateNow + "&tot=" + dateFrom), Appointment[].class);
    }

    public Appointment[] getAppointmentsOfToday() throws IOException, PrivilegeException {
        Date now = new Date();
        return getAppointments(now, now);
    }

    public Presence[] getPresence() throws IOException, PrivilegeException {
        if (!hasPrivilege("Absenties")) {
            throw new PrivilegeException();
        }
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/absenties?tot=2016-07-31&van=2015-08-01"), Presence[].class);
    }

    public Grade[] getGrades(boolean onlyAverage, boolean onlyPTA, boolean onlyActiveStudy) throws IOException, PrivilegeException {
        if (!hasPrivilege("Cijfers")) {
            throw new PrivilegeException();
        }
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/aanmeldingen/" + currentStudy.id + "/cijfers/cijferoverzichtvooraanmelding?alleenBerekendeKolommen=" + onlyAverage + "&alleenPTAKolommen=" + onlyPTA + "&actievePerioden=" + onlyActiveStudy), Grade[].class);
    }

    public Grade[] getAllGrades() throws IOException, PrivilegeException {
        return getGrades(false, false, false);
    }

    public MessageFolder[] getMessageFolders() throws IOException, PrivilegeException {
        if (!hasPrivilege("Berichten")) {
            throw new PrivilegeException();
        }
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/berichten/mappen"), MessageFolder[].class);
    }

    public Message[] getMessagesPerFolder(int folderID) throws IOException, PrivilegeException {
        if (!hasPrivilege("Berichten")) {
            throw new PrivilegeException();
        }
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/berichten?mapId=" + folderID + "&orderby=soort+DESC&skip=0&top=25"), Message[].class);
    }

    public SingleMessage[] getSingleMessage(int messageID) throws IOException, PrivilegeException {
        if (!hasPrivilege("Berichten")) {
            throw new PrivilegeException();
        }
        return gson.fromJson(HttpUtil.httpGet(school.url + "/api/personen/" + profile.id + "/berichten/" + messageID + "?berichtSoort=Bericht"), SingleMessage[].class);
    }

    public BufferedImage getImage() throws IOException {
        return getImage(42, 64, false);
    }

    public BufferedImage getImage(int width, int height, boolean crop) throws IOException {
        HttpGet get = new HttpGet(school.url + "/api/personen/" + profile.id + "/foto" + (width != 42 || height != 64 || crop ? "?width=" + width + "&height=" + height + "&crop=" + crop : ""));
        CloseableHttpResponse responseGet = HttpUtil.getHttpClient().execute(get);
        return ImageIO.read(responseGet.getEntity().getContent());
    }

//    public SingleMessage[] postSingleMessage() throws IOException {
//        TODO: Implement post
//        return SingleMessage;
//    }
}
