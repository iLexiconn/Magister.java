package net.ilexiconn.magister.container.sub;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Attachment implements Serializable {
    @SerializedName("Id")
    public int id;

    @SerializedName("Naam")
    public String fileName;

    @SerializedName("ContentType")
    public String contentType;

    @SerializedName("Datum")
    public String uploadDate;

    @SerializedName("Grootte")
    public long fileSizeInBytes;

    @SerializedName("UniqueId")
    public String uniqueId;

    @SerializedName("BronSoort")
    public int sourceType;

    @SerializedName("Links")
    public Link[] links;


}
