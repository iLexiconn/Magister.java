package net.ilexiconn.magister.cache;

import java.util.ArrayList;
import java.util.List;

public class ContainerCache {
    private static List<ICachable> list = new ArrayList<ICachable>();

    public static <T extends ICachable> T get(String id, Class<T> type) {
        for (ICachable cachable : list) { //Skip 'has' method for better performance.
            if (cachable.getType() == type && cachable.getID().equals(id)) {
                return (T) cachable;
            }
        }
        return null;
    }

    public static <T extends ICachable> boolean has(String id, Class<T> type) {
        for (ICachable cachable : list) {
            if (cachable.getType() == type && cachable.getID().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public static void add(ICachable cachable) {
        list.add(cachable);
    }

    public static void remove(ICachable cachable) {
        list.remove(cachable);
    }
}
