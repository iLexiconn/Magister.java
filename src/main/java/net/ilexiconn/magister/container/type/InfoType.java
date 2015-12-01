package net.ilexiconn.magister.container.type;

public enum InfoType {
    NONE(0),
    HOMEWORK(1),
    TEST(2),
    EXAM(3),
    QUIZ(4),
    ORAL(5),
    INFORMATION(6),
    ANNOTATION(7);

    private int id;

    private InfoType(int i) {
        id = i;
    }

    public int getID() {
        return id;
    }

    public static InfoType getTypeById(int i) {
        for (InfoType type : values()) {
            if (type.getID() == i) {
                return type;
            }
        }
        return null;
    }
}