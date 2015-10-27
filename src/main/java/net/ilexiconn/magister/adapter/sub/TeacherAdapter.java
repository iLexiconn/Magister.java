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

package net.ilexiconn.magister.adapter.sub;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.Contact;
import net.ilexiconn.magister.container.sub.Teacher;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherAdapter extends TypeAdapter<Teacher[]> {
    public Magister magister;

    public TeacherAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Teacher[] value) throws IOException {

    }

    public Teacher[] read(JsonReader jsonReader) throws IOException {
        List<Teacher> teacherList = new ArrayList<>();
        System.out.println("1");
        for (JsonElement element : magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonArray()) {
            System.out.println("2");
            JsonObject object = element.getAsJsonObject();
            String code = object.get("Docentcode").getAsString();
            Teacher t = ContainerCache.get(code, Teacher.class);
            if (t != null) {
                teacherList.add(t);
                continue;
            }
            System.out.println("3");
            Contact[] contacts = magister.getTeacherInfo(code);
            System.out.println("4");
            for (Contact c : contacts) {
                teacherList.add(new Teacher(c.id, c.links, c.surname, c.firstName, c.surnamePrefix, c.fullName, c.type, code));
            }
        }
        return teacherList.toArray(new Teacher[teacherList.size()]);
    }
}
