package Main_package;

import java.util.Scanner;

public class Manager{

    public static boolean validateString(String input) {
        boolean isValid;

        if (!input.matches("^[A-Za-z\\s]+$")) {
            isValid = false;
        } else {
            isValid = true;
        }
        return isValid;
    }

    public static Integer takeIntegerInput(String message, Scanner sc) {
        Integer newInteger;
        do {
            System.out.println(message);
            while (!sc.hasNextInt()) {
                System.out.println("That's not a valid integer!");
                sc.next();
                System.out.print("Please enter a valid number greater than 0: ");
            }
            newInteger = sc.nextInt();

        } while (newInteger <= 0);

        return newInteger;
    }

    public static String takeInput(String message, Scanner sc) {
        String newString = "";
        do {
            System.out.println(message);
            newString = sc.nextLine();

        } while (!Manager.validateString(newString));

        return newString;
    }
}

