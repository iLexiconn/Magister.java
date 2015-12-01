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

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.container.sub.Privilege;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivilegeAdapter extends TypeAdapter<Privilege[]> {
    public Gson gson = new Gson();

    public void write(JsonWriter out, Privilege[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Privilege[] read(JsonReader in) throws IOException {
        JsonArray array = gson.getAdapter(JsonElement.class).read(in).getAsJsonArray();
        List<Privilege> privilegeList = new ArrayList<Privilege>();
        for (JsonElement element : array) {
            JsonObject object = element.getAsJsonObject();
            String group = object.get("Naam").getAsString();
            JsonArray array1 = object.get("Privileges").getAsJsonArray();
            for (JsonElement element1 : array1) {
                Privilege privilege = gson.fromJson(element1, Privilege.class);
                privilege.group = group;
                privilegeList.add(privilege);
            }
        }
        return privilegeList.toArray(new Privilege[privilegeList.size()]);
    }
}
