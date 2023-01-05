import java.util.InputMismatchException;
import java.util.Scanner;

public class InputValidator {
    public static int getIntInput(String promptUser, String errorMessage) {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.println(promptUser);
                int input = sc.nextInt();

                // Throw error if input is negative
                if (input < 0) {
                    throw new IllegalArgumentException("Input has to be positive. Please try again");
                }

                return input;
            }
            catch (InputMismatchException e) {
                System.out.println(errorMessage);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static double getDoubleInput(String userInput, String errorMessage) {
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print(userInput);
                double input = sc.nextDouble();

                // Throw error if input is negative
                if (input < 0) {
                    throw new IllegalArgumentException("Input has to be positive. Please try again");
                }
                return input;
            }
            catch (InputMismatchException e) {
                System.out.println(errorMessage);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}