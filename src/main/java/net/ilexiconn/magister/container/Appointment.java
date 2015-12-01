package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.Classroom;
import net.ilexiconn.magister.container.sub.Group;
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.container.sub.Teacher;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.container.type.DisplayType;
import net.ilexiconn.magister.container.type.InfoType;

public class Appointment {
    @SerializedName("Id")
    public int id;

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("LeerlingId")
    public int pupilId;

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

    /**
     * 1 = normal
     * 4 = falls out
     */
    @SerializedName("Status")
    public int classState;

    public transient AppointmentType type;

    public transient DisplayType displayType;

    public transient InfoType infoType;

    @SerializedName("Afgerond")
    public boolean finished;

    @SerializedName("Vakken")
    public net.ilexiconn.magister.container.sub.Class[] classes;

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
