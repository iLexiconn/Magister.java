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

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.cache.ContainerCache;
import net.ilexiconn.magister.container.Profile;
import net.ilexiconn.magister.container.sub.Privilege;

import java.io.IOException;

public class ProfileAdapter extends TypeAdapter<Profile> {
    public Magister magister;

    public ProfileAdapter(Magister m) {
        magister = m;
    }

    public void write(JsonWriter jsonWriter, Profile value) throws IOException {
        throw new UnsupportedOperationException("Not implemented");
    }

    public Profile read(JsonReader jsonReader) throws IOException {
        JsonObject object = magister.gson.getAdapter(JsonElement.class).read(jsonReader).getAsJsonObject();
        JsonObject person = object.get("Persoon").getAsJsonObject();
        int id = person.get("Id").getAsInt();
        Profile p = ContainerCache.get(id + "", Profile.class);
        if (p != null) {
            return p;
        }
        String nickname = person.get("Roepnaam").getAsString();
        String surnamePrefix = person.get("Tussenvoegsel") instanceof JsonNull ? null : person.get("Tussenvoegsel").getAsString();
        String surname = person.get("Achternaam").getAsString();
        String officialFirstNames = person.get("OfficieleVoornamen").getAsString();
        String initials = person.get("Voorletters").getAsString();
        String officialSurnamePrefixes = person.get("OfficieleTussenvoegsels") instanceof JsonNull ? null : person.get("OfficieleTussenvoegsels").getAsString();
        String officialSurname = person.get("OfficieleAchternaam").getAsString();
        String dateOfBirth = person.get("Geboortedatum").getAsString();
        String birthSurname = person.get("GeboorteAchternaam") instanceof JsonNull ? null : person.get("GeboorteAchternaam").getAsString();
        String birthSurnamePrefix = person.get("GeboortenaamTussenvoegsel") instanceof JsonNull ? null : person.get("GeboortenaamTussenvoegsel").getAsString();
        boolean useBirthName = person.get("GebruikGeboortenaam").getAsBoolean();
        Privilege[] privileges = magister.gson.getAdapter(Privilege[].class).fromJsonTree(object.get("Groep").getAsJsonArray());
        return new Profile(id, nickname, surnamePrefix, surname, officialFirstNames, initials, officialSurnamePrefixes, officialSurname, dateOfBirth, birthSurname, birthSurnamePrefix, useBirthName, privileges);
    }
}
