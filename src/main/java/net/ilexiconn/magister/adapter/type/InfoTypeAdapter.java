package net.ilexiconn.magister.adapter.type;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.container.type.InfoType;

import java.io.IOException;

public class InfoTypeAdapter extends TypeAdapter<InfoType> {
    public Gson gson = new Gson();

    public void write(JsonWriter out, InfoType value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public InfoType read(JsonReader in) throws IOException {
        JsonPrimitive primitive = gson.getAdapter(JsonPrimitive.class).read(in);
        int id = primitive.getAsInt();
        return InfoType.getTypeById(id);
    }
}
