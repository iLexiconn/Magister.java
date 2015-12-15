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

package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Subject {
    @SerializedName("afkorting")
    public String abbreviation;

    @SerializedName("begindatum")
    public String startDateString;

    public Date startDate;

    @SerializedName("dispensatie")
    public boolean dispensation;

    @SerializedName("docent")
    public String teacher;

    @SerializedName("einddatum")
    public String endDateString;

    public Date endDate;

    @SerializedName("hogerNiveau")
    public boolean higherLevel;

    @SerializedName("id")
    public int id;

    @SerializedName("omschrijving")
    public String description;

    @SerializedName("studieId")
    public int studyId;

    @SerializedName("studieVakId")
    public int studySubjectId;

    @SerializedName("volgnr")
    public int followId;

    @SerializedName("vrijstelling")
    public boolean dispensation2;
}
