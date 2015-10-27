/*
 * Copyright (c) 2015 iLexiconn
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.cache;

import net.ilexiconn.magister.util.Triplet;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ContainerCache {
    private static List<Triplet<String, Class<?>, Object>> cacheList = new ArrayList<>();

    public static <T> T put(Cachable cachable, Class<T> type) {
        cacheList.add(new Triplet<String, Class<?>, Object>(cachable.getId(), type, cachable));
        return (T) cachable;
    }

    public static  <T> T get(String d, Class<T> type) {
        for (Triplet<String, Class<?>, Object> triplet : cacheList) {
            if (Objects.equals(triplet.getA(), d) && triplet.getB() == type) {
                return (T) triplet.getC();
            }
        }
        return null;
    }
}
