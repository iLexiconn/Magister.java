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
import net.ilexiconn.magister.adapter.type.AppointmentTypeAdapter;
import net.ilexiconn.magister.adapter.type.DisplayTypeAdapter;
import net.ilexiconn.magister.adapter.type.InfoTypeAdapter;
import net.ilexiconn.magister.container.SingleMessage;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.container.type.DisplayType;
import net.ilexiconn.magister.container.type.InfoType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleMessageAdapter extends TypeAdapter<SingleMessage[]> {
    public Gson gson = new GsonBuilder()
            .registerTypeAdapter(AppointmentType.class, new AppointmentTypeAdapter())
            .registerTypeAdapter(DisplayType.class, new DisplayTypeAdapter())
            .registerTypeAdapter(InfoType.class, new InfoTypeAdapter())
            .create();

    @Override
    public void write(JsonWriter out, SingleMessage[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public SingleMessage[] read(JsonReader in) throws IOException {
        JsonObject object = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();
        List<SingleMessage> SingleMessageList = new ArrayList<SingleMessage>();
        SingleMessageList.add(gson.fromJson(object.getAsJsonObject(), SingleMessage.class));

        return SingleMessageList.toArray(new SingleMessage[SingleMessageList.size()]);
    }
}
