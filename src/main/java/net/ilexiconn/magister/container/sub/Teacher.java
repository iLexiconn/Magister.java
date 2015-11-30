package net.ilexiconn.magister.container.sub;

import com.google.gson.annotations.SerializedName;

public class Teacher {
    @SerializedName("Id")
    public int id;

    @SerializedName("Naam")
    public String name;

    @SerializedName("Docentcode")
    public String TeacherAbbreviation;
}
