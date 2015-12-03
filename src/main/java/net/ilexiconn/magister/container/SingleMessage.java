package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.Link;

public class SingleMessage {
    @SerializedName("Inhoud")
    public String content;

    /* TODO: KopieOntvangers */

    /* TODO: Bijlagen */

    @SerializedName("Id")
    public int id;

    @SerializedName("MapId")
    public int mapId;

    @SerializedName("MapTitel")
    public String mapTitle;

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("Onderwerp")
    public String topic;

    /* TODO: Afzender */

    @SerializedName("IngekortBericht")
    public String shortMessage;

    /* TODO: Ontvangers */

    @SerializedName("VerstuurdOp")
    public String sentOn;

    @SerializedName("IsGelezen")
    public boolean isRead;

    @SerializedName("Status")
    public int status;

    @SerializedName("HeeftPrioriteit")
    public boolean hasPriority;

    @SerializedName("HeeftBijlagen")
    public boolean hasAttachments;

    @SerializedName("Soort")
    public int type;
}
