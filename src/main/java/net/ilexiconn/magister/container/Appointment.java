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

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.*;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.container.type.DisplayType;
import net.ilexiconn.magister.container.type.InfoType;

import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    @SerializedName("Id")
    public int id;

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("Start")
    public String startDateString;

    public Date startDate;

    @SerializedName("Einde")
    public String endDateString;

    public Date endDate;

    @SerializedName("LesuurVan")
    public int periodFrom;

    @SerializedName("LesuurTotMet")
    public int periodUpToAndIncluding;

    @SerializedName("DuurtHeleDag")
    public boolean takesAllDay;

    @SerializedName("Lokatie")
    public String location;

    @SerializedName("Status")
    public int classState;

    public AppointmentType type;

    public DisplayType displayType;

    public InfoType infoType;

    @SerializedName("Afgerond")
    public boolean finished;

    @SerializedName("Vakken")
    public SubSubject[] subjects;

    @SerializedName("Lokalen")
    public Classroom[] classrooms;

    @SerializedName("Docenten")
    public Teacher[] teachers;

    @SerializedName("Groep")
    public Group group;

    @SerializedName("Inhoud")
    public String content;

    @SerializedName("Omschrijving")
    public String description;
}
