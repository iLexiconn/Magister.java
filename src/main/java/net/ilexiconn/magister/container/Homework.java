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
import net.ilexiconn.magister.container.sub.*;

import java.io.Serializable;

public class Homework implements Serializable, Cachable {
    public final int id;
    public final Link[] links;
    public final String startDate;
    public final String endDate;
    public final int classFrom;
    public final int classTo;
    public final boolean wholeDay;
    public final String description;
    public final String location;
    public final int status;
    public final int type;
    public final int displayType;
    //Inhoud?
    public final int infoType;
    //Aantekening?
    public final boolean finished;
    public final Subject[] subjects;
    public final Contact[] teachers;
    public final Classroom[] classrooms;
    public final Group[] groups;
    public final int homeworkId;
    public final boolean hasAttachment;
    //Bijlagen?

    public Homework(int i, Link[] l, String s, String e, int f, int t, boolean w, String d, String o, int a, int y, int p, int n, boolean h, Subject[] u, Contact[] c, Classroom[] r, Group[] g, int m, boolean b) {
        id = i;
        links = l;
        startDate = s;
        endDate = e;
        classFrom = f;
        classTo = t;
        wholeDay = w;
        description = d;
        location = o;
        status = a;
        type = y;
        displayType = p;
        infoType = n;
        finished = h;
        subjects = u;
        teachers = c;
        classrooms = r;
        groups = g;
        homeworkId = m;
        hasAttachment = b;
        ContainerCache.put(this, getClass());
    }

    public String getId() {
        return id + "";
    }
}
