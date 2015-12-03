package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.Link;

public class MessageFolder {
    @SerializedName("Naam")
    public String naam;

    @SerializedName("OngelezenBerichten")
    public int unreadMessages;

    @SerializedName("Id")
    public int id;

    @SerializedName("ParentId")
    public int parentId;


//    TODO: Seems always to be NULL
//    @SerializedName("BerichtenUri")
//    public int id;

    @SerializedName("Links")
    public Link[] links;
}
