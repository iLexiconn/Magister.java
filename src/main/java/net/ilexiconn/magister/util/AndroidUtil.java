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

import java.lang.reflect.InvocationTargetException;

public class AndroidUtil {
    private static boolean runningOnAndroid = false;
    private static boolean androidSupportCache = false;

    /**
     * Check if the API is running on Android.
     */
    public static void checkAndroid() {
        try {
            Class.forName("android.view.View");
            runningOnAndroid = true;
            isCacheAvailableOnAndroid();
        } catch (ClassNotFoundException e) {
            runningOnAndroid = false;
        }
    }

    /**
     * Check if the API is running on Android, and if it can use the Android caching utilities.
     *
     * @return true if the API is running on Android and can use the Android caching utilities.
     */
    public static boolean isCacheAvailableOnAndroid() {
        try {
            Class<?> cache = Class.forName("android.net.http.HttpResponseCache");
            cache.getMethod("getInstalled").invoke(null);
            androidSupportCache = true;
        } catch (ClassNotFoundException e) {
            LogUtil.printError("Could not find Class: android.net.http.HttpResponseCache", e.getCause());
            androidSupportCache = false;
        } catch (NoSuchMethodException e) {
            LogUtil.printError("Could not find Method: getInstalled", e.getCause());
            androidSupportCache = false;
        } catch (IllegalAccessException e) {
            LogUtil.printError("Could not access Method: getInstalled", e.getCause());
            androidSupportCache = false;
        } catch (InvocationTargetException e) {
            LogUtil.printError("Failed to invoke Method: getInstalled", e.getCause());
            androidSupportCache = false;
        }
        return androidSupportCache;
    }

    public static boolean getRunningOnAndroid() {
        return runningOnAndroid;
    }

    public static boolean getAndroidSupportCache() {
        return androidSupportCache;
    }
}
