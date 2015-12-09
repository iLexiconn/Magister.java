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
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.util.DateUtil;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Date;

public class PersonalAppointment implements Serializable {
    @SerializedName("Id")
    public int id = 0;

    @SerializedName("Start")
    public String startDate;

    @SerializedName("Einde")
    public String endDate;

    @SerializedName("DuurtHeleDag")
    public boolean takesAllDay = false;

    @SerializedName("Lokatie")
    public String location;

    @SerializedName("InfoType")
    public int infoType = 1;

    @SerializedName("WeergaveType")
    public int displayType = 1;

    @SerializedName("Type")
    public int appointmentType = 1;

    @SerializedName("Afgerond")
    public boolean finished = false;

    @SerializedName("Inhoud")
    public String content;

    @SerializedName("Omschrijving")
    public String description;

    @SerializedName("Status")
    public int classState = 2;

    @SerializedName("OpdrachtId")
    public int asignmentId = 0;

    public PersonalAppointment(String title, String content, String location, AppointmentType type, Date start, Date end) throws ParseException {
        this.location = location;
        this.content = content;
        if (title == null || "".equals(title)) {
            throw new InvalidParameterException("The appointment's title must be set!");
        }
        description = title;
        if (start.after(end) || start.equals(end)) {
            throw new InvalidParameterException("The appointment's start date must be before and not equal to the end date!");
        }
        startDate = DateUtil.dateToString(start);
        endDate = DateUtil.dateToString(end);
        if (!(type == AppointmentType.PERSONAL || type == AppointmentType.PLANNING)) {
            throw new InvalidParameterException("The AppointmentType must be PERSONAL or PLANNING!");
        }
        appointmentType = type.getID();
    }
}
