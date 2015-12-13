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

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.reflect.TypeToken;
import net.ilexiconn.magister.container.*;
import net.ilexiconn.magister.util.AndroidUtil;
import net.ilexiconn.magister.util.HttpUtil;
import net.ilexiconn.magister.util.LogUtil;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

/**
 * A special Magister class implementing {@link Parcelable].
 * {@inheritDoc}
 */
public class ParcelableMagister extends Magister implements Parcelable {
    public static final Creator<ParcelableMagister> CREATOR = new Creator<ParcelableMagister>() {
        @Override
        public ParcelableMagister createFromParcel(Parcel in) {
            return new ParcelableMagister(in);
        }

        @Override
        public ParcelableMagister[] newArray(int size) {
            return new ParcelableMagister[size];
        }
    };

    protected ParcelableMagister() {
        super();
    }

    protected ParcelableMagister(Parcel in) {
        super();
        school = (School) in.readSerializable();
        user = (User) in.readSerializable();
        version = (Version) in.readSerializable();
        session = (Session) in.readSerializable();
        profile = (Profile) in.readSerializable();
        currentStudy = (Study) in.readSerializable();
        int n = in.readInt();
        ArrayList<Study> studyList = new ArrayList<Study>();
        for (int i = 0; i != n; i++) {
            studyList.add((Study) in.readSerializable());
        }
        studies = studyList.toArray(new Study[studyList.size()]);
    }

    /**
     * Create a new {@link ParcelableMagister} instance by logging in. Will return null if login fails.
     *
     * @param school   the {@link School} instance. Can't be null.
     * @param username the username of the profile. Can't be null.
     * @param password the password of the profile. Can't be null.
     * @return the new {@link ParcelableMagister} instance, null if login fails.
     * @throws IOException               if there is no active internet connection.
     * @throws ParseException            if parsing the date fails.
     * @throws InvalidParameterException if one of the arguments is null.
     */
    public static ParcelableMagister login(School school, String username, String password) throws IOException, ParseException, InvalidParameterException {
        if (school == null || username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new InvalidParameterException("Parameters can't be null or empty!");
        }
        ParcelableMagister magister = new ParcelableMagister();
        AndroidUtil.checkAndroid();
        magister.school = school;
        magister.version = magister.gson.fromJson(HttpUtil.httpGet(school.url + "/api/versie"), Version.class);
        magister.user = new User(username, password, true);
        HttpUtil.httpDelete(school.url + "/api/sessies/huidige");
        Map<String, String> nameValuePairMap = magister.gson.fromJson(magister.gson.toJson(magister.user), new TypeToken<Map<String, String>>() {
        }.getType());
        magister.session = magister.gson.fromJson(HttpUtil.httpPost(school.url + "/api/sessies/huidige", nameValuePairMap), Session.class);
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(school);
        dest.writeSerializable(user);
        dest.writeSerializable(version);
        dest.writeSerializable(session);
        dest.writeSerializable(profile);
        dest.writeSerializable(currentStudy);
        dest.writeInt(studies.length);
        for (Study s : studies) {
            dest.writeSerializable(s);
        }
    }
}
