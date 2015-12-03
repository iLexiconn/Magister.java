package net.ilexiconn.magister.adapter.type;

import com.google.gson.Gson;
import com.google.gson.JsonPrimitive;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.container.type.RowType;

import java.io.IOException;

public class RowTypeAdapter extends TypeAdapter<RowType> {
    public Gson gson = new Gson();

    public void write(JsonWriter out, RowType value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public RowType read(JsonReader in) throws IOException {
        JsonPrimitive primitive = gson.getAdapter(JsonPrimitive.class).read(in);
        int id = primitive.getAsInt();
        return RowType.getTypeById(id);
    }
}
