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
    private HashMap<String, Integer> boughtList = new HashMap<String, Integer>();

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
        HashMap<String, Integer> productBought = new HashMap<String, Integer>();

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
        ArrayList<String> list = new ArrayList<String>();
        int highestQuantity = getHighestBoughtQuantity();
        for (String product : getBoughtList().keySet()) {
            if (getBoughtList().get(product) == highestQuantity) {
                list.add(product);
            }
        }
        return list;
    }

    // Function for customer to register new account
    public void registerMember() throws IOException {
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
        int count = 0;
        fileName = "./src/File/customers.txt";
        PrintWriter pw = new PrintWriter(new FileWriter(fileName, true));
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            line = fileScanner.nextLine();
            StringTokenizer inReader = new StringTokenizer(line, ",");
            count++;
        }
        count += 1;
        pw.println(count + "," + name + "," + email + "," + address + "," + phoneNumb + "," + "none" + "," + username + "," + password + "," + totalSpending);
        pw.close();
        fileScanner.close();
    }

    private boolean checkUsername(String inputUsername) throws IOException {
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

    private boolean checkPassword(String inputPassword) throws IOException {
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

    public void login() throws IOException {
        Boolean loggedin = false;
        Scanner scanner = new Scanner(System.in);
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter password:");
        String password = scanner.nextLine();
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (username.equals(customer.getUsername()) && password.equals(customer.getPassword())) {
                loggedin = true;
                System.out.println("successful login");
                break;
            }
        }
        if (loggedin.equals(false)) {
            System.out.println("Incorrect username or password");
        }
        fileScanner.close();
    }

    // Written
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

    static void modifyFile(String filePath, String oldString, String newString) {
        File fileToBeModified = new File(filePath);

        String oldContent = "";

        BufferedReader reader = null;

        FileWriter writer = null;

        try {
            reader = new BufferedReader(new FileReader(fileToBeModified));

            //Reading all the lines of input text file into oldContent

            String line = reader.readLine();

            while (line != null) {
                oldContent = oldContent + line + System.lineSeparator();

                line = reader.readLine();
            }

            //Replacing oldString with newString in the oldContent

            String newContent = oldContent.replaceAll(oldString, newString);

            //Rewriting the input text file with newContent

            writer = new FileWriter(fileToBeModified);

            writer.write(newContent);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //Closing the resources

                reader.close();

                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

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
                    String adress = customer.getAddress();
                    String phoneNumb = customer.getPhoneNumb();
                    System.out.println("Choose the number to change the information you want to change:");
                    System.out.println("(1)Name");
                    System.out.println("(2)Email");
                    System.out.println("(3)Adress");
                    System.out.println("(4)Phone number");
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
                            adress = scanner.nextLine();
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
                    newData = customer.getID() + "," + name + "," + email + "," + adress + "," + phoneNumb + "," + customer.getMembership() + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getTotalSpending();
                    modifyFile(fileName, line, newData);
                    System.out.println("Do you want to change information again");
                    System.out.println("(1) Yes");
                    System.out.println("(2) No");
                    String continueChange = scanner.nextLine();
                    if (continueChange.equals("2")) {
                        break;
                    }
                }
            }
        }
    }

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

    public void updateTotalSpending() throws IOException {
        // get total spending of the customer
        double inputTotalSpeding = getTotalSpending();

        String newData;
        double newTotalSpending = 0;
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())) {
                System.out.println(customer.getTotalSpending());
                newTotalSpending = customer.getTotalSpending() + inputTotalSpeding;
                System.out.println(newTotalSpending);
                newData = customer.getID() + "," + customer.getName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumb() + "," + customer.getMembership() + "," + customer.getUsername() + "," + customer.getPassword() + "," + newTotalSpending;
                modifyFile(fileName, line, newData);
                break;
            }
        }
    }

    public void updateMembership() throws IOException {
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())) {
                String newMembership = customer.getMembership();
                if (customer.getTotalSpending() >= 300000) {
                    newMembership = "platinum";
                } else if (customer.getTotalSpending() >= 200000) {
                    newMembership = "gold";
                } else if (customer.getTotalSpending() >= 100000) {
                    newMembership = "silver";
                }
                String newData = customer.getID() + "," + customer.getName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumb() + "," + newMembership + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getTotalSpending();
                modifyFile(fileName, line, newData);
                break;
            }
        }
    }

    public static void viewProduct() throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));
        System.out.println("VIEW PRODUCT");
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            Product product = Product.generateProduct(items);
            product.getProductDetails();
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

    public static void searchProduct() throws IOException {
        String category = "";
        double minimum = 0;
        double maximum = Double.POSITIVE_INFINITY;

        try {
            category = Product.checkCategory();
            Scanner sc = new Scanner(System.in);
            System.out.println("Enter the minimum price: ");
            String minimumString = sc.nextLine();
            try {
                if (minimumString == null)
                    minimum = 0;
                else
                    minimum = Double.parseDouble(minimumString);
            } catch (NumberFormatException nfe) {
                minimum = 0;
            }
            System.out.println("Enter the maximum price: ");
            String maximumString = sc.nextLine();
            System.out.println("--------------");
            if (maximumString != null)
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
            if (product.getCategory().equals(category) && minimum < product.getPrice() && product.getPrice() < maximum) {
                product.getProductDetails();
            }
        }
    }


    public static void sortProducts() throws IOException {
        boolean correctInput = false;
        int sortOrder;

        // Notify user that only input 0 and 1 is available
        do {
            System.out.println("Choose the way you want to sort");
            System.out.println("0.Ascending");
            System.out.println("1.Descending");
            Scanner sort = new Scanner(System.in);
            sortOrder = sort.nextInt();
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

        for (Product product : productsList) {
            product.getProductDetails();
        }
    }


    // apply membership discount
    private double applyDiscount(double beforeDiscount) throws IOException {
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

            String chosenProductName = null;
            double chosenProductPrice = 0;
            boolean productMatched = false;

            // if the customer's input does not match any products in items.txt file, ask the user to re-enter the product name
            while (!productMatched) {

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
                        chosenProductName = product.getName();
                        chosenProductPrice = product.getPrice();
                        productMatched = true;
                    }
                }

                // notify the user when there was no matching product
                if (!productMatched) {
                    System.out.println("Can't find the product with matching name. Please try again.");
                }
            }

            // ask the user for the quantity of the product they want to purchase
            int quantity = InputValidator.getIntInput("How many do you want to buy?\n",
                    "Only enter int number!");

            // update the total cost of the order
            total += chosenProductPrice * quantity;

            // update hash map with the product and its quantity.
            // Use if-else condition in case customers want to add the product that is already in their cart

            if (orderProducts.containsKey(chosenProductName)) {
                orderProducts.put(chosenProductName, quantity + orderProducts.get(chosenProductName));
            } else {
                orderProducts.put(chosenProductName, quantity);
            }


            // a boolean to track if the user enter the right option
            boolean validInput = false;

            System.out.println("Do you want to continue to buy? (Y or N)");
            while (!validInput) {
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

                    System.out.println("Do you want to continue to buy? (Y or N)");
                }
            }
        }

        // let the customer enter the shipping address
        System.out.println("Your shipping address is:");
        String shippingAddress = customerInput.nextLine();


        // generate new order id
        String orderId = Order.newOrderId();

        // getting the date for the order
        String orderDate = LocalDate.now().toString();

        // getting the list of all products in the order and their quantities
        Object[] productsList = orderProducts.keySet().toArray();
        Object[] quantityList = orderProducts.values().toArray();


        // generate strings to store the list of products and their quantities
        StringBuilder currentOrderProducts = new StringBuilder(productsList[0].toString());
        StringBuilder currentOrderProductQuantities = new StringBuilder(quantityList[0].toString());
        for (int i = 1; i < orderProducts.size(); i++) {
            currentOrderProducts.append(":").append(productsList[i].toString());
        }
        for (int i = 1; i < orderProducts.size(); i++) {
            currentOrderProductQuantities.append(":").append(quantityList[i].toString());
        }

        // applying discount to the total cost of the order
        double totalAfterDiscount = applyDiscount(total);


        // append the line for the new order to the end of the orders.txt file
        String newOrder = String.join(",", orderId, this.getID(), orderDate, shippingAddress,
                currentOrderProducts.toString(), currentOrderProductQuantities.toString(), String.valueOf(totalAfterDiscount), "delivered");
        Order order = Order.generateOrder(newOrder);
        PrintWriter pw = new PrintWriter(new FileWriter("./src/File/orders.txt", true));
        pw.println(order.generateOrderLine());

        pw.flush();
        pw.close();

        // display order details
        order.displayOrderInfo();

        // update the membership for the user
    }

    public static void main(String[] args) throws IOException {
//        Customer cus = new Customer("C001", "Ngoc", "fafaf@gmail.com", "454540", "009909", "silver", "hiii", "hello", 133);
//        cus.createOrder();
        findOrderDetails();
    }


    public static void findOrderDetails() throws IOException {
        boolean notMatched = true;

        // notify the user about wrong input when necessary
        int count = 0;

        while (notMatched) {
            Scanner userInput = new Scanner(System.in);
            if (count == 0)
                System.out.println("Please enter the orderID: ");
            else
                System.out.println("Can't find the order ID. Please enter another order ID");
            String inputOrderID = userInput.nextLine();

            Scanner orderFileScanner = new Scanner(new File("./src/File/orders.txt"));
            while (orderFileScanner.hasNext()) {
                String orderLine = orderFileScanner.nextLine();
                Order order = Order.generateOrder(orderLine);

                if (inputOrderID.equals(order.getOrderID())) {
                    order.displayOrderInfo();
                    notMatched = false;
                }
            }
            count++;
        }
    }
}
//
//    public static void displayPreviousOrders() throws IOException {
//        boolean notMatched = true;
//
//        // notify the user about wrong input when necessary
//        int count = 0;
//
//        while (notMatched) {
//            Scanner userInput = new Scanner(System.in);
//            if (count == 0)
//                System.out.println("Please enter your username:");
//            else
//                System.out.println("Can't find the username. Please enter another username");
//            String inputCustomerName = userInput.nextLine();
//
//            Scanner orderFileScanner = new Scanner(new File("src/orders.txt"));
//            while (orderFileScanner.hasNext()) {
//                String orderLine = orderFileScanner.nextLine();
//                String[] orderInfo = orderLine.split(",");
//                String customerName = orderInfo[3];
//                if (inputCustomerName.equals(customerName)) {
//                    Order.displayOrderInfo(orderLine);
//                    notMatched = false;
//                }
//            }
//            count++;
//        }
//    }
//
//    public static void orderHash() {
//        HashMap<String, Integer> total = new HashMap<String, Integer>();
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("what product buy");
//            String product = sc.next();
//            System.out.println("quantity");
//            int quantity = sc.nextInt();
//            if (total.get(product) == null) {
//                total.put(product, quantity);
//            } else {
//                total.put(product, total.get(product) + quantity);
//            }
//            System.out.println("continue");
//            boolean option = sc.nextBoolean();
//            if (option == true) {
//                continue;
//            } else {
//                break;
//            }
//        }
//        System.out.println(total);
//    }
//

//
//    public HashMap<String, Integer> buyProduct() {
//        HashMap<String, Integer> total = new HashMap<String, Integer>();
//        Scanner sc = new Scanner(System.in);
//        while (true) {
//            System.out.println("what product buy");
//            String product = sc.next();
//            System.out.println("quantity");
//            int quantity = sc.nextInt();
//            if (total.get(product) == null) {
//                total.put(product, quantity);
//            } else {
//                total.put(product, total.get(product) + quantity);
//            }
//            System.out.println("continue");
//            boolean option = sc.nextBoolean();
//            if (option == true) {
//                continue;
//            } else {
//                break;
//            }
//        }
//        return total;
//    }
//
//    public static void main(String[] args) throws IOException {
//        orderHash();
//    }