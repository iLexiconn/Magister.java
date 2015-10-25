package net.ilexiconn.magister;

import java.io.IOException;
import java.util.Scanner;

public class Test
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter school name:");
        String school = scanner.nextLine();
        System.out.println("Please enter username:");
        String username = scanner.nextLine();
        System.out.println("Please enter password:");
        String password = scanner.nextLine();

        Magister magister = new Magister();
        magister.setUser(username, password);
        magister.setSchool(magister.findSchool(school)[0]);

        try
        {
            magister.login();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println("Hey, " + magister.getProfile().getPerson().getNickname() + "!");
        System.out.println(magister.getCurrentStudy().getStudy().getDescription());

        try
        {
            for (Mark.Items item : magister.getMarks())
            {
                System.out.print(item.getMark() + ", ");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
