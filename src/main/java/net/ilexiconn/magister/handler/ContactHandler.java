/*
 * Copyright (c) 2015 iLexiconn
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.handler;

import com.google.gson.Gson;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.adapter.ContactAdapter;
import net.ilexiconn.magister.container.Contact;
import net.ilexiconn.magister.exeption.PrivilegeException;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.IOException;

public class ContactHandler implements IHandler {
    private Gson gson = GsonUtil.getGsonWithAdapter(Contact[].class, new ContactAdapter());
    private Magister magister;

    public ContactHandler(Magister magister) {
        this.magister = magister;
    }

    /**
     * Get an array of {@link Contact}s with pupil contact information. If no contacts can be found, an empty array will
     * be returned instead.
     *
     * @param name the name of the pupil.
     * @return an array of {@link Contact}s with the contact information.
     * @throws IOException if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Contact[] getPupilInfo(String name) throws IOException, PrivilegeException {
        return getContactInfo(name, "Leerling");
    }

    /**
     * Get an array of {@link Contact}s with teacher contact information. If no contacts can be found, an empty array
     * will be returned instead.
     *
     * @param name the name of the teacher.
     * @return an array of {@link Contact} with the contact information.
     * @throws IOException if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Contact[] getTeacherInfo(String name) throws IOException, PrivilegeException {
        return getContactInfo(name, "Personeel");
    }

    /**
     * Get an array of {@link Contact}s with contact information. If no contacts can be found, an empty array will be
     * returned instead.
     *
     * @param name the name.
     * @return an array of {@link Contact} with the contact information.
     * @throws IOException if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Contact[] getContactInfo(String name, String type) throws IOException, PrivilegeException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/contactpersonen?contactPersoonType=" + type + "&q=" + name), Contact[].class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "Contactpersonen";
    }
}
