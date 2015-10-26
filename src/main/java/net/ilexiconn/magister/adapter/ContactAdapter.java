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

package net.ilexiconn.magister.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.container.Contact;
import net.ilexiconn.magister.container.Link;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends TypeAdapter<Contact[]> {
    public void write(JsonWriter out, Contact[] value) throws IOException {

    }

    public Contact[] read(JsonReader in) throws IOException {
        List<Contact> contactList = new ArrayList<>();
        in.beginObject();
        while (in.hasNext()) {
            switch (in.nextName()) {
                case "Items": {
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        Contact contact = new Contact();
                        while (in.hasNext()) {
                            String s = in.nextName();
                            if (in.peek() == JsonToken.NULL) {
                                in.nextNull();
                                continue;
                            }
                            switch (s) {
                                case "Id": {
                                    contact.setId(in.nextInt());
                                    break;
                                }
                                case "Links": {
                                    in.beginArray();
                                    List<Link> linkList = new ArrayList<>();
                                    while (in.hasNext()) {
                                        in.beginObject();
                                        Link link = new Link();
                                        switch (in.nextName()) {
                                            case "href": {
                                                link.setHref(in.nextString());
                                                break;
                                            }
                                            case "rel": {
                                                link.setRel(in.nextString());
                                                break;
                                            }
                                        }
                                        linkList.add(link);
                                        in.endObject();
                                    }
                                    contact.setLinks(linkList.toArray(new Link[linkList.size()]));
                                    in.endArray();
                                    break;
                                }
                                case "Achternaam": {
                                    contact.setSurname(in.nextString());
                                    break;
                                }
                                case "Voornaam": {
                                    contact.setFirstName(in.nextString());
                                    break;
                                }
                                case "Tussenvoegsel": {
                                    contact.setSurnamePrefix(in.nextString());
                                    break;
                                }
                                case "Naam": {
                                    contact.setFullName(in.nextString());
                                    break;
                                }
                                case "Type": {
                                    contact.setType(in.nextInt());
                                    break;
                                }
                            }
                        }
                        contactList.add(contact);
                        in.endObject();
                    }
                    in.endArray();
                    break;
                }
                case "TotalCount": {
                    in.nextInt(); // Just ignore the value.
                    break;
                }
                case "Links": {
                    in.beginArray(); // Just ignore the array.
                    in.endArray();
                    break;
                }
            }
        }
        in.endObject();
        return contactList.toArray(new Contact[contactList.size()]);
    }
}
