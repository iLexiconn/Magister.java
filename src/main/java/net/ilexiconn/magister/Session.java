package net.ilexiconn.magister;

public class Session {
    private String id;
    private String state;
    private boolean isVerified;
    private Links[] links;

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public Links[] getLinks() {
        return links;
    }

    public class Links {
        private String href;
        private String rel;

        public String getHref() {
            return href;
        }

        public String getRel() {
            return rel;
        }
    }
}
