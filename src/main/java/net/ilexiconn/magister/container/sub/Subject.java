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

public class Subject implements Serializable, Cachable {
    public final int id;
    public final int subjectId;
    public final int studyId;
    public final String abbreviation;
    public final String description;
    public final boolean exemption;
    public final boolean dispensation;
    public final int followId;
    public final String teacher;
    public final String startDate;
    public final String endDate;

    public Subject(int i, int s, int t, String a, String d, boolean e, boolean p, int f, String c, String r, String n) {
        id = i;
        subjectId = s;
        studyId = t;
        abbreviation = a;
        description = d;
        exemption = e;
        dispensation = p;
        followId = f;
        teacher = c;
        startDate = r;
        endDate = n;

        ContainerCache.put(this, getClass());
    }

    public String getId() {
        return description;
    }
}