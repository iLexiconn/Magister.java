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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Magister {
    private CloseableHttpClient httpClient = HttpClients.createDefault();
    private Gson gson = new Gson();

    private School school;
    private String username;
    private String password;

    private Version version;
    private Session session;
    private Profile profile;
    private Study study;
    private Study.Items currentStudy;

    public Magister(School school, String username, String password) {
        if (school != null) setSchool(school);
        if (username != null && password != null) setUser(username, password);
    }

    public Magister(String username, String password) {
        this(null, username, password);
    }

    public Magister(School school) {
        this(school, null, null);
    }

    public Magister() {
        this(null, null, null);
    }

    public void setUser(String u, String p) {
        username = u;
        password = p;
    }

    public void setSchool(School s) {
        school = s;
    }

    public School[] findSchool(String s) {
        try {
            return gson.fromJson(new InputStreamReader(new URL("https://mijn.magister.net/api/schools?filter=" + s).openStream()), School[].class);
        } catch (IOException e) {
            e.printStackTrace();
            return new School[0];
        }
    }

    public void login() throws IOException {
        if (school != null && !username.isEmpty() && !password.isEmpty()) {
            HttpGet get = new HttpGet(school.getUrl() + "/api/versie");
            CloseableHttpResponse responseGet = httpClient.execute(get);
            version = gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Version.class);
            System.out.println("Connecting to Magister version " + version.getProductVersion() + "...");
            HttpDelete delete = new HttpDelete(school.getUrl() + "/api/sessies/huidige");
            delete.addHeader(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
            httpClient.execute(delete);
            HttpPost post = new HttpPost(school.getUrl() + "/api/sessies");
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            nvps.add(new BasicNameValuePair("Gebruikersnaam", username));
            nvps.add(new BasicNameValuePair("Wachtwoord", password));
            post.setEntity(new UrlEncodedFormEntity(nvps));
            CloseableHttpResponse responsePost = httpClient.execute(post);
            session = gson.fromJson(new InputStreamReader(responsePost.getEntity().getContent()), Session.class);
            if (!session.isVerified() || !session.getState().equals("active")) {
                System.err.println("Invalid session, check credentials.");
                return;
            }
            get = new HttpGet(school.getUrl() + "/api/account");
            responseGet = httpClient.execute(get);
            profile = gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Profile.class);
            get = new HttpGet(school.getUrl() + "/api/personen/" + profile.getPerson().getId() + "/aanmeldingen");
            responseGet = httpClient.execute(get);
            study = gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Study.class);
            LocalDate now = LocalDate.now();
            for (Study.Items item : study.getItems()) {
                if (LocalDate.parse(item.getEnd().substring(0, 10)).isAfter(now)) {
                    currentStudy = item;
                }
            }
        }
    }

    public Version getVersion() {
        return version;
    }

    public Session getSession() {
        return session;
    }

    public Profile getProfile() {
        return profile;
    }

    public Study getStudy() {
        return study;
    }

    public Study.Items getCurrentStudy() {
        return currentStudy;
    }

    public Mark.Items[] getMarks() throws IOException {
        return getMarks(null);
    }

    public Mark.Items[] getMarks(String subject) throws IOException {
        HttpGet get = new HttpGet(school.getUrl() + "/api/personen/" + profile.getPerson().getId() + "/aanmeldingen/" + currentStudy.getId() + "/cijfers/cijferoverzichtvooraanmelding?actievePerioden=" + true + "&alleenBerekendeKolommen=" + false + "&alleenPTAKolommen=" + false);
        CloseableHttpResponse responseGet = httpClient.execute(get);
        Mark.Items[] items = gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Mark.class).getItems();
        if (subject == null) return items;
        List<Mark.Items> itemsList = new ArrayList<Mark.Items>();
        for (Mark.Items item : items)
            if (item.getSubject().getAbbreviation().equals(subject)) itemsList.add(item);
        return itemsList.toArray(new Mark.Items[itemsList.size()]);
    }

    public BufferedImage getImage() throws IOException {
        return getImage(42, 64, false);
    }

    public BufferedImage getImage(int width, int height, boolean crop) throws IOException {
        HttpGet get = new HttpGet("https://weert.magister.net/api/personen/" + profile.getPerson().getId() + "/foto" + (width != 42 || height != 64 || crop ? "?width=" + width + "&height=" + height + "&crop=" + crop : ""));
        CloseableHttpResponse responseGet = httpClient.execute(get);
        return ImageIO.read(responseGet.getEntity().getContent());
    }

    public Homework.Items[] getHomework() throws IOException {
        return getHomework(null, null);
    }

    public Homework.Items[] getHomework(Calendar from, Calendar to) throws IOException {
        SimpleDateFormat format = new SimpleDateFormat("Y-m-d");
        HttpGet get = new HttpGet(school.getUrl() + "/api/personen/" + profile.getPerson().getId() + "/afspraken" + (from == null || to == null ? "" : "?van=" + format.format(from.getTime()) + "&tot=" + format.format(to.getTime())));
        CloseableHttpResponse responseGet = httpClient.execute(get);
        return gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Homework.class).getItems();
    }

    public Subject[] getSubjects() throws IOException {
        HttpGet get = new HttpGet(school.getUrl() + "/api/personen/" + profile.getPerson().getId() + "/aanmeldingen/" + currentStudy.getId() + "/vakken");
        CloseableHttpResponse responseGet = httpClient.execute(get);
        return gson.fromJson(new InputStreamReader(responseGet.getEntity().getContent()), Subject[].class);
    }
}
