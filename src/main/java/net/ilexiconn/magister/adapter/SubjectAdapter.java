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

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.sub.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SubjectAdapter extends TypeAdapter<Subject[]> {
    public TypeAdapter<JsonElement> jsonElementTypeAdapter;
    public Magister magister;

    public SubjectAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Subject[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Subject[] read(JsonReader jsonReader) throws IOException {
        if (jsonElementTypeAdapter == null) {
            jsonElementTypeAdapter = magister.gson.getAdapter(JsonElement.class);
        }
        List<Subject> subjectList = new ArrayList<>();
        for (JsonElement element : jsonElementTypeAdapter.read(jsonReader).getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            if (!object.has("afkorting")) {
                String description = object.get("Naam").getAsString();
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
                String description = object.get("omschrijving").getAsString();
                Subject s = ContainerCache.get(description, Subject.class);
                if (s != null) {
                    subjectList.add(s);
                    continue;
                }
                int id = object.get("id").getAsInt();
                int subjectId = object.get("studieVakId").getAsInt();
                int studyId = object.get("studieId").getAsInt();
                String abbreviation = object.get("afkorting").getAsString();
                boolean exemption = object.get("vrijstelling").getAsBoolean();
                boolean dispensation = object.get("dispensatie").getAsBoolean();
                int followId = object.get("volgnr").getAsInt();
                String teacher = object.get("docent") instanceof JsonNull ? null : object.get("docent").getAsString();
                String startDate = object.get("begindatum").getAsString();
                String endDate = object.get("einddatum").getAsString();
                subjectList.add(new Subject(id, subjectId, studyId, abbreviation, description, exemption, dispensation, followId, teacher, startDate, endDate));
            }
        }
        return subjectList.toArray(new Subject[subjectList.size()]);
    }
}
