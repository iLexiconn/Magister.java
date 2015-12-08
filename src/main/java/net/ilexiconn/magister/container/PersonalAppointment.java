package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.util.DateUtil;

import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Date;

public class PersonalAppointment {
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

    public PersonalAppointment(String title, String location, AppointmentType type, Date start, Date end) throws ParseException {
        this.location = location;
        content = title;
        startDate = DateUtil.dateToString(start);
        endDate = DateUtil.dateToString(end);
        if (!(type == AppointmentType.PERSONAL || type == AppointmentType.PLANNING)) {
            throw new InvalidParameterException("The AppointmentType must be PERSONAL or PLANNING!");
        }
    }
}
