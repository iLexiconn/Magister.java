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
import net.ilexiconn.magister.container.Homework;
import net.ilexiconn.magister.container.sub.Classroom;
import net.ilexiconn.magister.container.sub.Link;
import net.ilexiconn.magister.container.sub.Subject;
import net.ilexiconn.magister.container.sub.Teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeworkAdapter extends TypeAdapter<Homework[]> {
    public Magister magister;

    public HomeworkAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Homework[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Homework[] read(JsonReader jsonReader) throws IOException {
        List<Homework> homeworkList = new ArrayList<>();
        JsonObject homeworkObject = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonObject();
        JsonArray items = homeworkObject.get("Items").getAsJsonArray();
        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();
            int id = item.get("Id").getAsInt();
            Homework h = ContainerCache.get(id + "", Homework.class);
            if (h != null) {
                homeworkList.add(h);
                continue;
            }
            Link[] links = magister.gson.getAdapter(Link[].class).fromJsonTree(item.getAsJsonArray("Links"));
            String startDate = item.get("Start").getAsString();
            String endDate = item.get("Einde").getAsString();
            int classFrom = item.get("LesuurVan").getAsInt();
            int classTo = item.get("LesuurTotMet").getAsInt();
            boolean wholeDay = item.get("DuurtHeleDag").getAsBoolean();
            String description = item.get("Omschrijving").getAsString();
            String location = item.get("Lokatie").getAsString();
            int status = item.get("Status").getAsInt();
            int type = item.get("Type").getAsInt();
            int displayType = item.get("WeergaveType").getAsInt();
            int infoType = item.get("InfoType").getAsInt();
            boolean finished = item.get("Afgerond").getAsBoolean();
            Subject[] subjects = magister.gson.getAdapter(Subject[].class).fromJsonTree(item.getAsJsonArray("Vakken"));
            System.out.println("pre");
            Teacher[] teachers = magister.gson.getAdapter(Teacher[].class).fromJsonTree(item.getAsJsonArray("Docenten"));
            System.out.println("post");
            Classroom[] classrooms = magister.gson.getAdapter(Classroom[].class).fromJsonTree(item.getAsJsonArray("Lokalen"));
            int homeworkId = item.get("OpdrachtId").getAsInt();
            boolean hasAttachment = item.get("HeeftBijlagen").getAsBoolean();
            homeworkList.add(new Homework(id, links, startDate, endDate, classFrom, classTo, wholeDay, description, location, status, type, displayType, infoType, finished, subjects, teachers, classrooms, homeworkId, hasAttachment));
        }
        return homeworkList.toArray(new Homework[homeworkList.size()]);
    }
}
