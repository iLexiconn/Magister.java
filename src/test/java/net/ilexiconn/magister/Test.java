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

import net.ilexiconn.magister.container.*;
import net.ilexiconn.magister.container.elo.SingleStudyGuide;
import net.ilexiconn.magister.container.elo.Source;
import net.ilexiconn.magister.container.elo.StudyGuide;
import net.ilexiconn.magister.container.elo.StudyGuideItem;
import net.ilexiconn.magister.container.sub.Privilege;
import net.ilexiconn.magister.container.type.AppointmentType;
import net.ilexiconn.magister.handler.*;

import java.util.Date;
import java.util.Scanner;

public class Test {
    @SuppressWarnings( "deprecation" )
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
        } else if (args.length >= 3) {
            school = School.findSchool(args[0])[0];
        } else {
            return;
        }

        System.out.println("Using school " + school.name);
        Magister magister = Magister.login(school, args[args.length == 2 ? 0 : 1], args[args.length == 2 ? 1 : 2]);

        if (magister != null) {
            System.out.println("Hey, " + magister.profile.nickname + "!");
            System.out.println("ID: " + magister.profile.id);

            System.out.println("======= MessageFolders =======");
            MessageHandler messageHandler = magister.getHandler(MessageHandler.class);
            for (MessageFolder messageFolder : messageHandler.getMessageFolders()) {
                System.out.println(messageFolder.naam);
                for (Message message : messageHandler.getMessagesPerFolder(messageFolder)) {
                    System.out.println("=> " + message.topic);
                    for (SingleMessage singleMessage : messageHandler.getSingleMessage(message)) {
                        System.out.println("==> " + singleMessage.content);
                    }
                }
            }

            System.out.println("==== Creating Appointment ====");
            AppointmentHandler appointmentHandler = magister.getHandler(AppointmentHandler.class);
            Date end = new Date();
            end.setHours(end.getHours() + 1);
            PersonalAppointment add = new PersonalAppointment("AppointmentName", "AppointmentBody", "AppointmentLocation", AppointmentType.PERSONAL, new Date(), end);
            String appointmentUrl = appointmentHandler.createAppointment(add);
            System.out.println(appointmentUrl);

            System.out.println("======== Appointments ========");
            for (Appointment appointment : appointmentHandler.getAppointmentsOfToday()) {
                System.out.println(appointment.description);
            }

            System.out.println("=== Removing Appointments ===");
            appointmentHandler.removeAppointment(appointmentUrl);
            System.out.println(appointmentUrl);

            System.out.println("====== Presence Periods ======");
            PresenceHandler presenceHandler = magister.getHandler(PresenceHandler.class);
            for (PresencePeriod presencePeriod : presenceHandler.getPresencePeriods()) {
                System.out.println(presencePeriod.description);
            }

            System.out.println("========== Presence ==========");
            for (Presence presence : presenceHandler.getPresence()) {
                System.out.println(presence.description);
            }

            System.out.println("=========== Grades ===========");
            for (Grade grade : magister.getHandler(GradeHandler.class).getGrades(true, false, true)) {
                System.out.println(grade.course.name + ": " + grade.grade);
            }

            System.out.println("========= Privileges =========");
            for (Privilege privilege : magister.profile.privileges) {
                System.out.println(privilege.name);
            }

            System.out.println("========== Password ==========");
            String current = args[args.length == 2 ? 1 : 2];
            System.out.println(magister.user.changePassword(current, current + "-test", current + "-test"));
            System.out.println(magister.user.changePassword(current + "-test", current, current));

            System.out.println("============= ELO =============");
            ELOHandler eloHandler = magister.getHandler(ELOHandler.class);
            System.out.println("==== Sources ====");
            for (Source source : eloHandler.getSources()) {
                System.out.println(source.name);
            }
            System.out.println("== Study Guides ==");
            for (StudyGuide studyGuide : eloHandler.getStudyGuides()) {
                System.out.println(studyGuide.title);
                SingleStudyGuide singleStudyGuide = eloHandler.getStudyGuide(studyGuide);
                for (StudyGuideItem item : singleStudyGuide.items) {
                    System.out.println("=> " + item.title);
                }
            }
            if (args.length > 3) {
                System.out.println("========== Contact ==========");
                for (Contact contact : magister.getHandler(ContactHandler.class).getPupilInfo(args[3])) {
                    System.out.println(contact.name);
                }
            }
        }
    }
}