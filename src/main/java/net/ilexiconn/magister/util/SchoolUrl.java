/*
 * Copyright (c) 2015 iLexiconn
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.util;

import net.ilexiconn.magister.container.School;

import java.io.Serializable;

public class SchoolUrl implements Serializable {
    private School school;

    public SchoolUrl(School school) {
        this.school = school;
    }

    public String getMagisterUrl() {
        return school.url + "/";
    }

    public String getApiUrl() {
        return getMagisterUrl() + "api/";
    }

    public String getVersionUrl() {
        return getApiUrl() + "versie/";
    }

    public String getSessionUrl() {
        return getApiUrl() + "sessies/";
    }

    public String getCurrentSessionUrl() {
        return getSessionUrl() + "huidige/";
    }

    public String getAccountUrl() {
        return getApiUrl() + "account/";
    }

    public String getStudiesUrl(int profileId) {
        return getApiUrl() + "personen/" + profileId + "/aanmeldingen";
    }
}
