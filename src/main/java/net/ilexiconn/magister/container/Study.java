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
import net.ilexiconn.magister.container.sub.Group;
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.container.sub.SubStudy;

import java.io.Serializable;

public class Study implements Serializable, Cachable {
    public final int id;
    public final Link[] links;
    public final int pupilId;
    public final String startDate;
    public final String endDate;
    public final String classPeriod;
    public final SubStudy study;
    public final Group group;

    public Study(int i, Link[] l, int p, String s, String e, String c, SubStudy t, Group g) {
        id = i;
        links = l;
        pupilId = p;
        startDate = s;
        endDate = e;
        classPeriod = c;
        study = t;
        group = g;
        ContainerCache.put(this, getClass());
    }

    public String getId() {
        return id + "";
    }
}
