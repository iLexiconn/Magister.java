package net.ilexiconn.magister;

import java.io.IOException;
import java.util.Scanner;

public class Test
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Please enter school name: \n");
        String school = scanner.nextLine();
        System.out.print("Please enter username: \n");
        String username = scanner.nextLine();
        System.out.print("Please enter password: \n");
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
    }
}
