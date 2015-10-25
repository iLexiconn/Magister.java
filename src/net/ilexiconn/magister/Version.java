package net.ilexiconn.magister;

public class Version {
    private String ApiVersie;
    private String CoreVersie;
    private String DatabaseVersie;
    private String ProductVersie;
    private String ReleaseDatum;

    public String getApiVersion() {
        return ApiVersie;
    }

    public String getCoreVersion() {
        return CoreVersie;
    }

    public String getDatabaseVersion() {
        return DatabaseVersie;
    }

    public String getProductVersion() {
        return ProductVersie;
    }

    public String getReleaseDate() {
        return ReleaseDatum;
    }
}
