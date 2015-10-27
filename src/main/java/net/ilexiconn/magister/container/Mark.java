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

import com.google.gson.JsonObject;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.Cachable;
import net.ilexiconn.magister.container.sub.MarkColumn;
import net.ilexiconn.magister.container.sub.MarkPeriod;
import net.ilexiconn.magister.container.sub.Subject;

import java.io.Serializable;

public class Mark implements Serializable, Cachable {
    public final int id;
    public final String mark;
    public final boolean sufficient;
    public final String date;
    public final MarkPeriod markPeriod;
    private final JsonObject subject;
    public final boolean canCatch;
    public final boolean hasExemption;
    public final boolean counts;
    public final MarkColumn markColumn;
    public final int eloId;
    public final String teacher;
    public final boolean dispensation;
    public final boolean subjectExemption;

    public Mark(int i, String n, boolean s, String d, MarkPeriod p, JsonObject u, boolean c, boolean e, boolean o, MarkColumn l, int m, String t, boolean a, boolean j) {
        id = i;
        mark = n;
        sufficient = s;
        date = d;
        markPeriod = p;
        subject = u;
        canCatch = c;
        hasExemption = e;
        counts = o;
        markColumn = l;
        eloId = m;
        teacher = t;
        dispensation = a;
        subjectExemption = j;
    }

    public Subject getSubject(Magister magister) {
        return magister.gson.getAdapter(Subject[].class).fromJsonTree(subject)[0];
    }

    public String getId() {
        return id + "";
    }
}
