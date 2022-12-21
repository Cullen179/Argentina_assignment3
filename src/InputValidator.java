import java.util.Scanner;

public class InputValidator {
    public static int getIntInput(String promptUser, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(promptUser);
                return Integer.parseInt(sc.nextLine());
            }
            catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }

    public static double getDoubleInput(String promptUser, String errorMessage) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            try {
                System.out.print(promptUser);
                return Double.parseDouble(sc.nextLine());
            }
            catch (Exception e) {
                System.out.println(errorMessage);
            }
        }
    }
}
