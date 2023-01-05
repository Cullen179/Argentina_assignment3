import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Customer {
    private String ID;
    private String name;
    private String email;
    private String address;
    private String phoneNumb;
    private String membership;
    private String username;
    private String password;

    private double totalSpending;
    private HashMap<String, Integer> boughtList = new HashMap<>();

    public Customer(String ID, String name, String email, String address, String phoneNumb, String membership, String username, String password, double totalSpending) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumb = phoneNumb;
        this.membership = membership;
        this.username = username;
        this.password = password;
        this.totalSpending = totalSpending;
    }


    public static Customer generateCus(String customer) {
        String[] customerInfo = customer.split(",");
        String ID = customerInfo[0];
        String name = customerInfo[1];
        String email = customerInfo[2];
        String address = customerInfo[3];
        String phoneNumb = customerInfo[4];
        String membership = customerInfo[5];
        String username = customerInfo[6];
        String password = customerInfo[7];
        double totalSpending = Double.parseDouble(customerInfo[8]);
        return new Customer(ID, name, email, address, phoneNumb, membership, username, password, totalSpending);
    }

    // Rewrite
    public HashMap<String, Integer> getBoughtList() throws IOException {
        HashMap<String, Integer> productBought = new HashMap<>();

        // Scan orders file
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));

        // Loop through orders file
        while (sc.hasNextLine()) {
            String orderInfo = sc.nextLine();

            // Generate order from order info line
            Order order = Order.generateOrder(orderInfo);

            // Check if order customer ID matches customer ID
            if (order.getCustomerID().equals(ID)) {

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
        }
        return productBought;
    }

    public int getHighestBoughtQuantity() throws IOException {
        int maxQuan = 0;
        for (int quantity : getBoughtList().values()) {
            if (quantity > maxQuan) {
                maxQuan = quantity;
            }
        }
        return maxQuan;
    }

    public ArrayList<String> getHighestBoughtProduct() throws IOException {
        ArrayList<String> list = new ArrayList<>();
        int highestQuantity = getHighestBoughtQuantity();
        for (String product : getBoughtList().keySet()) {
            if (getBoughtList().get(product) == highestQuantity) {
                list.add(product);
            }
        }
        return list;
    }

    // Rewrite
    // Function for customer to register new account
    public static void registerMember() throws IOException {
        //Declare attribute
        String line, ID, username, password, fileName, name, email, address, phoneNumb;
        double totalSpending = 0;
        //create a scanner setup for user inputs
        Scanner scanner = new Scanner(System.in);

        //get customer's name
        System.out.println("Enter name:");
        name = scanner.nextLine();

        //a loop to get customer's email
        while (true) {
            System.out.println("Enter email");
            email = scanner.nextLine();
            if (email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                break;
            } else {
                System.out.println("Wrong format for an email. Please enter email again");
            }
        }
        //get customer's address
        System.out.println("Enter address: ");
        address = scanner.nextLine();
        while (true) {
            System.out.println("Enter password:");
            password = scanner.nextLine();
            while (true) {
                System.out.println("Enter phone number:");
                phoneNumb = scanner.nextLine();
                if (phoneNumb.matches("^\\d{10}$")) {
                    break;
                } else {
                    System.out.println("Wrong format for a phone number. Please enter phone number again");
                }
            }
            while (true) {
                System.out.println("Enter username:");
                username = scanner.nextLine();
                if (!checkUsername(username)) {
                    break;
                } else {
                    System.out.println("This username has already used! Please enter different username");
                }
            }
            if (!checkPassword(password)) {
                break;
            } else {
                System.out.println("This password has already used! Please enter different username");
            }
        }
        System.out.println("Register successful");
        fileName = "./src/File/customers.txt";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(fileName, true));
            pw.printf("%s,%s,%s,%s,%s,%s,%s,%s,%1.f" + (scanner.hasNextLine() ? "\n" : ""), newCustomerID(), name, email, address, phoneNumb, "Normal", username, password, totalSpending);
            pw.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        pw.close();
    }

    //Function to create new customer ID
    public static String newCustomerID() throws IOException{
        Scanner sc = new Scanner(new File("D:\\Java project\\group asm\\customer.txt"));

        // Initialize lastItem
        String lastCustomer = "";

        // Loop through items file
        while (sc.hasNextLine()) {
            String customer = sc.nextLine();

            // Check if the current line is the last line
            if (!sc.hasNextLine()) {
                lastCustomer = customer;
            }
        }

        // Get item ID
        String customerID = lastCustomer.split(",")[0];
        // Get number of item ID
        int idNumber = Integer.parseInt(customerID.replace("C", ""));

        // Return ID
        return String.format("C%03d", idNumber + 1);
    }

    // Function to check the unique of the input username
    private static boolean checkUsername(String inputUsername) throws IOException {
        Boolean usernameExist = false;
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (inputUsername.equals(customer.getUsername())) {
                usernameExist = true;
                System.out.println("Username already exist");
                break;
            }
        }
        return usernameExist;
    }

    // Function to check the unique of the input password
    private static boolean checkPassword(String inputPassword) throws IOException {
        Boolean passwordExist = false;
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (inputPassword.equals(customer.getPassword())) {
                passwordExist = true;
                System.out.println("Password already exist");
                break;
            }
        }
        return passwordExist;
    }

    // Function for the customer to login as a role member
    public Customer login() throws IOException {
        Boolean loggedIn = false;
        Scanner scanner = new Scanner(System.in);
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        
        Customer user = null;
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {
                loggedIn = true;
                System.out.println("Successfully login");

                // Assign user to customer
                user = customer;
                break;
            }
        }
        if (loggedIn.equals(false)) {
            System.out.println("Incorrect username or password");
        }
        fileScanner.close();
        return user;
    }

    // Function to display the customer account information
    public void displayAccountInfo() throws IOException {
        System.out.println("-".repeat(17));
        System.out.println("ID:" + this.getID());
        System.out.println("Name:" + this.getName());
        System.out.println("Email:" + this.getEmail());
        System.out.println("Address:" + this.getAddress());
        System.out.println("Phone number:" + this.getPhoneNumb());
        System.out.println("Membership:" + this.getMembership());
        System.out.println("Username:" + this.getUsername());
        System.out.println("Total spending:" + this.getTotalSpending());
        System.out.println("-".repeat(17));
    }

    // Function to replace the old content with the new content in text file
    public static void modifyFile(String filePath, String oldString, String newString) throws IOException{
        File file = new File(filePath);
        String oldContent = "";
        BufferedReader reader = new BufferedReader(new FileReader(file));

        //Reading all the lines of input text file into oldContent
        String line = reader.readLine();
        int lineIndex = 0;

        while (line != null) {
            oldContent = oldContent + (lineIndex==0 ? "":"\n") + line;
            line = reader.readLine();
            lineIndex++;
        }

        //Replacing oldString with newString in the oldContent
        String newContent = oldContent.replaceAll(oldString, newString);

        //Rewriting the input text file with newContent
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter(file, false));
            pw.printf(newContent);
            pw.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        pw.close();
    }

    public static void modifyFile2(String filePath, String oldString, String newString) {
        File file = new File(filePath);
        String oldContent = "";
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            //Reading all the lines of input text file into oldContent
            String line = reader.readLine();
            int lineIndex = 0;

            while (line != null) {
                oldContent = oldContent + (lineIndex == 0 ? "" : "\n") + line;
                line = reader.readLine();
                lineIndex++;
            }
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            ioe.printStackTrace();
        }

        //Replacing oldString with newString in the oldContent
        String newContent = oldContent.replaceAll(oldString, newString);

        //Rewriting the input text file with newContent
        try (PrintWriter pw = new PrintWriter(new FileWriter(file, false))) {
            pw.printf(newContent);
            pw.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
            ioe.printStackTrace();
        }
    }

    // Function to update account information of the customer
    public void updateAccountInfo() throws IOException {
        String newData;
        String fileName = "./src/File/customers.txt";
        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())) {
                while (true) {
                    String name = customer.getName();
                    String email = customer.getEmail();
                    String address = customer.getAddress();
                    String phoneNumb = customer.getPhoneNumb();
                    System.out.println("Choose the number to change the information you want to change:");
                    System.out.println("(1) - Name");
                    System.out.println("(2) - Email");
                    System.out.println("(3) - Address");
                    System.out.println("(4) - Phone number");
                    String askCustomer = scanner.nextLine();
                    switch (askCustomer) {
                        case "1":
                            System.out.println("Enter new name");
                            name = scanner.nextLine();
                            break;
                        case "2":
                            while (true) {
                                System.out.println("Enter email");
                                email = scanner.nextLine();
                                if (email.matches("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$")) {
                                    break;
                                } else {
                                    System.out.println("Wrong format for an email. Please enter email again");
                                }
                            }
                            break;
                        case "3":
                            System.out.println("Enter new address");
                            address = scanner.nextLine();
                            break;
                        case "4":
                            while (true) {
                                System.out.println("Enter phone number:");
                                phoneNumb = scanner.nextLine();
                                if (phoneNumb.matches("^\\d{10}$")) {
                                    break;
                                } else {
                                    System.out.println("Wrong format for a phone number. Please enter phone number again");
                                }
                            }
                            break;
                    }
                    newData = customer.getID() + "," + name + "," + email + "," + address + "," + phoneNumb + "," + customer.getMembership() + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getTotalSpending();
                    modifyFile(fileName, line, newData);
                }
            }
        }
    }

    // Function to get the total spending of the customer form the orders file
    public double getTotalSpending() throws IOException {
        double totalSpending = 0;
        String fileName = "./src/File/orders.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Order order = Order.generateOrder(line);
            if (this.getID().equals(order.getCustomerID()) && (order.getStatus().equals("paid"))) {
                totalSpending += order.getPrice();
            }
        }
        this.totalSpending = totalSpending;
        return this.totalSpending;
    }

    // Function to update the total spending of the customer
    public void updateTotalSpending() throws IOException {

        // get total spending of the customer
        String newData;
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())) {
                newData = customer.generateCustomerInfo();
                modifyFile(fileName, line, newData);
                break;
            }
        }
    }

    // Function to update customer's membership depend on the total spending
    public void updateMembership() throws IOException {
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            System.out.println(customer.getTotalSpending());
            if (this.getUsername().equals(customer.getUsername())) {
                String newMembership = customer.getMembership();
                if (customer.getTotalSpending() > 25000000) {
                    newMembership = "platinum";
                } else if (customer.getTotalSpending() > 10000000) {
                    newMembership = "gold";
                } else if (customer.getTotalSpending() > 5000000) {
                    newMembership = "silver";
                }
                this.setMembership(newMembership);
                String newData = this.generateCustomerInfo();
                modifyFile(fileName, line, newData);
                break;
            }
        }
    }

    // Function to generate customer information
    public String generateCustomerInfo() throws IOException {
        return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%.1f", ID, name, address, email, phoneNumb, getMembership(), username, password, getTotalSpending());
    }

    // Function for customers to view the store's products
    public static void viewProduct() throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));
        System.out.println("\nVIEW PRODUCT\n");
        // Read each line in items.txt file, then generate and display the products.
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            Product product = Product.generateProduct(items);
            product.getProductDetails();
        }
    }

    // Function for customers to search the store's products based on category and price range
    public static void searchProduct() throws IOException {
        String category = "";
        double minimum = 0;
        double maximum;

        // use try-catch in case the customer did not enter the minimum and/or the maximum
        // the default minimum is 0 and that of maximum is positive infinity

        try {
            category = Product.checkCategory();
        } catch (IOException ioException) {
            System.out.println("Cannot check category!");
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the minimum price: ");
        String minimumString = sc.nextLine();
        try {
            minimum = Double.parseDouble(minimumString);
        } catch (NumberFormatException nfe) {
            minimum = 0;
        }
            System.out.println("Enter the maximum price: ");
            String maximumString = sc.nextLine();
            System.out.println("--------------");
        try {
            maximum = Double.parseDouble(maximumString);
            System.out.println("--------------");
        } catch (NumberFormatException nfe) {
            maximum = Double.POSITIVE_INFINITY;
        }

        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));
        while (scannerProduct.hasNext()) {
            String items = scannerProduct.nextLine();
            // generate a product from each line
            Product product = Product.generateProduct(items);
            // if the search category matches the category and the price range => display the product(s)
            if (product.getCategory().equals(category) && minimum <= product.getPrice() && product.getPrice() <= maximum) {
                product.getProductDetails();
            }
        }
    }

    // Function for customers to sort the store's products in ascending or descending order
    public static void sortProducts() throws IOException {
        boolean correctInput = false;
        int sortOrder;

        // Notify user that only input 0 (for ascending order) and 1 (for descending order) is available
        do {
            System.out.println("Choose the way you want to sort");
            System.out.println("0.Ascending");
            System.out.println("1.Descending");
            sortOrder = InputValidator.getIntInput("", "Input must be of integer value. Please try again");
            if (sortOrder == 0 || sortOrder == 1)
                correctInput = true;
            else
                System.out.println("Wrong input! Enter 0 or 1 only. Please try again");
        } while (!correctInput);

        File file = new File("./src/File/items.txt");
        Scanner productScanner = new Scanner(file);

        // initialise an array list storing the products
        List<Product> productsList = new ArrayList<>();

        // Reading items.txt file and create new Product to put in Array List productsList
        while (productScanner.hasNextLine()) {
            String item = productScanner.nextLine();
            Product product = Product.generateProduct(item);
            productsList.add(product);
        }

        int finalSortOrder = sortOrder;

        // Sort products inside the productsList array using Comparator
        productsList.sort(new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                if (finalSortOrder == 0) {
                    if (o1.getPrice() == o2.getPrice())
                        return 0;
                    else if (o1.getPrice() > o2.getPrice())
                        return 1;
                    else
                        return -1;
                } else {
                    if (o1.getPrice() == o2.getPrice())
                        return 0;
                    else if (o1.getPrice() > o2.getPrice())
                        return -1;
                    else
                        return 1;
                }
            }
        });

        // display the details of the products in the sorted order.
        for (Product product : productsList) {
            product.getProductDetails();
        }
    }


    // function to apply membership discount
    private double applyDiscount(double beforeDiscount) {
        switch (this.getMembership()) {
            case "platinum":
                return beforeDiscount * 85 / 100;
            case "gold":
                return beforeDiscount * 90 / 100;
            case "silver":
                return beforeDiscount * 95 / 100;
            default:
                return beforeDiscount;
        }
    }

    // Function for customers to create order(s)
    public void createOrder() throws IOException {

        // create a scanner to receive customer's input
        Scanner customerInput = new Scanner(System.in);

        // a hash map to store each product and its quantity
        HashMap<String, Integer> orderProducts = new HashMap<>();

        // total cost of the order
        double total = 0;

        boolean continueToBuy = true;

        // loop when customer still want to buy more products
        while (continueToBuy) {
            // display all the products
            viewProduct();

            String productName = null;
            double productPrice = 0;

            // if the customer's input does not match any products in items.txt file, ask the user to re-enter the product name
            while (true) {

                // a scanner for items.txt file
                Scanner productFileScanner = new Scanner(new File("./src/File/items.txt"));

                // getting the product name from users
                System.out.println("Enter the name of the item you want to buy:");
                String itemName = customerInput.nextLine();

                // a loop to go through each line in the items.txt file
                while (productFileScanner.hasNextLine()) {

                    // getting the name of each product
                    String items = productFileScanner.nextLine();
                    Product product = Product.generateProduct(items);

                    // if the current product's name match with the input, set the product variables
                    if (product.getName().equals(itemName)) {
                        productName = product.getName();
                        productPrice = product.getPrice();
                        break;
                    }
                }
                // notify the user when there was no matching product
                if (!Product.checkProductExisted(itemName)) {
                    System.out.println("Can't find the product with matching name. Please try again.");
                } else {
                    break;
                }
            }

            // ask the user for the quantity of the product they want to purchase
            int quantity = InputValidator.getIntInput("How many do you want to buy?",
                    "Quantity value must be of integer number!");

            // update the total cost of the order
            total += productPrice * quantity;

            // update hash map with the product and its quantity.
            // Use if-else condition in case customers want to add the product that is already in their cart

            if (orderProducts.containsKey(productName)) {
                orderProducts.put(productName, quantity + orderProducts.get(productName));
            } else {
                orderProducts.put(productName, quantity);
            }


            // a boolean to track if the user enter the right option
            boolean validInput = false;

            while (!validInput) {
                System.out.println("Do you want to continue to buy? (Y or N)");
                String isContinue = customerInput.nextLine();

                // prevent the case customer enters something apart from Y or N.
                if (isContinue.equalsIgnoreCase("Y")) {
                    System.out.println("-----------------");
                    validInput = true;
                } else if (isContinue.equalsIgnoreCase("N")) {
                    System.out.println("-----------------");
                    continueToBuy = false;
                    validInput = true;
                } else {
                    System.out.println("Wrong input! Y or N only! Please try again");
                }
            }
        }

        // let the customer enter the shipping address
        System.out.println("Your shipping address is:");
        String shippingAddress = customerInput.nextLine();

        // generate new order id
        String orderId = null;
        try {
            orderId = Order.newOrderId();
        } catch (IOException ioException) {
            System.out.println("Cannot generate order ID");
        }

        // getting the date for the order
        String orderDate = LocalDate.now().toString();

        String currentOrderProducts = String.join(":", orderProducts.keySet());
        ArrayList<String> quantityList = new ArrayList<>();
        for (int quantity : orderProducts.values()) {
            quantityList.add(Integer.toString(quantity));
        }
        String currentOrderProductQuantities = String.join(":", quantityList);

        // applying discount to the total cost of the order
        double totalAfterDiscount = applyDiscount(total);

        // append the line for the new order to the end of the orders.txt file
        String newOrder = String.join(",", orderId, this.getID(), orderDate, shippingAddress,
                currentOrderProducts, currentOrderProductQuantities, String.valueOf(totalAfterDiscount), "delivered");
        Order order = Order.generateOrder(newOrder);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("./src/File/orders.txt", true));
            pw.printf("\n" + newOrder);
            pw.flush();
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        } finally {
            if (pw != null) {
                pw.close();
            }
        }
        
        // display order details
        order.displayOrderInfo();
    }

    // Function for customers to find the details of a specific order based on order ID
    public static void findOrderDetails() throws IOException {
        boolean matched = false;
        System.out.println("Please enter the orderID: ");

        Scanner userInput = new Scanner(System.in);
        String inputOrderID = userInput.nextLine();
        Scanner orderFileScanner = new Scanner(new File("./src/File/orders.txt"));
        // read each line of orders.txt and generate the orders in that file.
        while (orderFileScanner.hasNext()) {
            String orderLine = orderFileScanner.nextLine();
            Order order = Order.generateOrder(orderLine);

            // when the input equals to the order ID, display information of that order
            if (inputOrderID.equals(order.getOrderID())) {
                order.displayOrderInfo();
                matched = true;
            }
        }
        // notify the customer when the order ID is incorrect
        if (!matched) {
            System.out.println("Can't find the order ID. Please try again.");
        }
    }

    // Function for customers to view all of their previous orders
    public void displayPreviousOrders() throws IOException {
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));

        // Loop through order file
        while (sc.hasNextLine()) {
            String orderInfo = sc.nextLine();

            // Generate and display order(s) of that specific customer
            Order order = Order.generateOrder(orderInfo);
            if (order.getCustomerID().equals(this.ID)) {
                order.displayOrderInfo();
            }
        }
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumb() {
        return phoneNumb;
    }

    public void setPhoneNumb(String phone) {
        this.phoneNumb = phone;
    }

    public String getMembership() {
        return membership;
    }

    public void setMembership(String membership) {
        this.membership = membership;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

}

