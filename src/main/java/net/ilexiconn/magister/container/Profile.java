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

package net.ilexiconn.magister.container;

import net.ilexiconn.magister.cache.Cachable;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.sub.Privilege;

import java.io.Serializable;

public class Profile implements Serializable, Cachable {
    public final int id;
    public final String nickname;
    public final String surnamePrefix;
    public final String surname;
    public final String officialFirstNames;
    public final String initials;
    public final String officialSurnamePrefixes;
    public final String officialSurname;
    public final String dateOfBirth;
    public final String birthSurname;
    public final String birthSurnamePrefix;
    public final boolean useBirthName;
    public final Privilege[] privileges;

    public Profile(int i, String n, String s, String u, String f, String t, String p, String r, String d, String b, String h, boolean e, Privilege[] v) {
        id = i;
        nickname = n;
        surnamePrefix = s;
        surname = u;
        officialFirstNames = f;
        initials = t;
        officialSurnamePrefixes = p;
        officialSurname = r;
        dateOfBirth = d;
        birthSurname = b;
        birthSurnamePrefix = h;
        useBirthName = e;
        privileges = v;
        ContainerCache.put(this, getClass());
    }

    public String getId() {
        return id + "";
    }
}
