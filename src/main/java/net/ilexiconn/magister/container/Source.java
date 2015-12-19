/*
 * Copyright (c) 2015 iLexiconn
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.container;

import com.google.gson.annotations.SerializedName;
import net.ilexiconn.magister.container.sub.Link;

import java.io.Serializable;

public class Source implements Serializable {
    @SerializedName("Id")
    public int id;

    @SerializedName("Links")
    public Link[] links;

    @SerializedName("BronSoort")
    public int sourceType;

    @SerializedName("Naam")
    public String name;

    @SerializedName("Referentie")
    public int reference;

    //Uri

    @SerializedName("Grootte")
    public int size;

    @SerializedName("Privilege")
    public int privelege;

    @SerializedName("Type")
    public int type;

    @SerializedName("ContentType")
    public String contentType;

    //GewijzigdOp

    //GeplaatstDoor

    //GemaaktOp

    @SerializedName("FileBlobId")
    public int fileBlobId;

    @SerializedName("ParentId")
    public int parentId;

    @SerializedName("UniqueId")
    public String uniqueId;

    @SerializedName("Volgnr")
    public int followId;

    @SerializedName("ModuleSoort")
    public int moduleType;
}
