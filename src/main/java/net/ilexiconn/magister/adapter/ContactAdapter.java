package net.ilexiconn.magister.adapter;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.container.Contact;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends TypeAdapter<Contact[]> {
    public Gson gson = new Gson();

    public void write(JsonWriter out, Contact[] value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Contact[] read(JsonReader in) throws IOException {
        JsonObject object = gson.getAdapter(JsonElement.class).read(in).getAsJsonObject();
        JsonArray array = object.get("Items").getAsJsonArray();
        List<Contact> contactList = new ArrayList<Contact>();
        for (JsonElement element : array) {
            JsonObject object1 = element.getAsJsonObject();
            Contact contact = gson.fromJson(object1, Contact.class);
            contactList.add(contact);
        }
        return contactList.toArray(new Contact[contactList.size()]);
    }
}
