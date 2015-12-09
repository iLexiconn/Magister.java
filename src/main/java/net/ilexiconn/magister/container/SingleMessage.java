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
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.util.DateUtil;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.Date;

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

    @SerializedName("Soort")
    public int type = 1;

    public SingleMessage(String topic, String content, Contact[] recipients) throws ParseException {
        this(topic, content, recipients, null);
    }

    public SingleMessage(String topic, String content, Contact recipient) throws ParseException {
        this(topic, content, new Contact[]{recipient}, null);
    }

    public SingleMessage(String topic, String content, Contact recipient, Object[] attachments) throws ParseException {
        this(topic, content, new Contact[]{recipient}, attachments);
    }

    public SingleMessage(String topic, String content, Contact[] recipients, Object[] attachments) throws ParseException {
        if (!(attachments == null || attachments.length <= 0)) {
            this.hasAttachments = true;
        }
        if (recipients == null || recipients.length <= 0) {
            throw new InvalidParameterException("Recipients must not be null and have a length higher than 0");
        }
        this.topic = topic;
        this.content = content;
        this.recipients = recipients;
        //this.attachments = attachments;
        sentOn = DateUtil.dateToString(new Date());
    }
}
