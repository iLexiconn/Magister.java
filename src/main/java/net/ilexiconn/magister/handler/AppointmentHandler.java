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
import com.google.gson.JsonParser;
import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.adapter.AppointmentAdapter;
import net.ilexiconn.magister.container.Appointment;
import net.ilexiconn.magister.container.PersonalAppointment;
import net.ilexiconn.magister.container.PresencePeriod;
import net.ilexiconn.magister.exeption.PrivilegeException;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AppointmentHandler implements IHandler {
    private Gson gson = GsonUtil.getGsonWithAdapter(Appointment[].class, new AppointmentAdapter());
    private Magister magister;

    public AppointmentHandler(Magister magister) {
        this.magister = magister;
    }

    /**
     * Get an array with all the {@link Appointment}s of this {@link PresencePeriod}. If no appointments can be found,
     * an empty array will be returned instead.
     *
     * @param period the {@link PresencePeriod} of the {@link Appointment}s.
     * @return an array with the {@link Appointment}s of this period.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Appointment[] getAppointments(PresencePeriod period) throws IOException {
        return getAppointments(period.startDate, period.endDate);
    }

    /**
     * Get an array with all the {@link Appointment}s. If no appointments can be found, an empty array will be returned
     * instead.
     *
     * @param from  the start date.
     * @param until the end date.
     * @return an array with the {@link Appointment}s of this date period.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Appointment[] getAppointments(Date from, Date until) throws IOException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        String dateNow = format.format(from);
        String dateFrom = format.format(until);
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/afspraken?status=0&van=" + dateNow + "&tot=" + dateFrom), Appointment[].class);
    }

    /**
     * Get an array with all the {@link Appointment}s of today. If no appointments can be found, an empty array will be
     * returned
     * instead.
     *
     * @return an array with the {@link Appointment}s of this date period.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public Appointment[] getAppointmentsOfToday() throws IOException {
        Date now = new Date();
        return getAppointments(now, now);
    }

    /**
     * Adds an appointment to magister. It wil return the Url of this Appointment, if it fails null will be
     * returned
     *
     * @return an string with the url of the added appointment.
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public String createAppointment(PersonalAppointment appointment) throws IOException {
        String data = gson.toJson(appointment);
        InputStreamReader respose = HttpUtil.httpPostRaw(magister.school.url + "/api/personen/" + magister.profile.id + "/afspraken", data);
        BufferedReader reader = new BufferedReader(respose);
        String s;
        StringBuilder sb = new StringBuilder();
        while ((s = reader.readLine()) != null) {
            sb.append(s);
        }
        return magister.school.url + (new JsonParser().parse(sb.toString()).getAsJsonObject().get("Url").getAsString());
    }

    /**
     * Deletes an appointment from magister.
     *
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public void removeAppointment(Appointment appointment) throws IOException {
        removeAppointment(appointment.id);
    }

    /**
     * Deletes an appointment from magister using the url.
     *
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public void removeAppointment(String url) throws IOException {
        HttpUtil.httpDelete(url);
    }

    /**
     * Deletes an appointment from magister.
     *
     * @throws IOException        if there is no active internet connection.
     * @throws PrivilegeException if the profile doesn't have the privilege to perform this action.
     */
    public void removeAppointment(int id) throws IOException {
        HttpUtil.httpDelete(magister.school.url + "/api/personen/" + magister.profile.id + "/afspraken/" + id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "Afspraken";
    }
}
