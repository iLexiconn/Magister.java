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

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.sub.Privilege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivilegeAdapter extends TypeAdapter<Privilege[]> {
    public Magister magister;

    public PrivilegeAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Privilege[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Privilege[] read(JsonReader jsonReader) throws IOException {
        List<Privilege> privilegeList = new ArrayList<>();
        JsonArray array = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonArray();
        for (JsonElement element : array) {
            JsonObject object = element.getAsJsonObject();
            String type = object.get("Naam").getAsString();
            JsonArray array1 = object.get("Privileges").getAsJsonArray();
            for (JsonElement element1 : array1) {
                JsonObject object1 = element1.getAsJsonObject();
                String name = object1.get("Naam").getAsString();
                Privilege p = ContainerCache.get(type + name, Privilege.class);
                if (p != null) {
                    privilegeList.add(p);
                    continue;
                }
                List<String> stringList = new ArrayList<>();
                JsonArray array2 = object1.get("AccessType").getAsJsonArray();
                for (JsonElement element2 : array2) {
                    stringList.add(element2.getAsString());
                }
                privilegeList.add(new Privilege(type, name, stringList.toArray(new String[stringList.size()])));
            }
        }
        return privilegeList.toArray(new Privilege[privilegeList.size()]);
    }
}
