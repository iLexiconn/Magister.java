package net.ilexiconn.magister.container.sub;

import com.google.gson.annotations.SerializedName;

public class Course {
    @SerializedName("Id")
    public int id;

    @SerializedName(value = "Naam", alternate = "Afkorting")
    public String name;
}
