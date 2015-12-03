package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.Link;

public class Contact {
    @SerializedName("Id")
    public int id;

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("Achternaam")
    public String surname;

    @SerializedName("Voornaam")
    public String firstName;

    @SerializedName("Tussenvoegsel")
    public String surnamePrefix;

    @SerializedName("Naam")
    public String name;

    @SerializedName("Type")
    public int type; //???
}
