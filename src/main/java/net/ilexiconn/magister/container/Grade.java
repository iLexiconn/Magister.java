package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;

import net.ilexiconn.magister.container.sub.Course;
import net.ilexiconn.magister.container.sub.GradePeriod;
import net.ilexiconn.magister.container.sub.GradeRow;

import java.util.Date;

public class Grade {
    @SerializedName("CijferId")
    public int id;

    @SerializedName("CijferStr")
    public String grade;

    @SerializedName("IsVoldoende")
    public boolean isSufficient;

    @SerializedName("IngevoerdDoor")
    public Object filledInBy;  //Always returns null?

    @SerializedName("DatumIngevoerd")
    public String filledInDateString;

    public transient Date filledInDate;

    @SerializedName("CijferPeriode")
    public GradePeriod gradePeriod;

    @SerializedName("Inhalen")
    public boolean doAtLaterDate;

    @SerializedName("Vrijstelling")
    public boolean dispensation;

    @SerializedName("TeltMee")
    public boolean doesCount;

    @SerializedName("CijferKolom")
    public GradeRow gradeRow;

    @SerializedName("Docent")
    public String teacherAbbreviation;

    @SerializedName("CijferKolomIdEloOpdracht")
    public String gradeRowIdOfElo;

    @SerializedName("Vak")
    public Course course;

    @SerializedName("VakDispensatie")
    public String dispensationForCourse;

    @SerializedName("VakVrijstelling")
    public String dispensationForCourse2;
}
