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
import net.ilexiconn.magister.container.sub.*;

import java.io.Serializable;

public class Homework implements Serializable, Cachable {
    private int Id;
    private Link[] Links;
    private String Start;
    private String Einde;
    private int LesuurVan;
    private int LesuurTotMet;
    private boolean DuurtHeleDag;
    private String Omschrijving;
    private String Lokatie;
    private int Status;
    private int Type;
    private int WeergaveType;
    //Inhoud?
    private int InfoType;
    //Aantekening?
    private boolean Afgerond;
    private Subject[] Vakken;
    private Teacher[] Docenten;
    private Classroom[] Lokalen;
    private Group[] Groepen;
    private int OpdrachtId;
    private boolean HeeftBijlagen;
    //Bijlagen?

    public Homework(int i, Link[] l, String s, String e, int f, int t, boolean w, String d, String o, int a, int y, int p, int n, boolean h, Subject[] u, Teacher[] c, Classroom[] r, Group[] g, int m, boolean b) {
        Id = i;
        Links = l;
        Start = s;
        Einde = e;
        LesuurVan = f;
        LesuurTotMet = t;
        DuurtHeleDag = w;
        Omschrijving = d;
        Lokatie = o;
        Status = a;
        Type = y;
        WeergaveType = p;
        InfoType = n;
        Afgerond = h;
        Vakken = u;
        Docenten = c;
        Lokalen = r;
        Groepen = g;
        OpdrachtId = m;
        HeeftBijlagen = b;
    }

    public int getId() {
        return Id;
    }

    public Link[] getLinks() {
        return Links;
    }

    public String getStartDate() {
        return Start;
    }

    public String getEndDate() {
        return Einde;
    }

    public int getClassFrom() {
        return LesuurVan;
    }

    public int getClassTo() {
        return LesuurTotMet;
    }

    public boolean isWholeDay() {
        return DuurtHeleDag;
    }

    public String getDescription() {
        return Omschrijving;
    }

    public String getLocation() {
        return Lokatie;
    }

    public int getStatus() {
        return Status;
    }

    public int getType() {
        return Type;
    }

    public int getDisplayType() {
        return WeergaveType;
    }

    public int getInfoType() {
        return InfoType;
    }

    public boolean isFinished() {
        return Afgerond;
    }

    public Subject[] getSubjects() {
        return Vakken;
    }

    public Teacher[] getTeachers() {
        return Docenten;
    }

    public Classroom[] getClassrooms() {
        return Lokalen;
    }

    public Group[] getGroups() {
        return Groepen;
    }

    public int getHomeworkId() {
        return OpdrachtId;
    }

    public boolean hasAttachments() {
        return HeeftBijlagen;
    }
}
