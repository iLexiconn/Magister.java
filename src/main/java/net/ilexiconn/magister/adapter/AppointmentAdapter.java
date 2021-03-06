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
import net.ilexiconn.magister.container.Appointment;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.container.type.DisplayType;
import net.ilexiconn.magister.container.type.InfoType;
import net.ilexiconn.magister.util.DateUtil;
import net.ilexiconn.magister.util.GsonUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentAdapter extends TypeAdapter<Appointment[]> {
    private static Gson gson;

    static {
        Map<Class<?>, TypeAdapter<?>> map = new HashMap<Class<?>, TypeAdapter<?>>();
        map.put(AppointmentType.class, new AppointmentTypeAdapter());
        map.put(DisplayType.class, new DisplayTypeAdapter());
        map.put(InfoType.class, new InfoTypeAdapter());
        gson = GsonUtil.getGsonWithAdapters(map);
    }

    @Override
    public void write(JsonWriter out, Appointment[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Appointment[] read(JsonReader in) throws IOException {
        JsonObject object = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();
        if (object.has("Items")) {
            JsonArray array = object.get("Items").getAsJsonArray();
            List<Appointment> appointmentList = new ArrayList<Appointment>();
            for (JsonElement element : array) {
                JsonObject object1 = element.getAsJsonObject();
                Appointment appointment = gson.fromJson(object1, Appointment.class);
                try {
                    appointment.startDate = DateUtil.stringToDate(appointment.startDateString);
                    appointment.endDate = DateUtil.stringToDate(appointment.endDateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                appointment.type = gson.getAdapter(AppointmentType.class).fromJsonTree(object1.getAsJsonPrimitive("Type"));
                appointment.displayType = gson.getAdapter(DisplayType.class).fromJsonTree(object1.getAsJsonPrimitive("WeergaveType"));
                appointment.infoType = gson.getAdapter(InfoType.class).fromJsonTree(object1.getAsJsonPrimitive("InfoType"));
                appointmentList.add(appointment);
            }
            return appointmentList.toArray(new Appointment[appointmentList.size()]);
        } else {
            Appointment appointment = gson.fromJson(object, Appointment.class);
            try {
                appointment.startDate = DateUtil.stringToDate(appointment.startDateString);
                appointment.endDate = DateUtil.stringToDate(appointment.endDateString);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            appointment.type = gson.getAdapter(AppointmentType.class).fromJsonTree(object.getAsJsonPrimitive("Type"));
            appointment.displayType = gson.getAdapter(DisplayType.class).fromJsonTree(object.getAsJsonPrimitive("WeergaveType"));
            appointment.infoType = gson.getAdapter(InfoType.class).fromJsonTree(object.getAsJsonPrimitive("InfoType"));
            return new Appointment[]{appointment};
        }
    }
}
