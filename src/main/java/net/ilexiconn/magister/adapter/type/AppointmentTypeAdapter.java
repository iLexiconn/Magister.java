package net.ilexiconn.magister.adapter.type;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.container.type.AppointmentType;

import java.io.IOException;

public class AppointmentTypeAdapter extends TypeAdapter<AppointmentType> {
    public Gson gson = new Gson();

    @Override
    public void write(JsonWriter out, AppointmentType value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    public AppointmentType read(JsonReader in) throws IOException {
        JsonPrimitive primitive = gson.getAdapter(JsonPrimitive.class).read(in);
        int id = primitive.getAsInt();
        return AppointmentType.getTypeById(id);
    }
}
