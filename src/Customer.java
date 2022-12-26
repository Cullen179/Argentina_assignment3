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
    private static HashMap<String, Integer> productBought = new HashMap<String, Integer>();

    public Customer(String ID, String name, String email, String address, String phoneNumb, String membership, String username, String password) {
        this.ID = ID;
        this.name = name;
        this.email = email;
        this.address = address;
        this.phoneNumb = phoneNumb;
        this.membership = membership;
        this.username = username;
        this.password = password;
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
        return new Customer(ID, name, email, address, phoneNumb, membership, username, password);
    }

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
        pw.println(count + "," + name + "," + email + "," + address + "," + phoneNumb + "," + "none" + "," + username + "," + password);
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
    public void login (String inputUsername, String inputPassword) throws IOException {
        Boolean loggedin = false;
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNextLine()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (inputUsername.equals(customer.getUsername()) && inputPassword.equals(customer.getPassword())) {
                loggedin = true;
                System.out.println("successful login");
                break;
            }
        }
        if (!loggedin) {
            System.out.println("Incorrect username or password");
        }
        fileScanner.close();
    }
    public void displayAccountInfo () throws IOException {
        String fileName = "./src/File/customers.txt";
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);

            if (this.getUsername().equals(customer.getUsername())) {
                System.out.println("ID:" + customer.getID());
                System.out.println("Name:" + customer.getName());
                System.out.println("Email:" + customer.getEmail());
                System.out.println("Adress:" + customer.getAddress());
                System.out.println("Phone number:" + customer.getPhoneNumb());
                System.out.println("Membership:" + customer.getMembership());
                System.out.println("Username:" + customer.getUsername());

                break;
            }
        }
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
        String  newData, newName, newEmail, newAdress, newPhoneNumb;
        String fileName = "./src/File/customers.txt";
        Scanner scanner = new Scanner(System.in);
        Scanner fileScanner = new Scanner(new File(fileName));
        while (fileScanner.hasNext()) {
            String line = fileScanner.nextLine();
            Customer customer = generateCus(line);
            if (this.getUsername().equals(customer.getUsername())) {
                System.out.println("Do you want to update your name?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askName = scanner.nextLine();
                if (askName.equals("1")) {
                    System.out.println("Enter new name");
                    name = scanner.nextLine();
                }
                System.out.println("Do you want to update your email?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askEmail = scanner.nextLine();
                if (askEmail.equals("1")) {
                    System.out.println("Enter new email");
                    email = scanner.nextLine();
                }
                System.out.println("Do you want to update your address?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askAdress = scanner.nextLine();
                if (askAdress.equals("1")) {
                    System.out.println("Enter new address");
                    address = scanner.nextLine();
                }
                System.out.println("Do you want to update your phone number?");
                System.out.println("(1) Yes");
                System.out.println("(2) No");
                String askPhoneNumb = scanner.nextLine();
                if (askPhoneNumb.equals("1")) {
                    System.out.println("Enter new phone number");
                    phoneNumb = scanner.nextLine();
                }
            }
        }
    }

    public static void viewProduct() throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/items.txt"));
        System.out.println("VIEW PRODUCT");
        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            Product.productDetails(items);
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
                Product.productDetails(items);
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
    public static void createOrder() throws IOException {
        // initialize the needed variables
        Scanner customerInput = new Scanner(System.in);
        boolean continueToBuy = true;
        int quantity, itemNum;
        double chosenItemPrice = 0, total = 0, totalAfterDiscount = 0;
        HashMap<String, ArrayList<Integer>> orderProducts = new HashMap<>();
        String chosenProductName = null;

        // loop when customer still want to buy more products
        while (continueToBuy) {
            // display all the products
            System.out.println("PRODUCT");
            System.out.println("--------------");
            viewProducts();
            System.out.println("--------------");
            System.out.println("Enter the number of the item you want to buy:");
            itemNum = customerInput.nextInt();

            File productFile = new File("./src/File/items.txt");
            Scanner productFileScanner = new Scanner(productFile);

            while (productFileScanner.hasNextLine()) {
                String product = productFileScanner.nextLine();
                String productId = product.split(",")[0];
                int idNumber = Integer.parseInt(productId.substring(1, 4));
                String productName = product.split(",")[1];
                double productPrice = Double.parseDouble(product.split(",")[2]);

                // when the item number that the user enters equal to the ID number of the product, use that product name and price
                if (itemNum == idNumber) {
                    chosenItemPrice = productPrice;
                    chosenProductName = productName;
                    break;
                }
            }

            System.out.println("How many do you want to buy?");
            quantity = customerInput.nextInt();

            // update hash map with the product and its quantity.
            // Use array list in case customers want to add the product that is already in their cart

            if (orderProducts.containsKey(chosenProductName)) {
                ArrayList<Integer> quantityArrayList;
                quantityArrayList = orderProducts.get(chosenProductName);
                quantityArrayList.add(quantity);
                orderProducts.put(chosenProductName, quantityArrayList);
            } else {
                ArrayList<Integer> quantityArrayList = new ArrayList<Integer>();
                quantityArrayList.add(quantity);
                orderProducts.put(chosenProductName,quantityArrayList);
            }

            total = total + (chosenItemPrice * quantity);

            System.out.println("Do you want to continue to buy? (Y or N)");
            String isContinue = customerInput.next();

            // prevent the case customer enters something apart from Y or N.
            if (isContinue.equalsIgnoreCase("Y"))
                System.out.println("-----------------");
            else if (isContinue.equalsIgnoreCase("N")) {
                System.out.println("-----------------");
                continueToBuy = false;
            }  else
                System.out.println("Wrong input! Y or N only! Please try again");
        }


        String customerId = "", username ="";

        boolean usernameNotFound = true;

        int count = 0;

        while (usernameNotFound) {
            // find the customer ID by asking the customer(s) their username
            if (count == 0)
                System.out.println("Your username is: ");
            else
                System.out.println("Invalid username. Please enter another username: ");

            username = customerInput.next();

            File customerFile = new File("./src/File/customers.txt");
            Scanner customerFileScanner = new Scanner(customerFile);

            while (customerFileScanner.hasNext()) {
                String customerLine = customerFileScanner.nextLine();
                String[] customerInfo = customerLine.split(",");
                if (customerInfo[6].equals(username)) {
                    customerId = customerInfo[0];
                    System.out.printf("Thank you %s!\n", username);
                    String membership = customerInfo[5];

                    // apply membership
                    if(membership.equals("silver"))
                        totalAfterDiscount = total * 95/100;
                    else if (membership.equals("gold"))
                        totalAfterDiscount = total * 90/100;
                    else if (membership.equals("platinum"))
                        totalAfterDiscount = total * 85/100;
                    else
                        totalAfterDiscount = total;

                    usernameNotFound = false;
                }
            }
            count ++;
        }


        // let the customer enter and confirm the shipping address
        System.out.println("Your shipping address is:");
        String address = customerInput.next();

        System.out.println("Please re-check your address. Is it correct?(Y or N) :");
        String addressConfirm = customerInput.next();
        while (true) {
            if (addressConfirm.equalsIgnoreCase("Y")) {
                System.out.println("-----------------");
                System.out.println("Thank you! You have successfully placed your order");
                break;
            }

            if (addressConfirm.equalsIgnoreCase("N")) {
                System.out.println("Please re-enter your address!");
                System.out.println("Your shipping address is:");
                address = customerInput.next();
            } else {
                System.out.println("Only enter Y or N");
            }
            System.out.println("Please re-check your address. Is it correct? (Y or N) :");
            addressConfirm = customerInput.next();
        }

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

        // append the line for the new order to the end of the orders.txt file
        String newOrder = String.join(",", orderId,customerId,username,orderDate,address,
                currentOrderProducts.toString(), currentOrderProductQuantity.toString(), String.valueOf(totalAfterDiscount), "delivered");
        PrintWriter pw = new PrintWriter(new FileWriter("./src/File/orders.txt",true));
        pw.println(newOrder);

        pw.flush();
        pw.close();

        // display order details
        System.out.println("Your order detail");
        System.out.println("-----------------");
        System.out.println("OrderID: " + orderId);
        System.out.println("Order date: "+ orderDate);
        System.out.println("Customer name:"+ username);
        System.out.println("Shipping address: "+ address);
        System.out.println("Items: "+ currentOrderProducts );
        System.out.println("Quantity: "+ currentOrderProductQuantity);
        System.out.println("Total: " + total);

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