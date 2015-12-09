/*
 * Copyright (c) 2015 iLexiconn
 *
 *  Permission is hereby granted, free of charge, to any person
 *  obtaining a copy of this software and associated documentation
 *  files (the "Software"), to deal in the Software without
 *  restriction, including without limitation the rights to use,
 *  copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the
 *  Software is furnished to do so, subject to the following
 *  conditions:
 *
 *  The above copyright notice and this permission notice shall be
 *  included in all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 *  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 *  OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *  FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *  OTHER DEALINGS IN THE SOFTWARE.
 */

package net.ilexiconn.magister.container.type;

import java.io.Serializable;

public enum AppointmentType implements Serializable {
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

    AppointmentType(int i) {
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
