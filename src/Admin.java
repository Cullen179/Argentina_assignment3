import java.io.*;
import java.lang.reflect.Member;
import java.util.Scanner;
import java.util.UUID;

public class Admin {
    // This method is used to log in for admin role.
    public static boolean login() throws IOException {
        System.out.println("\nLOG IN AS ADMIN ROLE\n");
        // Create a scanner object to be ready to get input information (username & password) from users via keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String usernameAd = sc.nextLine();
        System.out.print("Enter your password: ");
        String passwordAd = sc.nextLine();
        // Create a scanner object to read from an admin text file.
        Scanner scannerAdmin = new Scanner(new File("./src//admin.txt"));
        // Continue to loop through each line of admin.txt file to find the username and password of admin.
        while (scannerAdmin.hasNextLine()) {
            String currentAdmin = scannerAdmin.nextLine();
            String[] currentAdminInfo = currentAdmin.split(",");
            String currentAdminUsername = currentAdminInfo[0];
            String currentAdminPassword = currentAdminInfo[1];
            // in case the users input are matched, completed this function.
            if (usernameAd.equals(currentAdminUsername) && passwordAd.equals(currentAdminPassword)) {
                // Prompt user a successful message
                System.out.println("LOGIN SUCCESSFULLY");
                scannerAdmin.close();
                return true;
            }
        }
        // In case the users input are not matched, prompt user an unsuccessful message.
        System.out.println("Login unsuccessfully, please check your username and password and try again");
        scannerAdmin.close();
        return false;
    }

    // This method is used to view products for admin.
    public static void viewProduct() throws IOException {
        // Create a scanner object to read from an item text file.
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));
        System.out.println("\nVIEW PRODUCT\n");
        // A loop is used to display detailed information of each product.
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            Product.productDetails(items);
        }
        scannerProduct.close();
    }

    // This method is used to view orders for admin.
    public static void viewOrders() throws IOException {
        // Create a scanner object to read from an item text file.
        Scanner scannerOrder = new Scanner(new File("./src/order.txt"));
        System.out.println("\nVIEW ORDER");
        // A loop is used to display detailed information of each order.
        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            Order.orderDetail(order);
        }
        scannerOrder.close();
    }

    public static void viewMembers() throws IOException {
        // Create a scanner object to read from a member text file.
        Scanner scannerMember = new Scanner(new File("./src/File.member.txt"));
        System.out.println("\nVIEW MEMBER");
        // A loop is used to display detailed information of each member.
        while (scannerMember.hasNextLine()) {
            String member = scannerMember.nextLine();
            // ADD THIS FUNCTION IN MEMBER CLASS LATER
//            Member.memberDetail(member);
        }
        scannerMember.close();

    }

    // This method is used to add new product for admin.
    public static void addProduct() throws IOException{
        System.out.println("\nADD NEW PRODUCT");
        PrintWriter pw = new PrintWriter(new FileWriter("src/File/items.txt", true));
        Product newProduct = Product.createProduct();
        pw.printf("\n%s,%s,%.1f,%s", newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getCategory());
        System.out.println("ADD PRODUCT SUCCESSFULLY");
        pw.close();
    }
    public static void removeProduct() throws IOException {
        System.out.println("\nREMOVE PRODUCT");
        // Create a scanner object to be ready to get input information (nameItem) from users via keyboard.
        Scanner scannerInput = new Scanner(System.in);
        System.out.println("Enter item's name: ");
        String nameItemInput = scannerInput.nextLine();
        // Create a scanner object to read from an item text file.
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));

        // Create a writer for a temporary file to store updated data
        File inputFile = new File("./src/File/items.txt");
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));

        // A boolean value to check the item name matched or not.
        boolean checkItem = false;

        while (scannerProduct.hasNextLine()) {
            String currentItem = scannerProduct.nextLine();
            String[] currentItemInfo = currentItem.split(",");
            String currentItemName = currentItemInfo[1];
            // In case the name from input is not equivalent to the name of item in the file, it would add the new line.
            if (!nameItemInput.equals(currentItemName)) {
                writer.write(currentItem + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }
            checkItem = true;
            String removeName = nameItemInput;
            String currentLine;
            while ((currentLine = reader.readLine()) != null) {
                // Trim the newline when comparing with removeName.
                String trimmedLine = currentLine.trim();
                // In case the trimmedLine contains the name of removed product, it would be deleted.
                if (trimmedLine.equals(removeName)) {
                    writer.write((scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
                }
            }
            // Prompt a message to users.
            System.out.println("The product has been successfully removed.\n");
        }
        // Rename the temporary file to the original one.
        tempFile.renameTo(new File("./src/File/items.txt"));
        writer.close();
        // In case an item is not found, it would prompt user an unsuccessful message.
        if (!checkItem) {
            System.out.println("This item's name cannot found. Please try with another one.\n");
        }
    }

    // This method is used to update the price of product for admin.
    public static void updatePrice() throws IOException {
        System.out.println("\nUPDATE THE PRICE OF PRODUCT");
        // Create a scanner object to be ready to get input information (nameItem) from users via keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter item's name: ");
        String nameInput = sc.nextLine();
        // Create a scanner object to read from an item text file.
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));
        // Create a writer for a temporary file to store updated data
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        // A boolean value to check the item name matched or not.
        boolean checkItem = false;
        // Continue to loop through each line of items.txt file to find the name of item.
        while (scannerProduct.hasNextLine()) {
            String currentItem = scannerProduct.nextLine();
            String[] currentItemInfo = currentItem.split(",");
            String currentItemName = currentItemInfo[1];
            // In case the name from input is not equivalent to the name of item in the file, it would add the new line.
            if (!nameInput.equals(currentItemName)) {
                writer.write(currentItem + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }
            // In case an item is found, it would prompt user to input the new price of item.
            checkItem = true;
            String updatePrice = String.valueOf(InputValidator.getDoubleInput(
                    "Type the new item's price: ",
                    "The price should be a natural or decimal number. Please try again."));
            // Updated the new price for item.
            String currentPrice = currentItem.split(",")[2];
            String updateItem = currentItem.replace(currentPrice, updatePrice);
            // Write the updated line to the temporary file.
            writer.write(updateItem + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
            // Prompt a message to users.
            System.out.println("Update the price of product successfully");
        }
        // Rename the temporary file to the original one.
        tempFile.renameTo(new File("./src/File/items.txt"));
        // In case an item is not found, it would prompt user an unsuccessful message.
        if (!checkItem) {
            System.out.println("\nThis item's name cannot found. Please try with another one.");
        }
        writer.close();
    }
    public static void addCategory() {

    }

    public static void removeCategory() {

    }
    public static void getOrderByCustomerID() throws IOException {
        System.out.println("\nGET ORDER BY CUSTOMER ID");
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Customer ID: ");
        String customerIDInput = sc.nextLine();

        boolean checkCustomerIDExisted = false;

        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            String[] orderInfo = order.split(",");
            String customerID = orderInfo[1];

            if (customerIDInput.equals(customerID)) {
                Order.orderDetail(order);
                checkCustomerIDExisted = true;
            }
        }

        if (!checkCustomerIDExisted) {
            System.out.println("\nThis customer's id cannot found. Please try with another one.");
        }
        scannerOrder.close();
    }

    public static void changeOrderStatus() {
        System.out.println("\nCHANGE STATUS OF THE ORDER");
        Scanner sc = new Scanner(System.in);

    }

}
