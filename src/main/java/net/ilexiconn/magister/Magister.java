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
import net.ilexiconn.magister.adapter.sub.*;
import net.ilexiconn.magister.container.*;
import net.ilexiconn.magister.container.sub.*;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Magister {
    public CloseableHttpClient httpClient = HttpClients.createDefault();
    public Gson gson;

    private School school;
    private String username;
    private String password;

    private Version version;
    private Session session;
    private Profile profile;
    private Study[] studies;
    private Study currentStudy;

    public Magister(School school, String username, String password) {
        if (school != null) setSchool(school);
        if (username != null && password != null) setUser(username, password);

        gson = new GsonBuilder()
                .registerTypeAdapter(Link[].class, new LinkAdapter(this))
                .registerTypeAdapter(Contact[].class, new ContactAdapter(this))
                .registerTypeAdapter(Subject[].class, new SubjectAdapter(this))
                .registerTypeAdapter(Classroom[].class, new ClassroomAdapter(this))
                .registerTypeAdapter(Group.class, new GroupAdapter(this))
                .registerTypeAdapter(Homework[].class, new HomeworkAdapter(this))
                .registerTypeAdapter(MarkPeriod.class, new MarkPeriodAdapter(this))
                .registerTypeAdapter(MarkColumn.class, new MarkColumnAdapter(this))
                .registerTypeAdapter(Mark[].class, new MarkAdapter(this))
                .registerTypeAdapter(SubStudy.class, new SubStudyAdapter(this))
                .registerTypeAdapter(Study[].class, new StudyAdapter(this))
                .registerTypeAdapter(Privilege[].class, new PrivilegeAdapter(this))
                .registerTypeAdapter(Profile.class, new ProfileAdapter(this))
                .create();
    }

    public Magister(String username, String password) {
        this(null, username, password);
    }

    public Magister(School school) {
        this(school, null, null);
    }

    public Magister() {
        this(null, null, null);
    }

    public static School[] findSchool(String s) {
        try {
            return new Gson().fromJson(new InputStreamReader(new URL("https://mijn.magister.net/api/schools?filter=" + s).openStream()), School[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return new School[0];
        }
    }

    public void setUser(String u, String p) {
        username = u;
        password = p;
    }

    public void setSchool(School s) {
        school = s;
    }

    public void login() throws Exception {
        if (school != null && !username.isEmpty() && !password.isEmpty()) {
            version = gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/versie")), Version.class);
            System.out.println("Connecting to Magister version " + version.getProductVersion() + "...");
            HttpDelete delete = new HttpDelete(school.getUrl() + "/api/sessies/huidige");
            delete.addHeader(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
            httpClient.execute(delete);
            HttpPost post = new HttpPost(school.getUrl() + "/api/sessies");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("Gebruikersnaam", username));
            nvps.add(new BasicNameValuePair("Wachtwoord", password));
            post.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse responsePost = httpClient.execute(post);
            session = gson.fromJson(new InputStreamReader(responsePost.getEntity().getContent()), Session.class);
            if (!session.isVerified() || !session.getState().equals("active")) {
                System.err.println("Invalid session, check credentials.");
                return;
            }
            profile = gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/account")), Profile.class);
            studies = gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/aanmeldingen")), Study[].class);
            DateFormat format = new SimpleDateFormat("Y-m-d");
            Date now = new Date();
            for (Study study : studies) {
                if (format.parse(study.endDate.substring(0, 10)).after(now)) {
                    currentStudy = study;
                }
            }
        }
    }

    public Version getVersion() {
        return version;
    }

    public Session getSession() {
        return session;
    }

    public Profile getProfile() {
        return profile;
    }

    public Study[] getStudies() {
        return studies;
    }

    public Study getCurrentStudy() {
        return currentStudy;
    }

    public Mark[] getMarks() throws IOException {
        return getMarks(null);
    }

    public Mark[] getMarks(String subject) throws IOException {
        if (session == null) return null;
        Mark[] marks = gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/aanmeldingen/" + currentStudy.getId() + "/cijfers/cijferoverzichtvooraanmelding?actievePerioden=" + true + "&alleenBerekendeKolommen=" + false + "&alleenPTAKolommen=" + false)), Mark[].class);
        if (subject == null) return marks;
        List<Mark> itemsList = new ArrayList<>();
        for (Mark item : marks) {
            if (item.getSubject(this).abbreviation.equals(subject)) itemsList.add(item);
        }
        return itemsList.toArray(new Mark[itemsList.size()]);
    }

    public BufferedImage getImage() throws IOException {
        return getImage(42, 64, false);
    }

    public BufferedImage getImage(int width, int height, boolean crop) throws IOException {
        if (session == null) return null;
        return ImageIO.read(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/foto" + (width != 42 || height != 64 || crop ? "?width=" + width + "&height=" + height + "&crop=" + crop : "")));
    }

    public Homework[] getHomework() throws IOException {
        return getHomework(null, null);
    }

    public Homework[] getHomework(Calendar from, Calendar to) throws IOException {
        if (session == null) return null;
        SimpleDateFormat format = new SimpleDateFormat("Y-m-d");
        return gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/afspraken" + (from == null || to == null ? "" : "?van=" + format.format(from.getTime()) + "&tot=" + format.format(to.getTime())))), Homework[].class);
    }

    public Subject[] getSubjects() throws IOException {
        if (session == null) return null;
        return gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/aanmeldingen/" + currentStudy.getId() + "/vakken")), Subject[].class);
    }

    public Contact[] getTeacherInfo(String name) throws IOException {
        if (session == null || name == null || name.isEmpty()) return null;
        return gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/contactpersonen?contactPersoonType=Personeel&q=" + name)), Contact[].class);
    }

    public Contact[] getPupilInfo(String name) throws IOException {
        if (session == null || name == null || name.isEmpty()) return null;
        return gson.fromJson(new InputStreamReader(getInputStream(school.getUrl() + "/api/personen/" + profile.id + "/contactpersonen?contactPersoonType=Leerling&q=" + name)), Contact[].class);
    }

    private InputStream getInputStream(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse responseGet = httpClient.execute(get);
        return responseGet.getEntity().getContent();
    }
}
