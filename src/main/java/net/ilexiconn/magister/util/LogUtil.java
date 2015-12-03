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

package net.ilexiconn.magister.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class LogUtil {
    private static String TAG = "MAGISTER.JAVA";

    public static void printError(String description, Throwable throwable) {
        String details = toPrettyString("Operating System", System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ")") + toPrettyString("Java Version", System.getProperty("java.version"));
        String report = "---- Error Report ----\n" + description + "\n\n-- Crash Log --\n" + getStackTrace(throwable) + "\n-- System Details --\n" + details;
        if (AndroidUtil.getRunningOnAndroid()) {
            try {
                Class logClass = Class.forName("android.util.Log");
                logClass.getMethod("e", String.class, String.class, Throwable.class).invoke(null, TAG, description, throwable);
            } catch (Exception e) {
                System.err.println(report);
            } finally {
                return;
            }
        }
        System.err.println(report);
    }

    private static String toPrettyString(String key, String value) {
        return key + "\n\t" + value + "\n";
    }

    private static String getStackTrace(Throwable throwable) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        throwable.printStackTrace(printWriter);
        String s = stringWriter.toString();

        try {
            stringWriter.close();
            printWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return s;
    }

    public static void setAndroidTag(String s) {
        TAG = s;
    }
}
