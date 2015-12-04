package net.ilexiconn.magister.adapter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.adapter.type.RowTypeAdapter;
import net.ilexiconn.magister.container.Appointment;
import net.ilexiconn.magister.container.Grade;
import net.ilexiconn.magister.container.type.RowType;
import net.ilexiconn.magister.util.LogUtil;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class GradeAdapter extends TypeAdapter<Grade[]> {
    public Gson gson = new GsonBuilder()
            .registerTypeAdapter(RowType.class, new RowTypeAdapter())
            .create();

    @Override
    public void write(JsonWriter out, Grade[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public Grade[] read(JsonReader in) throws IOException {
        JsonObject object = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();
        JsonArray array = object.get("Items").getAsJsonArray();
        List<Grade> gradeList = new ArrayList<Grade>();
        for (JsonElement element : array) {
            JsonObject object1 = element.getAsJsonObject();
            Grade grade = gson.fromJson(object1, Grade.class);
            if (grade.filledInDateString != null) {
                try {
                    grade.filledInDate = Appointment.appointmentDateToDate(grade.filledInDateString);
                } catch (ParseException e) {
                    LogUtil.printError("Unable to parse date", e);
                }
            }
            gradeList.add(grade);
        }
        return gradeList.toArray(new Grade[gradeList.size()]);
    }
}
