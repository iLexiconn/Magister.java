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
import net.ilexiconn.magister.container.sub.Classroom;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClassroomAdapter extends TypeAdapter<Classroom[]> {
    public Magister magister;

    public ClassroomAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Classroom[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Classroom[] read(JsonReader jsonReader) throws IOException {
        List<Classroom> classroomList = new ArrayList<>();
        for (JsonElement element : magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonArray()) {
            JsonObject object = element.getAsJsonObject();
            String name = object.get("Naam").getAsString();
            classroomList.add(new Classroom(name));
        }
        return classroomList.toArray(new Classroom[classroomList.size()]);
    }
}
