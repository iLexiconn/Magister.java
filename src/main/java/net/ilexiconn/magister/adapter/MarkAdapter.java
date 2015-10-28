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
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.Mark;
import net.ilexiconn.magister.container.sub.MarkColumn;
import net.ilexiconn.magister.container.sub.MarkPeriod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MarkAdapter extends TypeAdapter<Mark[]> {
    public Magister magister;

    public MarkAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Mark[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Mark[] read(JsonReader jsonReader) throws IOException {
        List<Mark> markList = new ArrayList<>();
        JsonObject object = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonObject();
        JsonArray items = object.get("Items").getAsJsonArray();
        for (JsonElement element : items) {
            JsonObject item = element.getAsJsonObject();
            int id = item.get("CijferId").getAsInt();
            Mark m = ContainerCache.get(id + "", Mark.class);
            if (m != null) {
                markList.add(m);
                continue;
            }
            String mark = item.get("CijferStr").getAsString();
            boolean sufficient = item.get("IsVoldoende").getAsBoolean();
            String date = item.get("DatumIngevoerd").getAsString();
            MarkPeriod markPeriod = magister.gson.getAdapter(MarkPeriod.class).fromJsonTree(item.get("CijferPeriode"));
            JsonObject subject = item.get("Vak").getAsJsonObject();
            boolean canCatch = item.get("Inhalen").getAsBoolean();
            boolean hasExemption = item.get("Vrijstelling").getAsBoolean();
            boolean counts = item.get("TeltMee").getAsBoolean();
            MarkColumn markColumn = magister.gson.getAdapter(MarkColumn.class).fromJsonTree(item.get("CijferKolom"));
            int eloId = item.get("CijferKolomIdEloOpdracht").getAsInt();
            String teacher = item.get("Docent") instanceof JsonNull ? null : item.get("Docent").getAsString();
            boolean dispensation = item.get("VakDispensatie").getAsBoolean();
            boolean subjectExemption = item.get("VakVrijstelling").getAsBoolean();
            markList.add(new Mark(id, mark, sufficient, date, markPeriod, subject, canCatch, hasExemption, counts, markColumn, eloId, teacher, dispensation, subjectExemption));
        }
        return markList.toArray(new Mark[markList.size()]);
    }
}
