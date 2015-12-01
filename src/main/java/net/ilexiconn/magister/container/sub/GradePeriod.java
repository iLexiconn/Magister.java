package net.ilexiconn.magister.container.sub;

import com.google.gson.annotations.SerializedName;

public class GradePeriod {

    @SerializedName("Id")
    public int id;

    @SerializedName("Naam")
    public boolean gradePeriodName;

    @SerializedName("VolgNummer")
    public int gradePeriodSerialNumber;
}
