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

import net.ilexiconn.magister.Magister;
import net.ilexiconn.magister.container.Grade;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.IOException;

public class GradeHandler implements IHandler {
    private Magister magister;

    public GradeHandler(Magister magister) {
        this.magister = magister;
    }

    /**
     * Get an array of {@link Grade}s. If no grades can be found, an empty array
     * will be returned instead.
     *
     * @param onlyAverage only count the average grades.
     * @param onlyPTA only count the PTA grades.
     * @param onlyActiveStudy only check the current study.
     * @return an array of {@link Grade}s.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getGrades(boolean onlyAverage, boolean onlyPTA, boolean onlyActiveStudy) throws IOException {
        return magister.gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/aanmeldingen/" + magister.currentStudy.id + "/cijfers/cijferoverzichtvooraanmelding?alleenBerekendeKolommen=" + onlyAverage + "&alleenPTAKolommen=" + onlyPTA + "&actievePerioden=" + onlyActiveStudy), Grade[].class);
    }

    /**
     * Get an array of all the {@link Grade}s this profile hs ever got.
     *
     * @return an array of all the {@link Grade}s this profile hs ever got.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getAllGrades() throws IOException {
        return getGrades(false, false, false);
    }

    /**
     * {@inheritDoc}
     */
    public String getPrivilege() {
        return "Cijfers";
    }
}
