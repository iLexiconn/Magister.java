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
import net.ilexiconn.magister.adapter.ArrayAdapter;
import net.ilexiconn.magister.adapter.SingleStudyGuideAdapter;
import net.ilexiconn.magister.container.SingleStudyGuide;
import net.ilexiconn.magister.container.Source;
import net.ilexiconn.magister.container.StudyGuide;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ELOHandler implements IHandler {
    public Gson gson;
    private Magister magister;

    public ELOHandler(Magister magister) {
        this.magister = magister;
        Map<Class<?>, TypeAdapter<?>> map = new HashMap<Class<?>, TypeAdapter<?>>();
        map.put(Source[].class, new ArrayAdapter<Source>(Source.class, Source[].class));
        map.put(StudyGuide[].class, new ArrayAdapter<StudyGuide>(StudyGuide.class, StudyGuide[].class));
        map.put(SingleStudyGuide.class, new SingleStudyGuideAdapter());
        gson = GsonUtil.getGsonWithAdapters(map);
    }

    /**
     * Get all the ELO sources for this profile.
     *
     * @return all the ELO sources for this profile.
     * @throws IOException if there is no active internet connection.
     */
    public Source[] getSources() throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.schoolUrl.getApiUrl() + "personen/" + magister.profile.id + "/bronnen?soort=0"), Source[].class);
    }

    /**
     * Get all study guides for this profile.
     *
     * @return all study guides for this profile.
     * @throws IOException if there is no active internet connection.
     */
    public StudyGuide[] getStudyGuides() throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.schoolUrl.getApiUrl() + "leerlingen/" + magister.profile.id + "/studiewijzers"), StudyGuide[].class);
    }

    /**
     * Get more data about a specific study guide.
     *
     * @param studyGuide the study guide.
     * @return an object with more data about the study guide.
     * @throws IOException if there is no active internet connection.
     */
    public SingleStudyGuide getStudyGuide(StudyGuide studyGuide) throws IOException {
        return getStudyGuide(studyGuide.id);
    }

    /**
     * Get more data about a specific study guide.
     *
     * @param studyGuideID the study guide id.
     * @return an object with more data about the study guide.
     * @throws IOException if there is no active internet connection.
     */
    public SingleStudyGuide getStudyGuide(int studyGuideID) throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.schoolUrl.getApiUrl() + "leerlingen/" + magister.profile.id + "/studiewijzers/" + studyGuideID), SingleStudyGuide.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "EloOpdracht";
    }
}
