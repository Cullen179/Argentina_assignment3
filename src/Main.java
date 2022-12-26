import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void welcomeScreen() {
        System.out.println();
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("WELCOME TO OUR STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println("----------------");
        System.out.println("Group: ARGENTINA");
        System.out.println("s3938020, Tran Le Hong Ngoc");
        System.out.println("s3963286, Do Tung Lam");
        System.out.println("s3932134, Dinh Le Hong Tin");
        System.out.println("s3919657, Tran Nhat Tien");
        System.out.println("----------------");

    }
    static public void promptCommandSeparator() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.println("Press Enter/ Return to execute the next action.");
        scanner.nextLine();
    }
    public static void adminFlow() throws IOException {
        boolean checkLogin = false;
        while (!checkLogin) {
            checkLogin = Admin.login();
        }
        boolean isRunning = true;

        while (isRunning) {
            Scanner sc = new Scanner(System.in);
            System.out.println("------------------");
            System.out.println("Execute your next action by entering one of listed numbers [0-8].");
            System.out.println("(0) : Log Out");
            System.out.println("(1) : View All The Products");
            System.out.println("(2) : View All The Orders");
            System.out.println("(3) : View All The Members");
            System.out.println("(4) : Add New Product");
            System.out.println("(5) : Remove Existed Product");
            System.out.println("(6) : Update The Price");
            System.out.println("(7) : Display Information of Order by Customer ID");
            System.out.println("(8) : Change the Order Status");
            String number = sc.nextLine();
            while (!number.matches("[0-8]+")) {
                System.out.println("\nYour input number is invalid. Please try another one.");
                number = sc.nextLine();
            }
            switch (number) {
                case "0":
                    isRunning = false;
                    System.out.println("Log Out Successfully.");
                    break;
                case "1":
                    Admin.viewProducts();
                    promptCommandSeparator();
                    break;
                case "2":
                    Admin.viewOrders();
                    promptCommandSeparator();
                    break;
                case "3":
                    Admin.viewMembers();
                    promptCommandSeparator();
                    break;
                case "4":
                    Admin.addProduct();
                    promptCommandSeparator();
                    break;
                case "5":
                    Admin.removeProduct();
                    promptCommandSeparator();
                    break;
                case "6":
                    Admin.updatePrice();
                    promptCommandSeparator();
                    break;
                case "7":
                    Admin.getOrderByCustomerID();
                    promptCommandSeparator();
                    break;
                case "8":
                    Admin.changeOrderStatus();
                    promptCommandSeparator();
                    break;
                default:
                    System.out.println("\nPlease enter 0-8 only.\n");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        welcomeScreen();
        promptCommandSeparator();
        Scanner sc = new Scanner(System.in);
        String role;
        boolean userRole = false;

        while (!userRole) {
            System.out.println("Please type your role (customer / member / admin) to access the system.");
            role = sc.nextLine();
            switch (role) {
                case "customer":
//                    customerFlow();
//                    userRole = true;
//                    break;
                case "member":
//                    memberFlow();
//                    userRole = true;
//                    break;
                case "admin":
                    adminFlow();
                    userRole = true;
                    break;
                default:
                    System.out.println("Unsuccessfully access, please type your role again.");
                    Customer.login();
            }
        }
    }
}
