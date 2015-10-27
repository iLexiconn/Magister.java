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

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

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
        String password = scanner.nextLine();

        Magister magister = new Magister(school, username, password);

        try {
            magister.login();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Hey, " + magister.getProfile().getPerson().getNickname() + "!");
        System.out.println(magister.getCurrentStudy().getStudy().getDescription());

        try {
            for (Subject subject : magister.getSubjects()) {
                System.out.print("[" + subject.getAbbreviation() + "], ");
            }
            System.out.println();

            /*for (Mark.Items item : magister.getMarks("bi")) {
                System.out.print("[" + item.getMark() + "], ");
            }
            System.out.println();

            for (Homework.Items item : magister.getHomework()) {
                System.out.print("[" + item.getSubjects()[0].getName() + "], ");
            }
            System.out.println();*/

            System.out.println("Please enter a name:");
            for (Contact contact : magister.getPupilInfo(scanner.nextLine())) {
                System.out.print("[" + contact.getFullName() + "], ");
            }

            System.out.println("Please enter a name:");
            for (Contact contact : magister.getPupilInfo(scanner.nextLine())) {
                System.out.print("[" + contact.getFullName() + "], ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
