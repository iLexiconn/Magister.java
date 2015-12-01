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

package net.ilexiconn.magister;

import net.ilexiconn.magister.container.Appointment;
import net.ilexiconn.magister.container.Grade;
import net.ilexiconn.magister.container.School;
import net.ilexiconn.magister.container.sub.Privilege;

import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws Exception {
        School school = null;
        if (args.length == 2) {
            Scanner scanner = new Scanner(System.in);
            while (school == null) {
                System.out.print("Please enter your school's name: ");
                String name = scanner.next();
                School[] schools = School.findSchool(name);
                if (schools.length > 1) {
                    for (int i = 0; i < schools.length; i++) {
                        System.out.println((i + 1) + ". " + schools[i].name);
                    }
                    int id = 0;
                    while (id <= 0 || id > schools.length) {
                        System.out.print("Please select your school: ");
                        try {
                            id = Integer.parseInt(scanner.next());
                        } catch (NumberFormatException e) {
                            id = 0;
                        }
                    }
                    school = schools[id - 1];
                } else if (schools.length == 1) {
                    school = schools[0];
                }
            }
        } else if (args.length == 3) {
            school = School.findSchool(args[0])[0];
        } else {
            return;
        }

        System.out.println("Using school " + school.name);
        Magister magister = Magister.login(school, args[args.length == 2 ? 0 : 1], args[args.length == 2 ? 1 : 2]);

        if (magister != null) {
            System.out.println("Hey, " + magister.profile.nickname + "!");

            System.out.println("======== Appointments ========");
            for (Appointment appointment : magister.getAppointmentsOfToday()) {
                System.out.println(appointment.description);
            }

            System.out.println("=========== Grades ===========");
            for (Grade grade : magister.getAllGrades()) {
                System.out.println(grade.course.name + ": " + grade.grade);
            }

            System.out.println("========= Privileges =========");
            for (Privilege privilege : magister.profile.privileges) {
                System.out.println(privilege.name);
            }
        }
    }
}