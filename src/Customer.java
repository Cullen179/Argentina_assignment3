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
    private static HashMap<String, Integer> productBought = new HashMap<String, Integer>();

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
    public HashMap<String, Integer> getProductBought() throws IOException{
        HashMap<String, Integer> productBought = new HashMap<String, Integer>();
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));
        while (sc.hasNextLine()) {
            String orderLine = sc.nextLine();
            Order order = Order.generateOrderDetail(orderLine);
            if (order.getCustomerID().equals(this.ID)) {
                if (productBought.get(order.getOrderProduct()) == null) {
                    productBought.put(order.getOrderProduct(), order.getProductNum());
                } else {
                    productBought.put(order.getOrderProduct(), productBought.get(order.getOrderProduct()) + order.getProductNum());
                }
            }
        }
        this.productBought = productBought;
        return this.productBought;
    }

    // REWRITE
    public ArrayList<String> highestBoughtProduct() throws IOException {
        ArrayList<String> list = new ArrayList<String>();
        int maxNum = 0;
        for (String product: getProductBought().keySet()) {
            if (productBought.get(product) > maxNum) {
                list.clear();
                maxNum = productBought.get(product);
                list.add(product);
            } else if (productBought.get(product) == maxNum) {
                list.add(product);
            }
        }
        return list;
    }

    // REWRITE
    public int highBoughtAmount() throws IOException {
        int maxNum = 0;
        for (String product: getProductBought().keySet()) {
            if (productBought.get(product) > maxNum) {
                maxNum = productBought.get(product);
            }
        }
        return maxNum;
    }

    // Function for customer to register new account
    public void registerMember () throws IOException {
        //Declare attribute
        String line, ID, username, password, fileName, name, email, address, phoneNumb;
        double totalSpending = 0;
        //create a scanner setup for user inputs
        Scanner scanner = new Scanner(System.in);

        //get customer's name
        System.out.println("Enter name:");
        name = scanner.nextLine();

        // a loop to get customer's email
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

        // a loop to get customer's phone number
        while (true) {
            System.out.println("Enter phone number:");
            phoneNumb = scanner.nextLine();
            if (phoneNumb.matches("^\\d{10}$")) {
                break;
            } else {
                System.out.println("Wrong format for a phone number. Please enter phone number again");
            }
        }

        // a loop to get customer's username
        while (true) {
            System.out.println("Enter username:");
            username = scanner.nextLine();
            if (!checkUsername(username)) {
                break;
            } else {
                System.out.println("This username has already used! Please enter different username");
            }
        }

        // a loop to get customer's password
        while (true) {
            System.out.println("Enter password:");
            password = scanner.nextLine();
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
    private boolean checkUsername (String inputUsername) throws IOException {
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

    private boolean checkPassword (String inputPassword) throws IOException {
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
    public void login () throws IOException {
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
        if(loggedin.equals(false)){
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
        System.out.println("Adress:" + this.getAddress());
        System.out.println("Phone number:" + this.getPhoneNumb());
        System.out.println("Membership:" + this.getMembership());
        System.out.println("Username:" + this.getUsername());
        System.out.println("Total spending:" + this.getTotalSpending());
        System.out.println("-".repeat(17));
    }

    static void modifyFile (String filePath, String oldString, String newString)
    {
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
    public void updateAccountInfo ()throws IOException {
        String  newData;
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
                    if (continueChange.equals("2")){
                        break;
                    }
                }
            }
        }
    }

    public void updateTotalSpending(double inputTotalSpeding) throws IOException {
        String newData;
        double newTotalSpending = 0;
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())){
                System.out.println(customer.getTotalSpending());
                newTotalSpending = customer.getTotalSpending() + inputTotalSpeding;
                System.out.println(newTotalSpending);
                newData = customer.getID() + "," + customer.getName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumb() + ","+ customer.getMembership() + "," + customer.getUsername() + "," + customer.getPassword() + "," + newTotalSpending;
                modifyFile(fileName,line,newData);
                break;
            }
        }
    }
    public void updateMembership() throws IOException{
        //
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())){
                String newMembership = customer.getMembership();
                if(customer.getTotalSpending() > 25000000){
                    newMembership = "platinum";
                } else if(customer.getTotalSpending() > 10000000){
                    newMembership = "gold";
                } else if (customer.getTotalSpending() > 5000000){
                    newMembership = "silver";
                }
                String newData = customer.getID() + "," + customer.getName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumb() + ","+ newMembership + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getTotalSpending();
                modifyFile(fileName,line,newData);
                break;
            }
        }
    }
    public static void viewProduct() throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/items.txt"));
        System.out.println("VIEW PRODUCT");
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            Product.getproductDetails(items);
        }
    }

    public static void listProductByCategory() throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/items.txt"));
        System.out.print("Type your category to search for the needed product: ");
        Scanner input = new Scanner(System.in);
        String categoryInput = input.nextLine();

        boolean checkCategory = false;
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            String[] itemsInfo = items.split(",");
            String category = itemsInfo[3];

            if (categoryInput.equals(category)) {
                checkCategory = true;
                Product.getproductDetails(items);
            }
        }
        if (!checkCategory) {
            System.out.println("There isn't any products belong to this category.");
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

    public double getTotalSpending() {
        return totalSpending;
    }

    public void setTotalSpending(double totalSpending) {
        this.totalSpending = totalSpending;
    }

    public static void viewProducts() throws IOException {
        File file = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(file);
        String line;

        while (fileScanner.hasNext()) {
            // Read the whole line of the file
            line = fileScanner.nextLine();
            System.out.println(line);
        }
    }

    public static void searchProduct() throws IOException {
        // Open and read each line of the Product file
        File file = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(file);
        String line;

        // A scanner to take user input (category)
        Scanner sc = new Scanner(System.in);

        String searchCategory = "";
        double minimum = 0;
        double maximum = 10000000;

        try {
            System.out.println("Enter the category you want to search: ");
            searchCategory = sc.nextLine();
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
            maximum = 10000000;
        }

        // variable to count whether there is any matches
        int matched = 0;

        // Loop until the scanner reading the whole file
        while (fileScanner.hasNext()) {
            // Read the whole line of the file
            line = fileScanner.nextLine();

            String[] product = line.split(",");

            Double price = Double.valueOf(product[2]);
            String category = product[3];

            // if the search category matches the category and the price range => display the product(s)
            if (category.equals(searchCategory) && minimum < price && price < maximum) {
                System.out.println(line);
                matched += 1;
            }
        }
        // display message to notify user
        if (matched == 0)
            System.out.println("No matched category. Please try again!");
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
            String product = productScanner.nextLine();
            String[] productInfo = product.split(",");
            String productId = productInfo[0];
            String productName = productInfo[1];
            double productPrice = Double.parseDouble(productInfo[2]);
            String productCategory = productInfo[3];

            Product sortedProduct = new Product(productId, productName, productPrice, productCategory);
            productsList.add(sortedProduct);

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
        }

        for (Product product : productsList) {
            System.out.println("-----------------");
            System.out.println("ID: " + product.getId());
            System.out.println("Title: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Category: " + product.getCategory());
            System.out.println("-----------------");
        }
    }

    // generating the order ID while still maintaining the format
    public static String newOrderId() throws IOException {
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));
        // Initialize last order
        String lastOrder = "";
        // Loop through orders.txt file
        if (!sc.hasNextLine()) {
            return "O001";
        } else {
            while (sc.hasNextLine()) {
                String order = sc.nextLine();
                // Check if the current line is the last line
                if (!sc.hasNextLine()) {
                    lastOrder = order;
                }
            }
            // Get order ID
            String orderID = lastOrder.split(",")[0];
            // Get number of order ID
            int idNumber = Integer.parseInt(orderID.substring(5, 8));
            // Return ID
            return String.format("O%03d", idNumber + 1);
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

        }

    // Create order
    public void createOrder() throws IOException {
        // initialize the needed variables
        Scanner customerInput = new Scanner(System.in);
        boolean continueToBuy = true;
        int quantity;
        String itemName;
        double total = 0, totalAfterDiscount = 0;
        HashMap<String, Integer> orderProducts = new HashMap<>();

        // loop when customer still want to buy more products
        while (continueToBuy) {
            // display all the products
            System.out.println("PRODUCT");
            System.out.println("--------------");
            viewProducts();
            System.out.println("--------------");
            System.out.println("Enter the name of the item you want to buy:");
            itemName = customerInput.nextLine();

            String chosenProductName = null;
            double chosenItemPrice = 0;
            boolean productMatched = false;

            while (!productMatched) {
                File productFile = new File("./src/File/items.txt");
                Scanner productFileScanner = new Scanner(productFile);

                while (productFileScanner.hasNextLine()) {
                    String product = productFileScanner.nextLine();
                    String productName = product.split(",")[1];
                    double productPrice = Double.parseDouble(product.split(",")[2]);

                    // when the name that the user entered equals to the name of the product, take that product's name and price
                    if (itemName.equals(productName)) {
                        chosenItemPrice = productPrice;
                        chosenProductName = productName;
                        productMatched = true;
                    }
                }
                // notice the user when the input product name was not found
                if (!productMatched) {
                    System.out.println("Can't find the product. Please enter another name.");
                }
            }

            quantity = InputValidator.getIntInput("How many do you want to buy?\n",
                    "Only enter int number!");


            // update hash map with the product and its quantity.
            // Use if-else condition in case customers want to add the product that is already in their cart

            if (orderProducts.containsKey(chosenProductName)) {
                orderProducts.put(chosenProductName, quantity + orderProducts.get(chosenProductName));
            } else {
                orderProducts.put(chosenProductName, quantity);
            }

            total = total + (chosenItemPrice * quantity);

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

        // generate orderID
        String orderId = newOrderId();

        // order date
        String orderDate = LocalDate.now().toString();


        // get the list of all products in the order and their quantities
        Object[] productsList = orderProducts.keySet().toArray();
        Object[] quantityList = orderProducts.values().toArray();

        // generate strings to store the list of products and their quantities
        StringBuilder currentOrderProducts = new StringBuilder(productsList[0].toString());
        StringBuilder currentOrderProductQuantity = new StringBuilder(quantityList[0].toString());
        for (int i = 1; i < orderProducts.size(); i++) {
            currentOrderProducts.append(":").append(productsList[i].toString());
        }
        for (int i = 1; i < orderProducts.size(); i++) {
            currentOrderProductQuantity.append(":").append(quantityList[i].toString());
        }

        // apply membership discount
        totalAfterDiscount = applyDiscount(total);

        // append the line for the new order to the end of the orders.txt file
        String newOrder = String.join(",", orderId,this.getID(),this.getUsername(),orderDate,shippingAddress,
                currentOrderProducts.toString(), currentOrderProductQuantity.toString(), String.valueOf(totalAfterDiscount), "delivered");
        PrintWriter pw = new PrintWriter(new FileWriter("./src/File/orders.txt",true));
        pw.println(newOrder);

        pw.flush();
        pw.close();

        // display order details
        System.out.println("\nYour order detail");
        System.out.println("-----------------");
        System.out.println("OrderID: " + orderId);
        System.out.println("Order date: "+ orderDate);
        System.out.println("Customer name:"+ this.getUsername());
        System.out.println("Shipping address: "+ shippingAddress);
        System.out.println("Items: "+ currentOrderProducts );
        System.out.println("Quantity: "+ currentOrderProductQuantity);
        System.out.println("Total: " + totalAfterDiscount);

        // update membership by adding the order total to total spending

    }



    public static void findOrderDetails() throws IOException {
        boolean notMatched = true;

        // notify the user about wrong input when necessary
        int count = 0;

        while (notMatched) {
            Scanner userInput = new Scanner(System.in);
            if (count == 0)
                System.out.println("Please enter the orderID:");
            else
                System.out.println("Can't find the order ID. Please enter another order ID");
            String inputOrderID = userInput.nextLine();

            Scanner orderFileScanner = new Scanner(new File("./src/File/orders.txt"));
            while (orderFileScanner.hasNext()) {
                String orderLine = orderFileScanner.nextLine();
                String[] orderInfo = orderLine.split(",");
                String orderID = orderInfo[0];
                if (inputOrderID.equals(orderID)) {
                    Order.displayOrderInfo(orderLine);
                    notMatched = false;
                }
            }
            count++;
        }
    }

    public static void displayPreviousOrders() throws IOException {
        boolean notMatched = true;

        // notify the user about wrong input when necessary
        int count = 0;

        while (notMatched) {
            Scanner userInput = new Scanner(System.in);
            if (count == 0)
                System.out.println("Please enter your username:");
            else
                System.out.println("Can't find the username. Please enter another username");
            String inputCustomerName = userInput.nextLine();

            Scanner orderFileScanner = new Scanner(new File("src/orders.txt"));
            while (orderFileScanner.hasNext()) {
                String orderLine = orderFileScanner.nextLine();
                String[] orderInfo = orderLine.split(",");
                String customerName = orderInfo[3];
                if (inputCustomerName.equals(customerName)) {
                    Order.displayOrderInfo(orderLine);
                    notMatched = false;
                }
            }
            count++;
        }
    }

    public static void orderHash() {
        HashMap<String, Integer> total = new HashMap<String, Integer>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("what product buy");
            String product = sc.next();
            System.out.println("quantity");
            int quantity = sc.nextInt();
            if (total.get(product) == null) {
                total.put(product, quantity);
            } else {
                total.put(product, total.get(product) + quantity);
            }
            System.out.println("continue");
            boolean option = sc.nextBoolean();
            if (option == true) {
                continue;
            } else {
                break;
            }
        }
        System.out.println(total);
    }

    public void order2() throws IOException {
        System.out.println("Creating order");
        HashMap<String, Integer> total = buyProduct();
        double price = 0;
        Product product;
        Scanner file = new Scanner(new File("./src/File/items.txt"));
        while (file.hasNextLine()) {
            String line = file.nextLine();
            product = Product.generateProduct(line);
            if (total.get(product.getName()) != null) {
                price += total.get(product.getName()) * product.getPrice();
            }

        }
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your address");
        String address = sc.next();
        String list = "";
        int index = 0;
        for (String item: total.keySet()) {
            if (index == 0) {
                list += item;
            } else {
                list += ":" + item;
            }
            index++;
        }
        String listQuan = "";
        int indexQuan = 0;
        for (int item: total.values()) {
            if (indexQuan == 0) {
                listQuan += item;
            } else {
                listQuan += ":" + item;
            }
            indexQuan++;
        }
        Order order = new Order(newOrderId(), getID(), LocalDate.now().toString(), address, list, listQuan, price, "delivered");
        order.getOrderProduct();
    }

    public HashMap<String, Integer> buyProduct() {
        HashMap<String, Integer> total = new HashMap<String, Integer>();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("what product buy");
            String product = sc.next();
            System.out.println("quantity");
            int quantity = sc.nextInt();
            if (total.get(product) == null) {
                total.put(product, quantity);
            } else {
                total.put(product, total.get(product) + quantity);
            }
            System.out.println("continue");
            boolean option = sc.nextBoolean();
            if (option == true) {
                continue;
            } else {
                break;
            }
        }
        return total;
    }

    public static void main(String[] args) throws IOException {
        orderHash();
    }
}