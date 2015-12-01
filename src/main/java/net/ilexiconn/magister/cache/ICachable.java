package net.ilexiconn.magister.cache;

public interface ICachable {
    String getID();

    Class<? extends ICachable> getType();
}
