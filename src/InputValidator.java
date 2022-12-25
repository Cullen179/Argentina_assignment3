import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Scanner;

public class InputValidator {
    public static int validateIntInput(String promptUser, String errorMessage) {
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

    public static double validateDoubleInput(String promptUser, String errorMessage) {
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
