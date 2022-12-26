import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public static boolean login() throws IOException {
        System.out.println("\nLOG IN AS ADMIN ROLE");
        // Create a scanner object to be ready to get input information (username & password) from users via keyboard.
        Scanner sc = new Scanner(System.in);
        String usernameAd;
        System.out.print("Please type your username: ");
        while (true) {
            usernameAd = sc.nextLine();
            if (checkAdminUsernameExisted(usernameAd)) break;
            else {
                System.out.println("This username is not existed. Please try with another one.");
            }
        }
        String passwordAd;
        System.out.print("Please type your password: ");
        while (true) {
            passwordAd = sc.nextLine();
            if (checkAdminPasswordExisted(passwordAd)) break;
            else {
                System.out.println("This password is not existed. Please try with another one.");
            }
        }
        // Create a scanner object to read from an admin text file.
        Scanner scannerAdmin = new Scanner(new File("./src/File/admin.txt"));
        // Continue to loop through each line of admin.txt file to find the username and password of admin.
        while (scannerAdmin.hasNextLine()) {
            String currentAdmin = scannerAdmin.nextLine();
            String[] currentAdminInfo = currentAdmin.split(",");
            String currentAdminUsername = currentAdminInfo[0];
            String currentAdminPassword = currentAdminInfo[1];
            // in case the users input are matched, completed this function.
            if (currentAdminUsername.equals(usernameAd) && currentAdminPassword.equals(passwordAd)) {
                // Prompt user a successful message
                System.out.println("LOGIN SUCCESSFULLY");
                System.out.println("-------------------");
                scannerAdmin.close();
                return true;
            }
        }
        // In case the users input are not matched, prompt user an unsuccessful message.
        System.out.println("Login unsuccessfully, please check your username and password and try again");
        scannerAdmin.close();
        return false;
    }
    public static boolean checkAdminUsernameExisted(String username) throws IOException {
        Scanner scannerAdmin = new Scanner(new File("./src/File/admin.txt"));

        while (scannerAdmin.hasNextLine()) {
            String currentAdmin = scannerAdmin.nextLine();
            String[] currentAdminInfo = currentAdmin.split(",");
            String currentAdminUsername = currentAdminInfo[0];

            if (username.equals(currentAdminUsername)) {
                return true;
            }
        }
        scannerAdmin.close();
        return false;
    }

    public static boolean checkAdminPasswordExisted(String password) throws IOException {
        Scanner scannerAdmin = new Scanner(new File("./src/File/admin.txt"));

        while (scannerAdmin.hasNextLine()) {
            String currentAdmin = scannerAdmin.nextLine();
            String[] currentAdminInfo = currentAdmin.split(",");
            String currentAdminPassword = currentAdminInfo[1];

            if (password.equals(currentAdminPassword)) {
                return true;
            }
        }
        scannerAdmin.close();
        return false;
    }
    public void viewProduct() throws IOException {
        // Scan items file
        Scanner sc = new Scanner(new File("./src/File/items.txt"));
        // Loop through items file
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            Product.productDetails(item);
        }
    }

    // This method is used to view orders for admin.
    public static void viewOrders() throws IOException {
        // Create a scanner object to read from an item text file.
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        System.out.println("\nVIEW ORDER");
        // A loop is used to display detailed information of each order.
        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            Order.displayOrderInfo(order);
        }
        scannerOrder.close();
    }

    public static void viewMembers() throws IOException {
        // Create a scanner object to read from a member text file.
        Scanner scannerMember = new Scanner(new File("./src/File/member.txt"));
        System.out.println("\nVIEW MEMBER");
        // A loop is used to display detailed information of each member.
        while (scannerMember.hasNextLine()) {
            String member = scannerMember.nextLine();
//            Customer.(member);
        }
        scannerMember.close();
    }

    public void addProduct() throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter("./src/File/items.txt", true));
        // Get new product information
        Product newProduct = Product.createProduct();
        // Add new item line to file
        pw.printf("\n%s,%s,%.1f,%s", newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getCategory());
        pw.close();
    }

    public void removeProduct() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("What is name of the product you want to delete ?");
        String productName = sc.next();
        boolean matchProduct = false;
        // Add new file
        File removeItem = new File("./src/File/removeItems.txt");
        File itemFile = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(itemFile);
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(removeItem));
        // Initiate line number
        int line = 0;
        // Boolean variable if remove item match line 1
        boolean matchedLine1 = false;

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            // Check if product id equal remove item id, and line number is 1
            if (product.getName().equals(productName) && line == 1) {
                matchedLine1 = true;
                continue;

                // Check if product id equal remove item id,
            } else if (product.getName().equals(productName)) {
                continue;
            }

            // if line number is 1 or 2 with remove item in line 1, avoid adding new line
            if (line == 1 || (line == 2 && matchedLine1)) {
                bufferedWriter.write(item);
            } else {
                bufferedWriter.write("\n" + item);
            }
        }
        fileScanner.close();
        bufferedWriter.close();
        itemFile.delete();
        // Rename remove item file to item file
        removeItem.renameTo(itemFile);

        // Print error if product name doesn't match
        if (!Product.checkProductExisted(productName)) {
            System.out.println("Product doesn't exist.Please try again");
        }
    }

    public void updateProduct() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the product you want to update");
        String productName = sc.next();

        // Add new file
        File updatePrice = new File("./src/File/update_items.txt");
        File itemFile = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(itemFile);
        PrintWriter pw = new PrintWriter(new FileWriter(updatePrice, true));
        boolean matchProduct = false;

        // Initiate line number
        int line = 0;

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            if (product.getName().equals(productName)) {
                matchProduct = true;
                product.setPrice(100);
                // Update item to new product
                item = Product.generateItem(product);
            }

            // if line number is 1, avoid adding new line
            if (line == 1) {
                pw.printf(item);
            } else {
                pw.printf("\n" + item);
            }
        }
        fileScanner.close();
        pw.close();

        // Delete the item file
        itemFile.delete();
        // Rename remove item file to item file
        updatePrice.renameTo(itemFile);

        // Print error if product name doesn't match
        if (!Product.checkProductExisted(productName)) {
            System.out.println("Product doesn't exist.Please try again");
        }
    }
    public void addCategory() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("What category do you want to add ?");
        String newCategory = sc.next();

        // Check if new category is in category list
        if (!Product.getCategoryList().contains(newCategory)) {
            Product.addCategory(newCategory);
        } else {
            System.out.println("Category is already available. Do you want to retry ?");
            boolean retry = sc.nextBoolean();

            // Call addCategory method if admin want to retry
            if (retry) {
                addCategory();
            }
        }
    }

    public void removeCategory() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("What category do you want to remove ?");
        String removeCategory = sc.next();
        boolean matchCategory = false;
        ArrayList<String> categoryList = Product.getCategoryList();
        // Check if remove category is in category list
        if (categoryList.contains(removeCategory)) {
            matchCategory = true;
        }

        File update = new File("./src/File/update.txt");
        File items = new File("src/File/items.txt");
        Scanner fileScanner = new Scanner(new File("src/File/items.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(update));

        // Initiate line number
        int line = 0;

        // Loop through items file
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            // Check if product category equal remove category
            if (product.getCategory().equals(removeCategory)) {
                product.setCategory("None");
                // Update category to item line
                item = Product.generateItem(product);
            }

            // if line number is 1 , avoid adding new line
            if (line == 1) {
                bufferedWriter.write(item);
            } else {
                bufferedWriter.write("\n" + item);
            }
        }
        sc.close();
        bufferedWriter.close();
        // Delete the items file
        items.delete();
        // Rename remove item file to item file
        update.renameTo(new File("src/File/items.txt"));

        // Print error if category doesn't exist
        if (!matchCategory) {
            System.out.println("Category doesn't exist. Please try again.");
        }
    }

    public void removeCustomer() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the ID of the customer you want to remove?");
        String id = sc.next();
        // Add new file
        File removeCus = new File("./src/File/removeCustomers.txt");
        File customerFile = new File("./src/File/customers.txt");
        Scanner fileScanner = new Scanner(customerFile);
        PrintWriter pw = new PrintWriter(new FileWriter(removeCus, true));
        // Initiate line number
        int line = 0;
        // Boolean variable if remove customer match line 1
        boolean matchedLine1 = false;

        // Loop through customer file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            // Check if product id equal remove item id, and line number is 1
            if (product.getId().equals(id) && line == 1) {
                matchedLine1 = true;
                continue;

                // Check if product id equal remove item id,
            } else if (product.getId().equals(id)) {
                continue;
            }

            // if line number is 1 or 2 with remove item in line 1, avoid adding new line
            if (line == 1 || (line == 2 && matchedLine1)) {
                pw.printf(item);
            } else {
                pw.printf("\n" + item);
            }
        }
        fileScanner.close();
        pw.close();
        // Delete the item file
        customerFile.delete();
        // Rename remove item file to item file
        removeCus.renameTo(customerFile);
    }

    public static void getOrderByCustomerID() throws IOException {
        System.out.println("\nGET ORDER BY CUSTOMER ID");
        // Create a scanner object to be ready to get input information (customer ID) from users via keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Customer ID: ");
        String customerIDInput = sc.nextLine();
        // Create a scanner object to read from an order text file.
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        // A boolean value to check the item's name existed or not.
        boolean checkCustomerIDExisted = false;
        // Continue to loop through each line of order.txt file to find the ID of customers.
        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            String[] orderInfo = order.split(",");
            String customerID = orderInfo[1];
            // In case the input ID is equivalent to the customerID from file, the following function would be executed.
            if (customerIDInput.equals(customerID)) {
                Order.displayOrderInfo(order);
                checkCustomerIDExisted = true;
            }
        }
        // In case the customer's id is not existed, prompt user a message.
        if (!checkCustomerIDExisted) {
            System.out.println("\nThis customer's id cannot found. Please try with another one.");
        }
        scannerOrder.close();
    }
    public static void changeOrderStatus() throws IOException {
        System.out.println("\nCHANGE STATUS OF THE ORDER");
        // Create a scanner object to be ready to get input information (order ID) from users via keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the order ID: ");
        String orderIDInput = sc.nextLine();
        // Create a scanner object to read from an order text file.
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        // Create a writer for a temporary file to store updated data
        File tempFile = new File("tempFile.txt");
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
        // A boolean value to check the order was made or not.
        boolean checkOrderIDExisted = false;
        // Continue to loop through each line of order.txt file to find the ID of an order.
        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            String[] orderInfo = order.split(",");
            String orderID = orderInfo[0];
            // In case the ID from input is not equivalent to the orderID in the file, it would add the new line.
            if (!orderIDInput.equals(orderID)) {
                writer.write(order + (scannerOrder.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }
            checkOrderIDExisted = true;
            String orderStatus = orderInfo[9];
            // In case the status is delivered, it would be changed to 'paid', update the new line to the file then prompt a message.
            if (!orderStatus.equals("paid")) {
                String updatedOrder = order.replace(orderStatus, "paid");
                writer.write(updatedOrder + (scannerOrder.hasNextLine() ? System.lineSeparator() : ""));
                System.out.println("The status of this order has been successfully changed to paid.\n");
            } // In case the status is paid, it would prompt a message.
            else {
                System.out.println("This order is already paid received.");
                writer.write(order + (scannerOrder.hasNextLine() ? System.lineSeparator() : ""));
                continue;
            }
            // Rename the temporary file to the original one.
            tempFile.renameTo(new File("./src/File/order.txt"));
            writer.close();
        }
        // In case the order's id is not existed, prompt user a message.
        if (!checkOrderIDExisted) {
            System.out.println("\nThis order's id cannot found. Please try with another one.");
        }
        scannerOrder.close();
    }

    public void highestProduct() throws IOException{
        Scanner sc = new Scanner(new File("./src/File/customers.txt"));
        ArrayList<Customer> highBought = new ArrayList<Customer>();
        int maxNum = 0;
        while(sc.hasNextLine()) {
            String customerLine = sc.nextLine();
            Customer customer = Customer.generateCus(customerLine);
            if (customer.highBoughtAmount() > maxNum) {
                highBought.clear();
                highBought.add(customer);
                maxNum = customer.highBoughtAmount();
            } else if (customer.highBoughtAmount() == maxNum) {
                highBought.add(customer);
            }
        }
        ArrayList<String> listProduct = new ArrayList<String>();
        for (Customer customer: highBought) {
            for (String item: customer.highestBoughtProduct()) {
                if (!listProduct.contains(item)) {
                    listProduct.add(item);
                }
            }
        }
        System.out.println(listProduct);
        System.out.println("Highest number bought: " + maxNum);
    }

    public void customerHighestProduct() throws IOException{
        System.out.println("-".repeat(17));
        System.out.println("Get the highest product bought of a customer");
        System.out.println("-".repeat(17));
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the ID of the customer you want to view ?");
        String id = sc.next();
        // If the id of the customer
        boolean matchCustomer = false;

        Scanner fileScanner = new Scanner(new File("./src/File/customers.txt"));
        // Loop through customer file
        while(fileScanner.hasNextLine()) {
            String customerLine = fileScanner.nextLine();
            Customer customer = Customer.generateCus(customerLine);

            // Get the highest bought product(s) and the quantity
            if (customer.getID().equals(id)) {
                System.out.println("-".repeat(17));
                System.out.println("The highest bought item(s) of customer" + customer.getID() + ": ");
                System.out.println(customer.highestBoughtProduct());
                System.out.println("The total number bought is " + customer.highBoughtAmount());
                System.out.println("-".repeat(17));
            }
        }

        // Print error if customer ID not matched
        if (!matchCustomer) {
            System.out.println("Customer ID not valid. Please try again");
        }
    }
    public static boolean calculateTotalRevenue(String date) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        try {
            formatter.parse(date);
            double revenue = 0;
            while (scannerOrder.hasNextLine()) {
                String order = scannerOrder.nextLine();
                String[] orderInfo = order.split(",");
                String orderDate = orderInfo[3];

                if (orderDate.equals(date)) {
                    double orderTotal = Double.parseDouble(orderInfo[7]);
                    revenue += orderTotal;
                }
            }
            System.out.printf("\nThe total revenue in %s is %.2f", date, revenue);
            scannerOrder.close();
        }
        catch (Exception e) {
            System.out.println("\nInvalid input, please try with another one.");
        }
        return true;
    }
    public static void totalRevenue() throws IOException {
        System.out.println("\nCALCULATE THE STORE TOTAL REVENUE IN A PARTICULAR DAY");
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter the formatted date (yyyy-MM-dd) that you want to calculate the revenue: ");
        String date = sc.nextLine();
        calculateTotalRevenue(date);
        scannerOrder.close();
    }
    public static void checkOrderInfoInADay() throws IOException {
        System.out.println("\nCHECK THE INFORMATION OF ALL ORDERS EXECUTED IN A PARTICULAR DAY");
        Scanner scannerOrder = new Scanner(new File("./src/File/order.txt"));
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the formatted date (dd/MM/yyyy) that you want to check the information of all orders: ");
        String date = sc.nextLine();
        boolean checkOrderExisted = false;

        while (scannerOrder.hasNextLine()) {
            String order = scannerOrder.nextLine();
            String[] orderInfo = order.split(",");
            String orderDate = orderInfo[2];

            if (orderDate.equals(date)) {
                Order.displayOrderInfo(order);
                checkOrderExisted = true;

            }
        }
        if (!checkOrderExisted) {
            System.out.printf("There isn't any orders in %s, please try with another date", date);
        }
    }
    public static void main(String[] args) throws IOException {
        Admin admin = new Admin();
        admin.customerHighestProduct();
    }

}
