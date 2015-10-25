package net.ilexiconn.magister;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Magister magister = new Magister();

        System.out.println("Please enter school name:");
        School school = magister.findSchool(scanner.nextLine())[0];
        System.out.println("Selected " + school.getName() + " (" + school.getUrl() + ")");
        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        System.out.println("Please enter password:");
        String password = scanner.nextLine();

        magister.setUser(username, password);
        magister.setSchool(school);

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

            for (Mark.Items item : magister.getMarks("bi")) {
                System.out.print("[" + item.getMark() + "], ");
            }
            System.out.println();

            for (Homework.Items item : magister.getHomework()) {
                System.out.print("[" + item.getSubjects()[0].getName() + "], ");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
