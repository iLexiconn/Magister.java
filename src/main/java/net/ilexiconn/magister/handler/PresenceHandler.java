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
import com.google.gson.TypeAdapter;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.adapter.PresenceAdapter;
import net.ilexiconn.magister.adapter.PresencePeriodAdapter;
import net.ilexiconn.magister.container.Presence;
import net.ilexiconn.magister.container.PresencePeriod;
import net.ilexiconn.magister.exeption.PrivilegeException;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PresenceHandler implements IHandler {
    private Gson gson;
    private Magister magister;

    public PresenceHandler(Magister magister) {
        this.magister = magister;
        Map<Class<?>, TypeAdapter<?>> map = new HashMap<Class<?>, TypeAdapter<?>>();
        map.put(Presence[].class, new PresenceAdapter());
        map.put(PresencePeriod[].class, new PresencePeriodAdapter());
        gson = GsonUtil.getGsonWithAdapters(map);
    }

    /**
     * Get an array with all {@link Presence} data of the current study. If no data can be found, an empty array will
     * be returned instead.
     *
     * @return an array with all the {@link Presence} data.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Presence[] getPresence() throws IOException, PrivilegeException {
        return getPresence(null);
    }

    /**
     * Get an array with all {@link Presence} data of a specific period. If no data can be found, an empty array will
     * be returned instead.
     *
     * @param period the {@link PresencePeriod}.
     * @return an array with all the {@link Presence} data.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Presence[] getPresence(PresencePeriod period) throws IOException, PrivilegeException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/absenties?tot=" + (period == null ? magister.currentStudy.startDate : period.start) + "&van=" + (period == null ? magister.currentStudy.endDate : period.end)), Presence[].class);
    }

    /**
     * Get an array with all the {@link PresencePeriod}s of this profile. If no data can be found, an empty array will
     * be returned instead.
     *
     * @return an array with all the {@link PresencePeriod}s.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public PresencePeriod[] getPresencePeriods() throws IOException, PrivilegeException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/absentieperioden"), PresencePeriod[].class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "Absenties";
    }
}
