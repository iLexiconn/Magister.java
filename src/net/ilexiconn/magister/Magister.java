package net.ilexiconn.magister;

import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Magister
{
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private Gson gson = new Gson();

    private School school;
    private String username;
    private String password;

    private Session session;
    private Profile profile;

    public Magister(School school, String username, String password)
    {
        if (school != null) setSchool(school);
        if (username != null && password != null) setUser(username, password);
    }

    public Magister(String username, String password)
    {
        this(null, username, password);
    }

    public Magister(School school)
    {
        this(school, null, null);
    }

    public Magister()
    {
        this(null, null, null);
    }

    public void setUser(String u, String p)
    {
        username = u;
        password = p;
    }

    public void setSchool(School s)
    {
        school = s;
    }

    public School[] findSchool(String s)
    {
        try
        {
            return gson.fromJson(new InputStreamReader(new URL("https://mijn.magister.net/api/schools?filter=" + s).openStream()), School[].class);
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return new School[0];
        }
    }

    public void login() throws IOException
    {
        if (school != null && !username.isEmpty() && !password.isEmpty())
        {
            HttpDelete delete = new HttpDelete(school.getUrl() + "/api/sessies/huidige");
            delete.addHeader(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
            httpClient.execute(delete);

            HttpPost post = new HttpPost(school.getUrl() + "/api/sessies");
            List<NameValuePair> nvps = new ArrayList<>();
            nvps.add(new BasicNameValuePair("Gebruikersnaam", username));
            nvps.add(new BasicNameValuePair("Wachtwoord", password));
            post.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse responsePost = httpClient.execute(post);

            session = gson.fromJson(new InputStreamReader(responsePost.getEntity().getContent()), Session.class);

            if (!session.isVerified() || !session.getState().equals("active"))
            {
                System.err.println("Invalid session, check credentials.");
                return;
            }

            HttpGet get = new HttpGet(school.getUrl() + "/api/account");
            CloseableHttpResponse responseGet = httpClient.execute(get);

            profile = gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Profile.class);
        }
    }

    public Session getSession()
    {
        return session;
    }

    public Profile getProfile()
    {
        return profile;
    }
}
