import java.util.Scanner;

// Used to read number input from the player
// Keeps asking until the player enters a valid number in the given range

public class InputHelper {

    public static int readNumber(Scanner input, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = input.nextLine().trim();
            try {
                int number = Integer.parseInt(line);
                if (number >= min && number <= max) {
                    return number;
                }
            } catch (NumberFormatException e) {
                // not a number
            }
            System.out.println("Please enter a number from " + min + " to " + max + ".");
        }
    }

}
