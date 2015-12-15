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
import net.ilexiconn.magister.adapter.GradeAdapter;
import net.ilexiconn.magister.container.Grade;
import net.ilexiconn.magister.container.SingleGrade;
import net.ilexiconn.magister.container.sub.SubSubject;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradeHandler implements IHandler {
    private Gson gson = GsonUtil.getGsonWithAdapter(Grade[].class, new GradeAdapter());
    private Magister magister;

    public GradeHandler(Magister magister) {
        this.magister = magister;
    }

    /**
     * Get an array of {@link Grade}s. If no grades can be found, an empty array
     * will be returned instead.
     *
     * @param onlyAverage     only count the average grades.
     * @param onlyPTA         only count the PTA grades.
     * @param onlyActiveStudy only check the current study.
     * @return an array of {@link Grade}s.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getGrades(boolean onlyAverage, boolean onlyPTA, boolean onlyActiveStudy) throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.schoolUrl.getApiUrl() + "personen/" + magister.profile.id + "/aanmeldingen/" + magister.currentStudy.id + "/cijfers/cijferoverzichtvooraanmelding?alleenBerekendeKolommen=" + onlyAverage + "&alleenPTAKolommen=" + onlyPTA + "&actievePerioden=" + onlyActiveStudy), Grade[].class);
    }

    /**
     * Get an array of all the {@link Grade}s this profile hs ever got.
     *
     * @return an array of all the {@link Grade}s this profile has ever got.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getAllGrades() throws IOException {
        return getGrades(false, false, false);
    }

    /**
     * Get all grades from a period of 7 days.
     *
     * @return all grades from a period of 7 days.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getRecentGrades() throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.schoolUrl.getApiUrl() + "personen/" + magister.profile.id + "/aanmeldingen/" + magister.currentStudy.id + "/cijfers"), Grade[].class);
    }

    /**
     * Get all grades from a specific subject.
     *
     * @param subject          the subject.
     * @param onlyAverage     only count the average grades.
     * @param onlyPTA         only count the PTA grades.
     * @param onlyActiveStudy only check the current study.
     * @return all the grades from the specific subject.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getGradesFromSubject(SubSubject subject, boolean onlyAverage, boolean onlyPTA, boolean onlyActiveStudy) throws IOException {
        List<Grade> gradeList = new ArrayList<Grade>();
        for (Grade grade : getGrades(onlyAverage, onlyPTA, onlyActiveStudy)) {
            if (grade.subject == subject) {
                gradeList.add(grade);
            }
        }
        return gradeList.toArray(new Grade[gradeList.size()]);
    }

    /**
     * Get all grades from a specific subject.
     *
     * @param subject          the subject.
     * @return all the grades from the specific subject.
     * @throws IOException if there is no active internet connection.
     */
    public Grade[] getAllGradesFromSubject(SubSubject subject) throws IOException {
        List<Grade> gradeList = new ArrayList<Grade>();
        for (Grade grade : getAllGrades()) {
            if (grade.subject == subject) {
                gradeList.add(grade);
            }
        }
        return gradeList.toArray(new Grade[gradeList.size()]);
    }

    /**
     * Get more info about a specific grade.
     *
     * @param grade the grade to get more info about.
     * @return a SingleGrade instance.
     * @throws IOException if there is no active internet connection.
     */
    public SingleGrade getSingleGrade(Grade grade) throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.schoolUrl.getApiUrl() + "personen/" + magister.profile.id + "/aanmeldingen/" + magister.currentStudy.id + "/cijfers/extracijferkolominfo/" + grade.id), SingleGrade.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "Cijfers";
    }
}
