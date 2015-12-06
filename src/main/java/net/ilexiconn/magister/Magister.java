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
import net.ilexiconn.magister.handler.*;
import net.ilexiconn.magister.util.AndroidUtil;
import net.ilexiconn.magister.util.HttpUtil;
import net.ilexiconn.magister.util.LogUtil;
import org.apache.http.auth.InvalidCredentialsException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The main API class. You can get a new instance by running {@link Magister#login(School, String, String)}.
 *
 * @since 0.1.0
 * @author iLexiconn
 */
public class Magister {
    public static final String VERSION = "0.1.0-SNAPSHOT";

    public Gson gson = new GsonBuilder()
            .registerTypeAdapter(Profile.class, new ProfileAdapter())
            .registerTypeAdapter(Study[].class, new StudyAdapter())
            .create();

    public School school;

    public Version version;
    public Session session;
    public Profile profile;
    public Study[] studies;
    public Study currentStudy;

    private List<IHandler> handlerList = new ArrayList<IHandler>();

    private Magister() {
        handlerList.add(new GradeHandler(this));
        handlerList.add(new PresenceHandler(this));
        handlerList.add(new ContactHandler(this));
        handlerList.add(new MessageHandler(this));
        handlerList.add(new AppointmentHandler(this));
        handlerList.add(new ELOHandler(this));
    }

    /**
     * Create a new {@link Magister} instance by logging in. Will return null if login fails.
     *
     * @param school the {@link School} instance. Can't be null.
     * @param username the username of the profile. Can't be null.
     * @param password the password of the profile. Can't be null.
     * @return the new {@link Magister} instance, null if login fails.
     * @throws IOException if there is no active internet connection.
     * @throws ParseException if parsing the date fails.
     * @throws InvalidParameterException if one of the arguments is null.
     */
    public static Magister login(School school, String username, String password) throws IOException, ParseException, InvalidParameterException {
        if (school == null || username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidParameterException("Parameters can't be null or empty!");
        }
        Magister magister = new Magister();
        AndroidUtil.checkAndroid();
        magister.school = school;
        magister.version = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/versie"), Version.class);
        HttpUtil.httpDelete(school.url + "/api/sessies/huidige");
        Map<String, String> nameValuePairMap = new HashMap<String, String>();
        nameValuePairMap.put("Gebruikersnaam", username);
        nameValuePairMap.put("Wachtwoord", password);
        magister.session = magister.gson.fromJson(HttpUtil.httpPost(school.url + "/api/sessies", nameValuePairMap), Session.class);
        if (!magister.session.state.equals("active")) {
            LogUtil.printError("Invalid credentials", new InvalidCredentialsException());
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

    /**
     * Check if this user has the following privilege.
     *
     * @param privilege the privilege name.
     * @return true if the profile has the privilege.
     */
    public boolean hasPrivilege(String privilege) {
        for (Privilege p : profile.privileges) {
            if (p.name.equals(privilege)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the current profile picture in the default size.
     *
     * @return the current profile picture in the default size.
     * @throws IOException if there is no active internet connection.
     */
    public BufferedImage getImage() throws IOException {
        return getImage(42, 64, false);
    }

    /**
     * Get the current profile picture.
     *
     * @param width the width.
     * @param height the height.
     * @param crop true if not in default ratio.
     * @return the current profile picture.
     * @throws IOException if there is no active internet connection.
     */
    public BufferedImage getImage(int width, int height, boolean crop) throws IOException {
        HttpGet get = new HttpGet(school.url + "/api/personen/" + profile.id + "/foto" + (width != 42 || height != 64 || crop ? "?width=" + width + "&height=" + height + "&crop=" + crop : ""));
        CloseableHttpResponse responseGet = HttpUtil.getHttpClient().execute(get);
        return ImageIO.read(responseGet.getEntity().getContent());
    }

    /**
     * Change the password of the current profile.
     *
     * @param oldPassword the current password.
     * @param newPassword the new password.
     * @param newPassword2 the new password.
     * @return a String with the response. 'Successful' if the password changed successfully.
     * @throws IOException if there is no active internet connection.
     * @throws InvalidParameterException if one of the parameters is null or empty, or when the two new passwords aren't
     * the same.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public String changePassword(String oldPassword, String newPassword, String newPassword2) throws IOException, InvalidParameterException, PrivilegeException {
        if (!hasPrivilege("WachtwoordWijzigen")) {
            throw new PrivilegeException();
        }
        if (oldPassword == null || oldPassword.isEmpty() || newPassword == null || newPassword.isEmpty() || newPassword2 == null || newPassword2.isEmpty()) {
            throw new InvalidParameterException("Parameters can't be null or empty!");
        } else if (!newPassword.equals(newPassword2)) {
            throw new InvalidParameterException("New passwords don't match!");
        }
        Map<String, String> nameValuePairMap = new HashMap<String, String>();
        nameValuePairMap.put("HuidigWachtwoord", oldPassword);
        nameValuePairMap.put("NieuwWachtwoord", newPassword);
        nameValuePairMap.put("PersoonId", profile.id + "");
        nameValuePairMap.put("WachtwoordBevestigen", newPassword2);
        Response response = gson.fromJson(HttpUtil.httpPost(school.url + "/api/personen/account/wachtwoordwijzigen?persoonId=" + profile.id, nameValuePairMap), Response.class);
        if (response == null) {
            return "Successful";
        } else {
            LogUtil.printError(response.message, new InvalidParameterException());
            return response.message;
        }
    }

    public <T extends IHandler> T getHandler(Class<T> type) throws PrivilegeException {
        for (IHandler handler : handlerList) {
            if (handler.getClass() == type) {
                if (!hasPrivilege(handler.getPrivilege())) {
                    throw new PrivilegeException();
                }
                return (T) handler;
            }
        }
        return null;
    }
}
