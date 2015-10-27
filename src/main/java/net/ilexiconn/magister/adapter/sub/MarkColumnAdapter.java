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
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.sub.MarkColumn;

import java.io.IOException;

public class MarkColumnAdapter extends TypeAdapter<MarkColumn> {
    public Magister magister;

    public MarkColumnAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, MarkColumn value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public MarkColumn read(JsonReader jsonReader) throws IOException {
        JsonObject object = (JsonObject) magister.gson.getAdapter(JsonElement.class).read(jsonReader);
        int id = object.get("Id").getAsInt();
        MarkColumn markColumn = ContainerCache.get(id + "", MarkColumn.class);
        if (markColumn == null) {
            String name = object.get("KolomNaam").getAsString();
            String columnNumber = object.get("KolomNummer").getAsString();
            String followId = object.get("KolomVolgNummer").getAsString();
            String header = object.get("KolomKop").getAsString();
            String description = object.get("KolomOmschrijving") instanceof JsonNull ? null : object.get("KolomOmschrijving").getAsString();
            int type = object.get("KolomSoort").getAsInt();
            boolean resitColumn = object.get("IsHerkansingKolom").getAsBoolean();
            boolean teacherColumn = object.get("IsDocentKolom").getAsBoolean();
            boolean columnUnderneath = object.get("HeeftOnderliggendeKolommen").getAsBoolean();
            boolean pta = object.get("IsPTAKolom").getAsBoolean();
            markColumn = new MarkColumn(id, name, columnNumber, followId, header, description, type, resitColumn, teacherColumn, columnUnderneath, pta);
        }
        return markColumn;
    }
}
