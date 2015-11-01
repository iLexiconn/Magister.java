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
import net.ilexiconn.magister.adapter.ProfileAdapter;
import net.ilexiconn.magister.container.Profile;
import net.ilexiconn.magister.container.School;
import net.ilexiconn.magister.container.Session;
import net.ilexiconn.magister.container.Version;
import net.ilexiconn.magister.util.HttpUtil;
import net.ilexiconn.magister.util.LogUtil;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public class Magister {
    public Gson gson = new GsonBuilder().registerTypeAdapter(Profile.class, new ProfileAdapter()).create();

    public School school;

    public Version version;
    public Session session;
    public Profile profile;

    public static Magister login(School school, String username, String password) throws IOException {
        Magister magister = new Magister();
        magister.school = school;
        magister.version = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/versie"), Version.class);
        HttpUtil.httpDelete(school.url + "/api/sessies/huidige");
        List<NameValuePair> nameValuePairList = new ArrayList<>();
        nameValuePairList.add(new BasicNameValuePair("Gebruikersnaam", username));
        nameValuePairList.add(new BasicNameValuePair("Wachtwoord", password));
        magister.session = magister.gson.fromJson(HttpUtil.httpPost(school.url + "/api/sessies", nameValuePairList), Session.class);
        if (!magister.session.isVerified || !magister.session.state.equals("active")) {
            LogUtil.printError("Invalid credentials", new InvalidParameterException());
            return null;
        }
        magister.profile = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/account"), Profile.class);
        return magister;
    }
}
