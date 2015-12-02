package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.cache.ICachable;
import net.ilexiconn.magister.container.sub.Link;

public class Contact implements ICachable {
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

    public String getID() {
        return "contact-" + id;
    }

    public Class<? extends ICachable> getType() {
        return Contact.class;
    }
}
