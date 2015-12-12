/*
 * Copyright (c) 2015 iLexiconn
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.container.sub.Attachment;
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.util.DateUtil;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SingleMessage implements Serializable {
    @SerializedName("Inhoud")
    public String content;

    /* TODO: KopieOntvangers */

    /* TODO: Bijlagen */

    @SerializedName("Id")
    public int id = 0;

    @SerializedName("MapId")
    public int mapId = 0;

    @SerializedName("MapTitel")
    public String mapTitle = "";

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("Onderwerp")
    public String topic;

    @SerializedName("Afzender")
    public Contact sender = new Contact();

    @SerializedName("IngekortBericht")
    public String shortMessage = "";

    @SerializedName("Ontvangers")
    public Contact[] recipients;

    @SerializedName("VerstuurdOp")
    public String sentOn;

    @SerializedName("IsGelezen")
    public boolean isRead = false;

    @SerializedName("Status")
    public int status = 0;

    @SerializedName("HeeftPrioriteit")
    public boolean hasPriority = false;

    @SerializedName("HeeftBijlagen")
    public boolean hasAttachments = false;

    @SerializedName("Bijlagen")
    private Attachment[] attachments;

    @SerializedName("Soort")
    public int type = 1;

    public SingleMessage(String topic, String content, Contact[] recipients) throws ParseException, IOException {
        this(null, topic, content, recipients, new File[]{});
    }

    public SingleMessage(Magister m, String topic, String content, Contact[] recipients, File attachment) throws ParseException, IOException {
        this(m, topic, content, recipients, new File[]{attachment});
    }

    public SingleMessage(Magister m, String topic, String content, Contact[] recipients, File[] attachments) throws ParseException, IOException {
        if (!(attachments == null || attachments.length <= 0)) {
            this.hasAttachments = true;
            List<Attachment> list = new ArrayList<Attachment>();
            for (File f : attachments) {
                list.add(getAttachmentFromFile(m, f));
            }
            this.attachments = new Attachment[list.size()];
            this.attachments = list.toArray(this.attachments);
        }
        if (recipients == null || recipients.length <= 0) {
            throw new InvalidParameterException("Recipients must not be null and have a length higher than 0");
        }
        this.topic = topic;
        this.content = content;
        this.recipients = recipients;
        sentOn = DateUtil.dateToString(new Date());
    }

    private static Attachment getAttachmentFromFile(Magister m, File f) throws IOException, ParseException {
        if (f.isDirectory()) {
            throw new NoSuchFileException("File must not be an Directory");
        }
        Attachment a = new Attachment();
        a.fileName = f.getName();
        a.contentType = Files.probeContentType(f.toPath());
        a.id = 0;
        a.sourceType = 0;
        a.uploadDate = DateUtil.dateToString(new Date());
        a.fileSizeInBytes = f.length();
        String response = HttpUtil.convertInputStreamReaderToString(HttpUtil.httpPostFile(m, f));
        a.uniqueId = GsonUtil.getFromJson(response, "Value").getAsString();
        return a;
    }

    public URL[] getAttachmentsUrls(Magister magister) throws MalformedURLException {
        if (!hasAttachments) {
            return null;
        }
        List<URL> urls = new ArrayList<URL>();
        for (Attachment attachment : attachments) {
            for (Link link : attachment.links) {
                if ("Self".equalsIgnoreCase(link.rel)) {
                    urls.add(new URL(magister.school.url + link.href));
                }
            }
        }
        if (urls.size() == 0) {
            return null;
        }
        return urls.toArray(new URL[urls.size()]);
    }
}
