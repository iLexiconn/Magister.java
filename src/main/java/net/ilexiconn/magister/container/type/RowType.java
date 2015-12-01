package net.ilexiconn.magister.container.type;

public enum RowType {
    NULL(0),
    UNKNOWN(1),
    AVERAGE(2),
    NORMAL(3);

    private int id;

    private RowType(int i) {
        id = i;
    }

    public static RowType getTypeById(int i) {
        for (RowType type : values()) {
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
