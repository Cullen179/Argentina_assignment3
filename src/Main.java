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
    public static void adminFlow() throws IOException {
        Admin admin = new Admin();
        boolean checkLogin = false;
        while (!checkLogin) {
            checkLogin = admin.login();
        }
        boolean isRunning = true;

        while (isRunning) {
            System.out.println("------------------");
            System.out.println("(0) : Log Out");
            System.out.println("(1) : View All The Products");
            System.out.println("(2) : View All The Orders");
            System.out.println("(3) : View All The Members");
            System.out.println("(4) : Add New Product");
            System.out.println("(5) : Remove Existed Product");
            System.out.println("(6) : Add New Category");
            System.out.println("(7) : Remove Existed Category");
            System.out.println("(8) : Update The Price");
            System.out.println("(9) : Display Information of All Orders by Customer ID");
            System.out.println("(10) : Change the Order Status");
            System.out.println("(11) : Remove a customer/member by customer ID");
            System.out.println("(12) : List product with the highest number bought by a customer");
            System.out.println("(13) : Calculate the store total revenue on a day");
            System.out.println("(14) : Display Information of all Orders on a day");
            System.out.println("------------------");
            int number = InputValidator.getIntInput("Execute your next action by entering one of listed numbers [0-14]: ",
                    "Your input number is invalid. Please try another one.");
            switch (number) {
                case 0:
                    isRunning = false;
                    System.out.println("Log Out Successfully.");
                    break;
                case 1:
                    admin.viewProduct();
                    break;
                case 2:
                    admin.viewOrders();
                    break;
                case 3:
                    admin.viewMembers();
                    break;
                case 4:
                    admin.addProduct();
                    break;
                case 5:
                    admin.removeProduct();
                    break;
                case 6:
                    admin.addCategory();
                    break;
                case 7:
                    admin.removeCategory();
                    break;
                case 8:
                    admin.updateProductPrice();
                    break;
                case 9:
                    admin.getOrderByCustomerID();
                    break;
                case 10:
                    admin.changeOrderStatus();
                    break;
                case 11:
                    admin.removeCustomer();
                    break;
                case 12:
                    admin.getHighestBoughtProduct();
                    break;
                case 13:
                    admin.getTotalRevenue();
                    break;
                case 14:
                    admin.checkOrderInfoInADay();
                    break;
            }
        }
    }

    public static void customerFlow() throws IOException {
        Scanner scanner = new Scanner(System.in);
        Customer customer = null;
        boolean userInput = false;
        String userChoice;
        while (!userInput) {
            System.out.println("Please choose to register or login");
            userChoice = scanner.nextLine();
            switch (userChoice) {
                case "register":
                    System.out.println("You're ready to register");
                    Customer.registerMember();
                    userInput = true;
                case "login":
                    System.out.println("You're ready to login");
                    customer = Customer.login();
                    userInput = true;
                default:
                    System.out.println("Only enter register or login");
            }
        }
        if (customer != null) {
            boolean isRunning = true;

            while (isRunning) {
                System.out.println("------------------");
                System.out.println("(0) : Log Out");
                System.out.println("(1) : Update Account Information");
                System.out.println("(2) : Check the current membership status.");
                System.out.println("(3) : View All Products");
                System.out.println("(4) : Search Product (by Category and Price Range)");
                System.out.println("(5) : Sort All Products (ascending or descending order)");
                System.out.println("(6) : Create Order");
                System.out.println("(7) : Find Order Details");
                System.out.println("(8) : Display Previous Order(s)");
                System.out.println("------------------");
                int number = InputValidator.getIntInput("Execute your next action by entering one of listed numbers [0-7]: ",
                        "Your input number is invalid. Please try another one.");
                switch (number) {
                    case 0:
                        isRunning = false;
                        System.out.println("Log Out Successfully.");
                        break;
                    case 1:
                        customer.updateAccountInfo();
                        break;
                    case 2:
                        System.out.printf("The current membership status: %s \n", customer.getMembership());
                        break;
                    case 3:
                        customer.viewProduct();
                        break;
                    case 4:
                        customer.searchProduct();
                        break;
                    case 5:
                        customer.sortProducts();
                        break;
                    case 6:
                        customer.createOrder();
                        break;
                    case 7:
                        customer.findOrderDetails();
                        break;
                    case 8:
                        customer.displayPreviousOrders();
                        break;
                }
            }

        }
    }


    public static void main(String[] args) throws IOException {
        welcomeScreen();
        System.out.println("\n");
        Scanner sc = new Scanner(System.in);
        String role;
        boolean userRole = false;

        while (!userRole) {
            System.out.println("Please type your role (customer / admin) to access the system: ");
            role = sc.nextLine();
            switch (role) {
                case "customer":
                    try {
                        customerFlow();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    userRole = true;
                    break;
                case "admin":
                    try {
                        adminFlow();
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    userRole = true;
                    break;
                default:
                    System.out.println("Unsuccessfully access, please type your role again.");
            }
        }
    }
}
