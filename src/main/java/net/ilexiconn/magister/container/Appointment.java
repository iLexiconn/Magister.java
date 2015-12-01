package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.*;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.container.type.DisplayType;
import net.ilexiconn.magister.container.type.InfoType;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Appointment {
    @SerializedName("Id")
    public int id;

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("Start")
    public String startDate;

    @SerializedName("Einde")
    public String endDate;

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

    public transient AppointmentType type;

    public transient DisplayType displayType;

    public transient InfoType infoType;

    @SerializedName("Afgerond")
    public boolean finished;

    @SerializedName("Vakken")
    public Course[] courses;

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

    public static Date appointmentDateToDate(String date) throws ParseException {
        if (date == null) {
            throw new ParseException("String parameter was null", 0);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss.SSSSSSS");
        format.setTimeZone(TimeZone.getTimeZone("GMT-0"));
        return format.parse(date.replace("T", "-").replace("Z", ""));
    }
}
