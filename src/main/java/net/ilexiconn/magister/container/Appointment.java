package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;

import net.ilexiconn.magister.container.sub.ClassRoom;
import net.ilexiconn.magister.container.sub.Group;
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.container.sub.Teacher;

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
    public int PeriodFrom;

    @SerializedName("LesuurTotMet")
    public int PeriodUpToAndIncluding;

    @SerializedName("DuurtHeleDag")
    public boolean TakesAllDag;

    @SerializedName("Lokatie")
    public String location;

    @SerializedName("Status")
    public int ClassState;// 1 = normal, 4 = falls out

    @SerializedName("Type")
    public int Type;
    /*
        Types:
                when 1 then 'personal'
				when 2 then 'general'
				when 3 then 'schoolwide'
				when 4 then 'internship'
				when 5 then 'intake'
				when 6 then 'scheduleFree'
				when 7 then 'kwt'
				when 8 then 'standby'
				when 9 then 'block'
				when 10 then 'miscellaneous'
				when 11 then 'localBlock'
				when 12 then 'classBlock'
				when 13 then 'lesson'
				when 14 then 'studiehuis'
				when 15 then 'scheduleFreeStudy'
				when 16 then 'planning'
				# what's up with this big hole here, schoolmaster?
				when 101 then 'actions'
				when 102 then 'presences'
				when 103 then 'examSchedule'

     */

    @SerializedName("WeergaveType")
    public int DisplayType;

    /*
        DisplayTypes:
    			when 1 then 'available'
				when 2 then 'provisionallyScheduled'
				when 3 then 'scheduled'
				when 4 then 'absent'
     */

    @SerializedName("InfoType")
    public int InfoType;

    /*
        InfoTypes:
     			when 0 then 'none'
				when 1 then 'homework'
				when 2 then 'test'
				when 3 then 'exam'
				when 4 then 'quiz'
				when 5 then 'oral'
				when 6 then 'information'
				when 7 then 'annotation'

     */

    @SerializedName("Afgerond")
    public boolean finished;

    @SerializedName("Vakken")
    public net.ilexiconn.magister.container.sub.Class[] Classes;

    @SerializedName("Lokalen")
    public ClassRoom[] ClassRooms;

    @SerializedName("Docenten")
    public Teacher[] Teachers;

    @SerializedName("Groep")
    public Group group;

    @SerializedName("Inhoud")
    public String content;

    @SerializedName("Omschrijving")
    public String description;
}
