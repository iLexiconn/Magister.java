package net.ilexiconn.magister.container.type;

public enum AppointmentType {
    PERSONAL(1),
    GENERAL(2),
    SCHOOLWIDE(3),
    INTERNSHIP(4),
    INTAKE(4),
    SHEDULE_FREE(5),
    KWT(6),
    STANDBY(7),
    BLOCK(8),
    MISCELLANEOUS(9),
    LOCAL_BLOCK(10),
    CLASS_BLOCK(11),
    LESSON(12),
    //STUDIEHUIS(13),
    SHEDULE_FREE_STUDY(14),
    PLANNING(15),
    ACTIONS(101),
    PRESENCES(102),
    EXAM_SHUDULE(103);

    private int id;

    private AppointmentType(int i) {
        id = i;
    }

    public static AppointmentType getTypeById(int i) {
        for (AppointmentType type : values()) {
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
