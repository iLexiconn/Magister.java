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
import net.ilexiconn.magister.adapter.ArrayAdapter;
import net.ilexiconn.magister.container.elo.Source;
import net.ilexiconn.magister.util.GsonUtil;
import net.ilexiconn.magister.util.HttpUtil;

import java.io.IOException;

public class ELOHandler implements IHandler {
    public Gson gson = GsonUtil.getGsonWithAdapter(Source[].class, new ArrayAdapter<Source>(Source.class, Source[].class));
    private Magister magister;

    public ELOHandler(Magister magister) {
        this.magister = magister;
    }

    public Source[] getSources() throws IOException {
        return gson.fromJson(HttpUtil.httpGet(magister.school.url + "/api/personen/" + magister.profile.id + "/bronnen?soort=0"), Source[].class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getPrivilege() {
        return "EloOpdracht";
    }
}
