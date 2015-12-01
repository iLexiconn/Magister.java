package net.ilexiconn.magister.container.sub;

import com.google.gson.annotations.SerializedName;

public class GradeRow {

    @SerializedName("Id")
    public int id;

    @SerializedName("KolomNaam")
    public String rowName;

    @SerializedName("KolomVolgNummer")
    public String rowSerialNumber;

    @SerializedName("KolomNummer")
    public String rowNumber;

    public transient String rowDiscription; //Discription of grade
    /*
        Not Implemented yet.
        Can't use @SerializedName("KolomOmschrijving") because this is always null.
     */

    @SerializedName("KolomKop")
    public String rowTitle;

    @SerializedName("KolomSoort")
    public int rowSort;
    /*
        rowSort:
            when 0 : null
            when 1 : unknown
            when 2 : Average
            when 3 : normal
    */

    @SerializedName("IsDocentKolom")
    public boolean isTeacherOnly;

    @SerializedName("IsHerkansingKolom")
    public boolean isResitRow;

    @SerializedName("HeeftOnderliggendeKolommen")
    public boolean hasSubRows;

    @SerializedName("IsPTAKolom")
    public boolean isPTARow;
}
