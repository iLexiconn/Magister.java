package net.ilexiconn.magister.container.type;

public enum DisplayType {
    AVAILABLE(1),
    PROVISIONALLY_SCHEDULED(2),
    SCHEDULED(3),
    ABSENT(4);

    private int id;

    DisplayType(int i) {
        id = i;
    }

    public static DisplayType getTypeById(int i) {
        for (DisplayType type : values()) {
            if (type.getID() == i) {
                return type;
            }
        }
        return null;
    }

    public int getID() {
        return id;
    }
}
