import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void welcomeScreen() {
        System.out.println();
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("WELCOME TO OUR STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println();
        System.out.println("Group: Argentina");
        System.out.println("s3932134, Dinh Le Hong Tin");
    }
    static public void promptCommandSeparator() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Press Enter to perform your next action.");
        scanner.nextLine();
    }

    public static void main(String[] args) throws IOException {
//        welcomeScreen();
//        promptCommandSeparator();
//        Customer.viewProduct();
//        Customer.listProductByCategory();
//        Customer.sortProduct();
//
//        Admin.removeProduct();
//        Customer.login();
//        Admin.login();
//        Admin.addProduct();
//        Admin.viewProduct();
//        Admin.updatePrice();
        Admin.changeOrderStatus();
    }
}