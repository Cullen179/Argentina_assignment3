import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Admin {
    public static boolean login() throws IOException {
        System.out.println("-".repeat(17));
        System.out.println("LOG IN AS ADMIN ROLE");
        System.out.println("-".repeat(17));
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
        System.out.println("-".repeat(17));
        System.out.println("VIEW PRODUCT");
        System.out.println("-".repeat(17));
        // Loop through items file
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            Product product = Product.generateProduct(item);
            product.getProductDetails();
        }
    }

    // This method is used to view orders for admin.
    public void viewOrders() throws IOException {
        // Create a scanner object to read from an item text file.
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));
        System.out.println("-".repeat(17));
        System.out.println("VIEW ORDER");
        System.out.println("-".repeat(17));
        // A loop is used to display detailed information of each order.
        while (sc.hasNextLine()) {
            String orderInfo = sc.nextLine();
            Order order = Order.generateOrder(orderInfo);
            order.displayOrderInfo();
        }
        sc.close();
    }

    public void viewMembers() throws IOException {
        System.out.println("-".repeat(17));
        System.out.println("VIEW MEMBERS");
        System.out.println("-".repeat(17));
        // Create a scanner object to read from a member text file.
        Scanner sc = new Scanner(new File("./src/File/customers.txt"));

        // A loop is used to display detailed information of each member.
        while (sc.hasNextLine()) {
            String memberInfo = sc.nextLine();
            Customer member = Customer.generateCus(memberInfo);

            // Display member only
            if (!member.getMembership().equals("normal")) {
                member.displayAccountInfo();
            }
        }
        sc.close();
    }

    public void addProduct() throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter("./src/File/items.txt", true));

        // Get new product information
        System.out.println("-".repeat(17));
        System.out.println("ADDING NEW PRODUCT");
        System.out.println("-".repeat(17));
        Product newProduct = Product.createProduct();

        // Add new item line to file
        pw.printf("\n%s,%s,%.1f,%s", newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getCategory());
        System.out.println("Add new product successfully !");
        pw.close();
    }

    public void removeProduct() throws IOException{
        System.out.println("-".repeat(17));
        System.out.println("REMOVE EXISTED PRODUCT");
        System.out.println("-".repeat(17));
        Scanner sc = new Scanner(System.in);
        System.out.println("What is name of the product you want to delete ?");
        String productName = sc.nextLine();

        File itemFile = new File("./src/File/items.txt");

        // Scan item file
        Scanner fileScanner = new Scanner(itemFile);

        // Initiate variable to store new content
        String newContent = "";

        // Initiate file line number
        int line = 0;

        // Boolean variable if remove item matches line 1
        boolean matchedLine1 = false;

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();

            // Add 1 to line every loop
            line++;

            // Get product from item line
            Product product = Product.generateProduct(item);

            // Check if product id equal remove item id, and line number is 1
            if (product.getName().equals(productName) && line == 1) {
                matchedLine1 = true;
                continue;

            // Check if product id equal remove item id,
            } else if (product.getName().equals(productName)) {
                continue;
            }

            // Boolean variable if line number is 1 or 2 with remove item in line 1, avoid adding new line
            boolean lineCheck = (line == 1 || (line == 2 && matchedLine1));

            // Avoid adding new line at the start of the file if line check is true
            newContent += ((lineCheck ? "" : "\n") + item);
        }
        fileScanner.close();

        // Rewrite item file with new content
        PrintWriter pw = new PrintWriter(new FileWriter(itemFile, false));
        pw.printf(newContent);
        pw.close();

        // Print error if product name doesn't match
        if (Product.checkProductExisted(productName)) {
            System.out.println("Product doesn't exist.Please try again");
        } else {
            System.out.println("Remove product successfully!");
        }
    }

    public void updateProductPrice() throws IOException{
        System.out.println("-".repeat(17));
        System.out.println("UPDATE PRICE OF PRODUCT");
        System.out.println("-".repeat(17));
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the product you want to update?");
        String productName = sc.nextLine();

        // Scan item file
        File itemFile = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(itemFile);

        // Initiate variable to store new content
        String newContent = "";

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            Product product = Product.generateProduct(item);

            if (product.getName().equals(productName)) {

                // Check new price input
                double newPrice = InputValidator.getDoubleInput("New price of the product: ", "Product price must be of integer or double value");
                product.setPrice(newPrice);

                // Update item info line from new product
                item = Product.generateItem(product);
            }

            // If the item reach last line, don't add new line
            newContent += (item + (fileScanner.hasNextLine() ? "\n" : ""));
        }
        fileScanner.close();
        System.out.println("Update the product successfully!");

        // Rewrite item file with new content
        PrintWriter pw = new PrintWriter(new FileWriter(itemFile, false));
        pw.printf(newContent);
        pw.close();

        // Print error if product name doesn't match
        if (!Product.checkProductExisted(productName)) {
            System.out.println("Product doesn't exist. Please try again");
        }
    }
    public void addCategory() throws IOException{
        System.out.println("\nADD NEW CATEGORY\n");
        Scanner sc = new Scanner(System.in);
        System.out.println("What category do you want to add ?");
        String newCategory = sc.nextLine();

        Scanner fileScanner = new Scanner(new File ("./src/File/category.txt"));
        boolean matchCategory = false;

        // Loop through category file
        while (fileScanner.hasNextLine()) {
            String category = fileScanner.nextLine();

            // Check if new category equal category
            if (newCategory.equals(category)) {
                matchCategory = true;
            }
        }
        fileScanner.close();

        // Check if category is available
        if (matchCategory) {
            System.out.println("Category is already available. Please try again");
        } else {
            PrintWriter pw = new PrintWriter(new FileWriter("./src/File/category.txt", true));
            pw.printf("\n" + newCategory);
            pw.close();
        }

        // Check if new category is in category list
        if (!Product.getCategoryList().contains(newCategory)) {
            Product.addCategory(newCategory);
            System.out.println("Add new category successfully !");

        } else {
            System.out.println("Category is already available. Please try again");
        }
    }

    public void removeCategory() throws IOException {
        System.out.println("\nREMOVE EXISTED CATEGORY\n");
        // Check if category exists
        Scanner sc = new Scanner(System.in);
        System.out.println("What category do you want to remove?");
        String removeCategory = sc.nextLine();

        // Initiate variable to store new content
        String newContent = "";

        // Initiate file line number
        int line = 0;

        // Boolean variable if remove item matches line 1
        boolean matchedLine1 = false;

        // Scan category file
        Scanner fileScanner = new Scanner(new File ("./src/File/category.txt"));
        boolean matchCategory = false;

        // Loop through category file
        while (fileScanner.hasNextLine()) {

            // Add 1 to line after each loop
            line++;
            String category = fileScanner.nextLine();

            // Check if remove category equal category
            if (removeCategory.equals(category) && line == 1) {
                matchCategory = true;
                matchedLine1 = true;
                continue;
            } else if (removeCategory.equals(category)) {
                matchCategory = true;
                continue;
            }

            // Boolean variable if line number is 1 or 2 with remove item in line 1, avoid adding new line
            boolean lineCheck = (line == 1 || (line == 2 && matchedLine1));

            // Avoid adding new line at the start of the file if line check is true
            newContent += ((lineCheck ? "" : "\n") + category);
        }
        fileScanner.close();

        // Rewrite item file with new content
        PrintWriter pw = new PrintWriter(new FileWriter("./src/File/category.txt", false));
        pw.printf(newContent);
        pw.close();

        // Print error if category doesn't exist
        if (!matchCategory) {
            System.out.println("Remove category doesn't exist. Please try again.");
        } else {

            // Update category of product with remove category
            updateItemCategory(removeCategory);
        }
    }

    public void updateItemCategory(String category) throws IOException{
        // Scan item file
        File items = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(items);

        // Initiate variable to store new content
        String newContent = "";

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();

            // Get product from item line
            Product product = Product.generateProduct(item);

            // Check if product category equal given category
            if (product.getCategory().equals(category)) {
                product.setCategory("None");
                // Update category to item line
                item = Product.generateItem(product);
            }

            // If the item reach last line, don't add new line
            newContent += (item + (fileScanner.hasNextLine() ? "\n" : ""));
        }

        fileScanner.close();

        // Rewrite item file with new content
        PrintWriter pw = new PrintWriter(new FileWriter(items, false));
        pw.printf(newContent);
        pw.close();
    }
    public void removeCustomer() throws IOException{
        System.out.println("-".repeat(17));
        System.out.println("REMOVE EXISTED CUSTOMER");
        System.out.println("-".repeat(17));
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the ID of the customer you want to remove?");
        String id = sc.next();

        // Boolean variable if customer ID exists
        boolean matchCus = false;
        File customerFile = new File("./src/File/customers.txt");
        Scanner fileScanner = new Scanner(customerFile);

        // Initiate variable to store new content
        String newContent = "";

        // Initiate line number
        int line = 0;

        // Boolean variable if remove customer match line 1
        boolean matchedLine1 = false;

        // Loop through customer file
        while (fileScanner.hasNextLine()) {
            line++;
            String customerInfo = fileScanner.nextLine();
            Customer customer = Customer.generateCus(customerInfo);

            // Check if product id equal remove item id, and line number is 1
            if (customer.getID().equals(id) && line == 1) {
                matchedLine1 = true;
                matchCus = true;
                continue;

                // Check if product id equal remove item id,
            } else if (customer.getID().equals(id)) {
                matchCus = true;
                continue;
            }

            // Boolean variable if line number is 1 or 2 with remove item in line 1, avoid adding new line
            boolean lineCheck = (line == 1 || (line == 2 && matchedLine1));

            // Avoid adding new line at the start of the file if line check is true
            newContent += ((lineCheck ? "" : "\n") + customerInfo);
        }
        fileScanner.close();
        System.out.println("Remove the customer successfully !");
        // Rewrite item file with new content
        PrintWriter pw = new PrintWriter(new FileWriter(customerFile, false));
        pw.printf(newContent);
        pw.close();

        // Print error if customer ID doesn't exist
        if (!matchCus) {
            System.out.println("Customer ID doesn't exist. Please try again");
        }
    }

    public void getOrderByCustomerID() throws IOException {
        System.out.println("-".repeat(17));
        System.out.println("GET ORDER BY CUSTOMER ID");
        System.out.println("-".repeat(17));
        // Create a scanner object to be ready to get input information (customer ID) from users via keyboard.
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the Customer ID: ");
        String customerID = sc.next();

        // Create a scanner object to read from an order text file.
        Scanner scannerOrder = new Scanner(new File("./src/File/orders.txt"));

        // A boolean value to check the item's name existed or not.
        boolean checkCustomerIDExisted = false;

        // Continue to loop through each line of order.txt file to find the ID of customers.
        while (scannerOrder.hasNextLine()) {
            String orderInfo = scannerOrder.nextLine();
            Order order = Order.generateOrder(orderInfo);

            // In case the input ID is equivalent to the customerID from file, the following function would be executed.
            if (order.getCustomerID().equals(customerID)) {
                order.displayOrderInfo();
                checkCustomerIDExisted = true;
            }
        }
        // In case the customer's id is not existed, prompt user a message.
        if (!checkCustomerIDExisted) {
            System.out.println("\nThis customer's id cannot found. Please try with another one.");
        }
        scannerOrder.close();
    }

    public void changeOrderStatus() throws IOException {
        System.out.println("\nCHANGE STATUS OF THE ORDER\n");

        // Get order id
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the order ID: ");
        String orderID = sc.nextLine();

        // Boolean variable if orderID exists
        boolean matchOrderID = false;

        // Initiate variable to store new content
        String newContent = "";

        // Scan order file
        File orders = new File("src/File/orders.txt");
        Scanner fileScanner = new Scanner(orders);

        // Loop through order file
        while (fileScanner.hasNextLine()) {
            String orderInfo = fileScanner.nextLine();

            // Generate order from order info line
            Order order = Order.generateOrder(orderInfo);

            if (order.getOrderID().equals(orderID)) {
                matchOrderID = true;

                // Check order status is paid or not
                if (order.getStatus().equals("paid")) {
                    System.out.println("The order status is already paid");
                } else {

                    // Update status order if not
                    order.setStatus("paid");

                }
            }

            // If the order line reach last line, don't add new line
            newContent += (order.generateOrderLine() + (fileScanner.hasNextLine() ? "\n" : ""));
        }

        fileScanner.close();
        System.out.println("Change the status of the order successfully !");
        // Rewrite item file with new content
        PrintWriter pw = new PrintWriter(new FileWriter(orders, false));
        pw.printf(newContent);
        pw.close();

        // In case the order's id is not existed, prompt user a message.
        if (!matchOrderID) {
            System.out.println("\nThis order's id cannot found. Please try with another one.");
        }
    }

    public static void calculateTotalRevenue(String date) throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Scanner scannerOrder = new Scanner(new File("./src/File/orders.txt"));
        try {
            formatter.parse(date);
            double revenue = 0;
            while (scannerOrder.hasNextLine()) {
                String orderInfo = scannerOrder.nextLine();

                // Generate order from order info line
                Order order = Order.generateOrder(orderInfo);
                if (order.getOrderDate().equals(date)) {
                    double orderTotal = order.getPrice();
                    revenue += orderTotal;
                }
            }
            System.out.printf("\nThe total revenue in %s is %.2f\n", date, revenue);
            scannerOrder.close();
        }
        catch (Exception e) {
            System.out.println("\nInvalid input, please try again.");
        }
    }

    public void getTotalRevenue() throws IOException {
        System.out.println("-".repeat(17));
        System.out.println("CALCULATE THE STORE TOTAL REVENUE IN A PARTICULAR DAY");
        System.out.println("-".repeat(17));
        Scanner scannerOrder = new Scanner(new File("./src/File/orders.txt"));
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter date(yyyy-MM-dd) that you want to calculate the revenue: ");
        String date = sc.nextLine();
        calculateTotalRevenue(date);
        scannerOrder.close();
    }

    public void checkOrderInfoInADay() throws IOException {
        System.out.println("-".repeat(17));
        System.out.println("CHECK THE INFORMATION OF ALL ORDERS EXECUTED IN A PARTICULAR DAY");
        System.out.println("-".repeat(17));
        Scanner scannerOrder = new Scanner(new File("./src/File/orders.txt"));
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the formatted date (dd/MM/yyyy) that you want to check the information of all orders: ");
        String date = sc.nextLine();
        boolean checkOrderExisted = false;

        // Loop through order file
        while (scannerOrder.hasNextLine()) {
            String orderInfo = scannerOrder.nextLine();
            Order order = Order.generateOrder(orderInfo);

            // Check if order date match order date
            if (order.getOrderDate().equals(date)) {
                order.displayOrderInfo();
                checkOrderExisted = true;
            }
        }
        if (!checkOrderExisted) {
            System.out.printf("There isn't any orders in %s, please try with another date", date);
        }
    }

    public void findMostLeastPopularProduct() throws IOException{
        System.out.println("\nVIEW THE MOST AND LEAST POPULAR PRODUCT\n");

        // Get product list and quantity
        HashMap<String, Integer> productList = getProductList();

        // Create array list to find max and min quantity
        ArrayList<Integer> quantity = new ArrayList<>(productList.values());
        int min = Collections.min(quantity);
        int max = Collections.max(quantity);

        // Create array list of product with max and min quantity
        ArrayList<String> maxList = new ArrayList<>();
        ArrayList<String> minList = new ArrayList<>();

        for (String product: productList.keySet()) {
            if (productList.get(product) == max) {
                maxList.add(product);
            } else if (productList.get(product) == min) {
                minList.add(product);
            }
        }

        System.out.println("Most popular product(s): " + maxList + " - Quantity: " + max);
        System.out.println("Least popular product(s): " + minList + " - Quantity: " + min);

    }

    public HashMap<String, Integer> getProductList() throws IOException {
        HashMap<String, Integer> productBought = new HashMap<>();

        // Scan orders file
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));

        // Loop through orders file
        while (sc.hasNextLine()) {
            String orderInfo = sc.nextLine();

            // Generate order from order info line
            Order order = Order.generateOrder(orderInfo);

            // Loop through product name from order product list key set
            for (String productName : order.getProductList().keySet()) {

                // Check if product name is in product bought
                if (productBought.get(productName) == null) {
                    productBought.put(productName, order.getProductList().get(productName));
                } else {

                    // Add order quantity to product bought quantity of that product
                    productBought.put(productName, productBought.get(productName) + order.getProductList().get(productName));
                }
            }
        }
        return productBought;
    }

    public void findMostPaidCustomer() throws IOException{
        System.out.println("\nFIND CUSTOMER PAY THE MOST\n");
        // Create a scanner object to read from a member text file.
        Scanner sc = new Scanner(new File("./src/File/customers.txt"));
        double maxSpending = 0;
        ArrayList<String> maxPaidCustomer = new ArrayList<>();
        // A loop is used to display detailed information of each member.
        while (sc.hasNextLine()) {
            String customerInfo = sc.nextLine();

            // Generate Customer object from customer info
            Customer customer = Customer.generateCus(customerInfo);

            if (customer.getTotalSpending() > maxSpending) {
                maxSpending = customer.getTotalSpending();
                System.out.println(maxSpending);
                maxPaidCustomer.clear();
                maxPaidCustomer.add(customer.getID() + " " + customer.getName());
            } else if (customer.getTotalSpending() == maxSpending) {
                maxPaidCustomer.add(customer.getID() + " " + customer.getName());
            }
        }
        sc.close();

        System.out.println("Customer(s) pay(s) the most: " + maxPaidCustomer);
    }

    public void viewMemberList() throws IOException{
        HashMap<String, Integer> memberList = new HashMap<>();
        memberList.put("Platinum", 0);
        memberList.put("Gold", 0);
        memberList.put("Silver", 0);

        // Create a scanner object to read from a member text file.
        Scanner sc = new Scanner(new File("./src/File/customers.txt"));

        // A loop is used to display detailed information of each member.
        while (sc.hasNextLine()) {
            String memberInfo = sc.nextLine();
            Customer member = Customer.generateCus(memberInfo);

            // Display member only
            if (!member.getMembership().equals("Normal")) {

                // Add 1 to quantity
                memberList.put(member.getMembership(), memberList.get(member.getMembership()) + 1);
            }
        }
        System.out.println("Platinum membership: " + memberList.get("Platinum"));
        System.out.println("Gold membership: " + memberList.get("Gold"));
        System.out.println("Silver membership: " + memberList.get("Silver"));
    }

    public static void main(String[] args) throws IOException{
        Admin admin = new Admin();
        admin.removeCategory();
    }
}
