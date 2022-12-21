import java.io.*;
import java.util.Scanner;
import java.util.UUID;

public class Admin {
    public static boolean login() throws IOException {
        System.out.println("LOG IN AS ADMIN ROLE");
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String usernameAd = sc.nextLine();
        System.out.print("Enter your pass word: ");
        String passwordAd = sc.nextLine();

        Scanner scannerAdmin = new Scanner(new File("./src//admin.txt"));

        while (scannerAdmin.hasNextLine()) {
            String currentAdmin = scannerAdmin.nextLine();
            String[] currentAdminInfo = currentAdmin.split(",");
            String currentAdminUsername = currentAdminInfo[0];
            String currentAdminPassword = currentAdminInfo[1];


            if (usernameAd.equals(currentAdminUsername) && passwordAd.equals(currentAdminPassword)) {
                System.out.println("LOGIN SUCCESSFULLY");
                scannerAdmin.close();
                return true;
            }
        }
        System.out.println("Login unsuccessfully, please check your username and password and try again");
        scannerAdmin.close();

        return false;
    }

    public static void viewProduct() throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));
        System.out.println("\nVIEW PRODUCT\n");
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            Product.productDetails(items);
        }
    }

    public static void viewOrders() throws IOException {
        Scanner scannerOrder = new Scanner(new File("./src/order.txt"));
        System.out.println("\nVIEW ORDER\n");
        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            Order.displayOrderInfo(order);
        }
    }

    public static void viewMembers() {
//        Scanner scannerMember = new Scanner(new File());
    }

    public static void addProduct() throws IOException {
        System.out.println("\nADD NEW PRODUCT\n");
        Scanner sc = new Scanner(System.in);

        String itemId = UUID.randomUUID().toString();

        String itemName;

        while (true) {
            System.out.print("Type the item's name: ");
            itemName = sc.nextLine();

            if (!Product.checkProductExisted(itemName)) {
                break;
            } else {
                System.out.println("This name has already been used. Please try with another one.");
            }
        }
        System.out.print("Type the item's category: ");
        String itemCategory = sc.nextLine();

        String itemPrice = String.valueOf(InputValidator.getDoubleInput(
                "Type the item's price: ",
                "The price should be a natural or decimal number. Please try again."));

        String newItem = String.join(",", itemId, itemName, itemPrice, itemCategory);

        Writer addItem = new BufferedWriter(new FileWriter("./src/File/items.txt", true));
        addItem.append(System.lineSeparator() + newItem);

        System.out.println("ADD PRODUCT SUCCESSFULLY");
        addItem.close();
    }

    public static void updatePrice() throws IOException {
        System.out.println("\nUPDATE THE PRICE OF PRODUCT");

        Scanner sc = new Scanner(System.in);
        System.out.print("Enter item's name: ");
        String nameInput = sc.nextLine();

        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));

        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        boolean checkItem = false;

        while (scannerProduct.hasNextLine()) {
            String currentItem = scannerProduct.nextLine();
            String[] currentItemInfo = currentItem.split(",");
            String currentItemName = currentItemInfo[1];
            String currentItemPrice = currentItemInfo[2];

            if (!nameInput.equals(currentItemName)) {
                writer.write(currentItem + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }

            checkItem = true;
            String updatePrice = String.valueOf(InputValidator.getDoubleInput(
                    "Type the item's price: ",
                    "The price should be a natural or decimal number. Please try again."));


            String updateItem = currentItem.replace(currentItemPrice, updatePrice);

            writer.write(updateItem + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
            System.out.println("Update the price of product successfully");
        }
        tempFile.renameTo(new File("./src/File/items.txt"));
        writer.close();

        if (!checkItem) {
            System.out.println("This item's name cannot found. Please try with another one.\n");
        }
    }
    public static void addCategory() {

    }

    public static void removeCategory() {

    }

    public static void removeProduct() throws IOException {

        Scanner scannerInput = new Scanner(System.in);

        System.out.println("Enter item's name: ");
        String nameItemInput = scannerInput.nextLine();

        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));

        File inputFile = new File("./src/File/items.txt");
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));


        boolean checkItem = false;

        while (scannerProduct.hasNextLine()) {
            String currentItem = scannerProduct.nextLine();
            String[] currentItemInfo = currentItem.split(",");
            String currentItemName = currentItemInfo[1];

            if (!nameItemInput.equals(currentItemName)) {
                writer.write(currentItem + (scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }

            checkItem = true;

            String removeName = nameItemInput;
            String currentLine;

            while((currentLine = reader.readLine()) != null) {
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(removeName)) {
                    writer.write((scannerProduct.hasNextLine() ? System.lineSeparator() : ""));
                }
            }

            System.out.println("The product has been successfully removed.\n");
        }
        tempFile.renameTo(new File("./src/File/items.txt"));
        writer.close();

        if (!checkItem) {
            System.out.println("This item's name cannot found. Please try with another one.\n");
        }
    }

    public static void changeOrderStatus() {
        System.out.println("\nCHANGE STATUS OF THE ORDER\n");
        Scanner sc = new Scanner(System.in);

    }

}
