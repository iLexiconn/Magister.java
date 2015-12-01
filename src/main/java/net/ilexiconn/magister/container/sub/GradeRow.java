package net.ilexiconn.magister.container.sub;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.type.RowType;

public class GradeRow {
    @SerializedName("Id")
    public int id;

    @SerializedName("KolomNaam")
    public String rowName;

    @SerializedName("KolomVolgNummer")
    public String rowSerialNumber;

    @SerializedName("KolomNummer")
    public String rowNumber;

    @SerializedName("KolomOmschrijving")
    public String rowDiscription; //TODO: See if this works

    @SerializedName("KolomKop")
    public String rowTitle;

    @SerializedName("KolomSoort")
    public RowType rowSort;

    @SerializedName("IsDocentKolom")
    public boolean isTeacherOnly;

    @SerializedName("IsHerkansingKolom")
    public boolean isResitRow;

    @SerializedName("HeeftOnderliggendeKolommen")
    public boolean hasSubRows;

    @SerializedName("IsPTAKolom")
    public boolean isPTARow;
}
