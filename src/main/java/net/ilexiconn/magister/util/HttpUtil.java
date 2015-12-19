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

import net.ilexiconn.magister.Magister;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static CookieManager cookieManager = new CookieManager();

    public static InputStreamReader httpDelete(String url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Cookie", getCurrentCookies());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.connect();
        storeCookies(connection);
        return new InputStreamReader(connection.getInputStream());
    }

    public static InputStreamReader httpPut(String url, String json) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Cookie", getCurrentCookies());
        connection.setRequestProperty("Content-Type", "application/json");
        byte[] data_url = json.getBytes("UTF-8");
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(data_url);
        outputStream.flush();
        outputStream.close();
        storeCookies(connection);
        return new InputStreamReader(connection.getInputStream());
    }

    public static InputStreamReader httpPost(String url, Map<String, String> data) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Cookie", getCurrentCookies());
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        byte[] data_url = convertToDataString(data).getBytes("UTF-8");
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(data_url);
        outputStream.flush();
        outputStream.close();
        storeCookies(connection);
        return new InputStreamReader(connection.getInputStream());
    }

    public static InputStreamReader httpPostRaw(String url, String json) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Cookie", getCurrentCookies());
        connection.setRequestProperty("Content-Type", "application/json");
        byte[] data_url = json.getBytes("UTF-8");
        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        outputStream.write(data_url);
        outputStream.flush();
        outputStream.close();
        storeCookies(connection);
        return new InputStreamReader(connection.getInputStream());
    }

    public static InputStreamReader httpPostFile(Magister m, File file) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(m.school.url + "/api/file").openConnection();

        String boundary = Long.toHexString(System.currentTimeMillis());
        String lineEnd = "\r\n";
        String twoHyphens = "--";

        connection.setRequestMethod("POST");
        connection.setRequestProperty("Cookie", HttpUtil.getCurrentCookies());
        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Cache-Control", "no-cache");
        connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        DataOutputStream dos = new DataOutputStream(connection.getOutputStream());
        FileInputStream fis = new FileInputStream(file);

        dos.writeBytes(twoHyphens + boundary + lineEnd);
        dos.writeBytes("Content-Disposition: form-data; name=\"file\";" + " filename=\"" + file.getName() + "\"" + lineEnd);
        dos.writeBytes(lineEnd);

        int bytesAvailable = fis.available();
        int bufferSize = Math.min(bytesAvailable, 1024);
        byte[] buffer = new byte[bufferSize];
        int bytesRead = fis.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dos.write(buffer, 0, bufferSize);
            bytesAvailable = fis.available();
            bufferSize = Math.min(bytesAvailable, 1024);
            bytesRead = fis.read(buffer, 0, bufferSize);
        }

        dos.writeBytes(lineEnd);
        dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        fis.close();
        dos.flush();
        dos.close();
        return new InputStreamReader(connection.getInputStream());
    }

    public static InputStreamReader httpGet(String url) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", getCurrentCookies());
        if (AndroidUtil.getAndroidSupportCache()) {
            connection.setUseCaches(true);
        }
        connection.connect();
        storeCookies(connection);
        return new InputStreamReader(connection.getInputStream());
    }

    public static File httpGetFile(String url, File downloadDir) throws IOException {
        HttpsURLConnection connection = (HttpsURLConnection) new URL(url).openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Cookie", getCurrentCookies());
        if (AndroidUtil.getAndroidSupportCache()) {
            connection.setUseCaches(true);
        }
        String disposition = connection.getHeaderField("Content-Disposition");
        String fileName = disposition.substring(disposition.indexOf("filename=") + 10, disposition.length() - 1);
        File target = new File(downloadDir.getPath() + "\\" + fileName);
        copyFileUsingStream(connection.getInputStream(), target);
        connection.connect();
        storeCookies(connection);
        return target.getAbsoluteFile();
    }

    private static void copyFileUsingStream(InputStream is, File dest) throws IOException {
        if (is == null || dest == null) {
            return;
        }
        OutputStream os = null;
        try {
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            if (os != null) {
                os.close();
            }
        }
    }

    private static void storeCookies(HttpURLConnection connection) {
        Map<String, List<String>> headers = connection.getHeaderFields();
        List<String> cookies = headers.get("Set-Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                cookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
            }
        }
    }

    public static String getCurrentCookies() {
        String result = "";
        for (HttpCookie cookie : cookieManager.getCookieStore().getCookies()) {
            result = result.concat(cookie.toString() + ";");
        }
        return result;
    }

    private static String convertToDataString(Map<String, String> data) throws UnsupportedEncodingException {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry entry : data.entrySet()) {
            builder.append(URLEncoder.encode(entry.getKey().toString(), "UTF-8")).append("=").append(URLEncoder.encode(entry.getValue().toString(), "UTF-8")).append("&");
        }
        String result = builder.toString();
        return result.length() > 0 ? result.substring(0, result.length() - 1) : "";
    }

    public static String convertInputStreamReaderToString(InputStreamReader r) throws IOException {
        BufferedReader reader = new BufferedReader(r);
        StringBuilder responseBuilder = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            responseBuilder.append(line);
        }
        return responseBuilder.toString();
    }
}