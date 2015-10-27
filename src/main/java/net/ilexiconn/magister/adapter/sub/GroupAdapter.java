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
import net.ilexiconn.magister.container.sub.Group;
import net.ilexiconn.magister.container.sub.Link;

import java.io.IOException;

public class GroupAdapter extends TypeAdapter<Group> {
    public TypeAdapter<JsonElement> jsonElementTypeAdapter;
    public Magister magister;

    public GroupAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Group value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Group read(JsonReader jsonReader) throws IOException {
        if (jsonElementTypeAdapter == null) {
            jsonElementTypeAdapter = magister.gson.getAdapter(JsonElement.class);
        }
        JsonObject groupObject = (JsonObject) jsonElementTypeAdapter.read(jsonReader);
        int id = groupObject.get("Id").getAsInt();
        Group group = ContainerCache.get(id + "", Group.class);
        if (group == null) {
            Link[] links = magister.gson.getAdapter(Link[].class).fromJsonTree(groupObject.getAsJsonArray("Links"));
            String description = groupObject.get("Omschrijving").getAsString();
            int locationId = groupObject.get("LocatieId").getAsInt();
            group = new Group(id, links, description, locationId);
        }
        return group;
    }
}
