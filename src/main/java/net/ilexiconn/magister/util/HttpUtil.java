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

import org.apache.http.NameValuePair;
import org.apache.http.client.cache.HttpCacheContext;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.cache.CachingHttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {
    private static CloseableHttpClient httpClient = CachingHttpClientBuilder.create().build();

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static InputStreamReader httpDelete(String url) throws IOException {
        if (AndroidUtil.getRunningOnAndroid()) {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("DELETE");
            conn.connect();
            return new InputStreamReader(conn.getInputStream());
        } else {
            HttpDelete delete = new HttpDelete(url);
            HttpCacheContext context = HttpCacheContext.create();
            CloseableHttpResponse response = getHttpClient().execute(delete, context);
            return getReader(response);
        }
    }

    public static InputStreamReader httpPost(String url, Map<String, String> nameValuePairMap) throws IOException {
        if (AndroidUtil.getRunningOnAndroid()) {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            StringBuilder parameters = new StringBuilder();
            for (String key : nameValuePairMap.keySet()) {
                parameters.append(key).append("=").append(nameValuePairMap.get(key)).append("&");
            }
            DataOutputStream dataOut = new DataOutputStream(conn.getOutputStream());
            dataOut.write(parameters.substring(0, parameters.length() - 1).getBytes());
            return new InputStreamReader(conn.getInputStream());
        } else {
            HttpPost post = new HttpPost(url);
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();
            for (String key : nameValuePairMap.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, nameValuePairMap.get(key)));
            }
            post.setEntity(new UrlEncodedFormEntity(nameValuePairList));
            HttpCacheContext context = HttpCacheContext.create();
            CloseableHttpResponse response = getHttpClient().execute(post, context);
            return getReader(response);
        }
    }

    public static InputStreamReader httpGet(String url) throws IOException {
        if (AndroidUtil.getRunningOnAndroid()) {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            if (AndroidUtil.getAndroidSupportCache()) {
                conn.setUseCaches(true);
            }
            conn.connect();
            return new InputStreamReader(conn.getInputStream());
        } else {
            HttpGet get = new HttpGet(url);
            HttpCacheContext context = HttpCacheContext.create();
            CloseableHttpResponse response = getHttpClient().execute(get, context);
            return getReader(response);
        }
    }

    public static InputStreamReader getReader(CloseableHttpResponse response) throws IOException {
        return new InputStreamReader(response.getEntity().getContent());
    }
}
