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

import net.ilexiconn.magister.container.School;
import net.ilexiconn.magister.util.android.ImageContainer;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ProfileTest {
    @Test
    public void login() {
        try {
            MagisterTest.magister = Magister.login(School.findSchool(System.getProperty("school"))[0], System.getProperty("username"), System.getProperty("password"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        assertEquals("active", MagisterTest.magister.session.state);
    }

    @Test
    public void changePassword() {
        String current = MagisterTest.magister.user.password;
        String response1 = null;
        String response2 = null;
        try {
            response1 = MagisterTest.magister.changePassword(current, current + "-test", current + "-test");
            response2 = MagisterTest.magister.changePassword(current + "-test", current, current);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(response1, "Successful");
        assertEquals(response2, "Successful");
    }

    @Test
    public void profileImage() {
        ImageContainer ic = null;
        try {
            ic = MagisterTest.magister.getImage();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertNotNull(ic);
    }
}
