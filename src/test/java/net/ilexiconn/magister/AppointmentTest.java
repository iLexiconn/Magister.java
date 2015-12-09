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

package net.ilexiconn.magister;

import net.ilexiconn.magister.container.PersonalAppointment;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.handler.AppointmentHandler;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

public class AppointmentTest {
    @Test
    public void appointment() {
        AppointmentHandler appointmentHandler = MagisterTest.magister.getHandler(AppointmentHandler.class);
        Date date = new Date();
        Date end = new Date();
        end.setTime(date.getTime() + 1000);
        PersonalAppointment appointment = null;
        try {
            appointment = new PersonalAppointment("AppointmentName", "AppointmentBody", "AppointmentLocation", AppointmentType.PERSONAL, date, end);
            appointmentHandler.createAppointment(appointment);
            appointmentHandler.removeAppointment(appointment.id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertNotNull(appointment);
    }
}
