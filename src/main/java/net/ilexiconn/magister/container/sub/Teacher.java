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

package net.ilexiconn.magister.container.sub;

import net.ilexiconn.magister.cache.Cachable;
import net.ilexiconn.magister.cache.ContainerCache;

import java.io.Serializable;

public class Teacher implements Serializable, Cachable {
    public final int id;
    public final Link[] links;
    public final String surname;
    public final String firstName;
    public final String surnamePrefix;
    public final String fullName;
    public final int type;
    public final String code;

    public Teacher(int i, Link[] l, String s, String f, String p, String n, int t, String c) {
        id = i;
        links = l;
        surname = s;
        firstName = f;
        surnamePrefix = p;
        fullName = n;
        type = t;
        code = c;
        ContainerCache.put(this, getClass());
    }

    public String getId() {
        return code;
    }
}
