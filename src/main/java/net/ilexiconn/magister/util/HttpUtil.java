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
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class HttpUtil {
    private static CloseableHttpClient httpClient = HttpClients.createDefault();

    public static CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static InputStreamReader httpDelete(String url) throws IOException {
        HttpDelete delete = new HttpDelete(url);
        CloseableHttpResponse response = getHttpClient().execute(delete);
        return getReader(response);
    }

    public static InputStreamReader httpPost(String url, List<NameValuePair> nameValuePairList) throws IOException {
        HttpPost post = new HttpPost(url);
        post.setEntity(new UrlEncodedFormEntity(nameValuePairList));
        CloseableHttpResponse response = getHttpClient().execute(post);
        return getReader(response);
    }

    public static InputStreamReader httpGet(String url) throws IOException {
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = getHttpClient().execute(get);
        return getReader(response);
    }

    public static InputStreamReader getReader(CloseableHttpResponse response) throws IOException {
        return new InputStreamReader(response.getEntity().getContent());
    }
}
