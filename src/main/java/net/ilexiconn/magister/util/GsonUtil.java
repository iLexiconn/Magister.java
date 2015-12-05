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

package net.ilexiconn.magister.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;

import java.util.HashMap;
import java.util.Map;

public class GsonUtil {
    private static Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    public static Gson getGsonWithAdapter(Class<?> type, TypeAdapter<?> adapter) {
        Map<Class<?>, TypeAdapter<?>> map = new HashMap<Class<?>, TypeAdapter<?>>();
        map.put(type, adapter);
        return getGsonWithAdapters(map);
    }

    public static Gson getGsonWithAdapters(Map<Class<?>, TypeAdapter<?>> map) {
        GsonBuilder builder = new GsonBuilder();
        for (Map.Entry<Class<?>, TypeAdapter<?>> entry : map.entrySet()) {
            builder.registerTypeAdapter(entry.getKey(), entry.getValue());
        }
        return builder.create();
    }
}
