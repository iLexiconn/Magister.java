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

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectAdapter extends TypeAdapter<Subject[]> {
    public Magister magister;

    public SubjectAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Subject[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Subject[] read(JsonReader jsonReader) throws IOException {
        List<Subject> subjectList = new ArrayList<>();
        JsonArray array = null;
        JsonObject object = null;
        if (jsonReader.peek() == JsonToken.BEGIN_ARRAY) {
            array = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonArray();
        } else {
            object = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonObject();
        }
        if (array != null) {
            for (JsonElement element : array) {
                JsonObject obj = element.getAsJsonObject();
                if (!obj.has("afkorting")) {
                    String description = obj.get("Naam").getAsString();
                    Subject s = ContainerCache.get(description, Subject.class);
                    if (s != null) {
                        subjectList.add(s);
                    } else {
                        Subject[] subjects = magister.getSubjects();
                        for (Subject c : subjects) {
                            if (Objects.equals(c.description, description)) {
                                subjectList.add(ContainerCache.put(c, c.getClass()));
                            }
                        }
                    }
                } else {
                    String description = obj.get("omschrijving").getAsString();
                    Subject s = ContainerCache.get(description, Subject.class);
                    if (s != null) {
                        subjectList.add(s);
                        continue;
                    }
                    int id = obj.get("id").getAsInt();
                    int subjectId = obj.get("studieVakId").getAsInt();
                    int studyId = obj.get("studieId").getAsInt();
                    String abbreviation = obj.get("afkorting").getAsString();
                    boolean exemption = obj.get("vrijstelling").getAsBoolean();
                    boolean dispensation = obj.get("dispensatie").getAsBoolean();
                    int followId = obj.get("volgnr").getAsInt();
                    String teacher = obj.get("docent") instanceof JsonNull ? null : obj.get("docent").getAsString();
                    String startDate = obj.get("begindatum").getAsString();
                    String endDate = obj.get("einddatum").getAsString();
                    subjectList.add(new Subject(id, subjectId, studyId, abbreviation, description, exemption, dispensation, followId, teacher, startDate, endDate));
                }
            }
        } else if (object != null) {
            String abbreviation = object.get("Afkorting").getAsString();
            Subject[] s = magister.getSubjects();
            for (Subject subject : s) {
                if (Objects.equals(subject.abbreviation, abbreviation)) {
                    subjectList.add(subject);
                }
            }
        }
        return subjectList.toArray(new Subject[subjectList.size()]);
    }
}
