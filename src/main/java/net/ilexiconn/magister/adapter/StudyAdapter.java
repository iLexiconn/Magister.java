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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.Study;
import net.ilexiconn.magister.container.sub.Group;
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.container.sub.SubStudy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudyAdapter extends TypeAdapter<Study[]> {
    public Magister magister;

    public StudyAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Study[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Study[] read(JsonReader jsonReader) throws IOException {
        List<Study> studyList = new ArrayList<>();
        JsonObject object = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonObject();
        JsonArray items = object.get("Items").getAsJsonArray();
        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();
            int id = item.get("Id").getAsInt();
            Study s = ContainerCache.get(id + "", Study.class);
            if (s != null) {
                studyList.add(s);
                continue;
            }
            Link[] links = magister.gson.getAdapter(Link[].class).fromJsonTree(item.get("Links"));
            int pupilId = item.get("LeerlingId").getAsInt();
            String startDate = item.get("Start").getAsString();
            String endDate = item.get("Einde").getAsString();
            String classPeriod = item.get("Lesperiode").getAsString();
            SubStudy study = magister.gson.getAdapter(SubStudy.class).fromJsonTree(item.get("Studie"));
            Group group = magister.gson.getAdapter(Group.class).fromJsonTree(item.get("Groep"));
            studyList.add(new Study(id, links, pupilId, startDate, endDate, classPeriod, study, group));
        }
        return studyList.toArray(new Study[studyList.size()]);
    }
}
