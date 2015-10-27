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

import net.ilexiconn.magister.container.Contact;
import net.ilexiconn.magister.container.Homework;
import net.ilexiconn.magister.container.sub.Subject;

import java.io.IOException;

public class Test {
    public static void main(String[] args) {
        /*Scanner scanner = new Scanner(System.in);

        School school = null;
        while (school == null) {
            System.out.println("Please enter school name:");
            School[] s = Magister.findSchool(scanner.nextLine());
            if (s.length > 0) school = s[0];
        }
        System.out.println("Selected " + school.getName() + " (" + school.getUrl() + ")");
        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        System.out.println("Please enter password:");
        String password = scanner.nextLine();*/

        Magister magister = new Magister(Magister.findSchool(args[0])[0], args[1], args[2]);

        try {
            magister.login();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hey, " + magister.getProfile().getPerson().getNickname() + "!");
        System.out.println(magister.getCurrentStudy().getStudy().getDescription());

        try {
            for (Subject subject : magister.getSubjects()) {
                System.out.print("[" + subject.abbreviation + "], ");
            }
            System.out.println();

            /*for (Mark.Items item : magister.getMarks("bi")) {
                System.out.print("[" + item.getMark() + "], ");
            }
            System.out.println();*/

            for (Contact contact : magister.getTeacherInfo(args[3])) {
                System.out.print("[" + contact.fullName + "], ");
            }
            System.out.println();

            for (Homework item : magister.getHomework()) {
                System.out.print("[" + item.subjects[0].abbreviation + ", " + item.getTeachers(magister)[0].surname + "], ");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
