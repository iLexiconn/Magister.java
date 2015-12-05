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
import net.ilexiconn.magister.container.PresencePeriod;
import net.ilexiconn.magister.util.DateUtil;
import net.ilexiconn.magister.util.LogUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class PresencePeriodAdapter extends TypeAdapter<PresencePeriod[]> {
    public Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, PresencePeriod[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public PresencePeriod[] read(JsonReader in) throws IOException {
        JsonObject object = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();
        JsonArray array = object.get("Items").getAsJsonArray();
        List<PresencePeriod> presencePeriodList = new ArrayList<PresencePeriod>();
        for (JsonElement element : array) {
            JsonObject object1 = element.getAsJsonObject();
            PresencePeriod presencePeriod = gson.fromJson(object1, PresencePeriod.class);
            try {
                presencePeriod.startDate = DateUtil.stringToDate(presencePeriod.start);
                presencePeriod.endDate = DateUtil.stringToDate(presencePeriod.end);
            } catch (ParseException e) {
                LogUtil.printError("Failed to parse date.", e);
            }
            presencePeriodList.add(presencePeriod);
        }
        return presencePeriodList.toArray(new PresencePeriod[presencePeriodList.size()]);
    }
}
