import java.io.*;
import java.util.*;

public class Customer {
    private String memberId;
    private String fullName;
    private String phoneNumb;
    private String address;
    private String username;
    private String password;
    private String membership;
    private static HashMap<String, Integer> productBought = new HashMap<String, Integer>();
    double totalSpent;

    public Customer(String memberId, String fullName, String phoneNumb, String address, String username, String password, String membership) {
        this.memberId = memberId;
        this.fullName = fullName;
        this.phoneNumb = phoneNumb;
        this.address = address;
        this.username = username;
        this.password = password;
        this.membership = membership;
    }

    public static Customer generateCus(String customer) {
        String[] customerInfo = customer.split(",");
        String customerID = customerInfo[0];
        String customerName = customerInfo[1];
        String phone = customerInfo[2];
        String customerAddress = customerInfo[3];
        String username = customerInfo[4];
        String customerPass = customerInfo[5];
        String membership = customerInfo[6];
        return new Customer(customerID, customerName, phone,customerAddress, username, customerPass, membership);
    }

    public HashMap<String, Integer> getProductBought() throws IOException{
        HashMap<String, Integer> productBought = new HashMap<String, Integer>();
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));
        while (sc.hasNextLine()) {
            String orderLine = sc.nextLine();
            Order order = Order.displayOrderDetail(orderLine);
            if (order.getCustomerID().equals(this.memberId)) {
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
    public static void registerCustomer() throws IOException {
        Scanner input = new Scanner(System.in);

        String username;
        System.out.println("Please type your username: ");
        while (true) {
            username = input.nextLine();

            if (!checkCustomerUsernameExisted(username)) break;
            else {
                System.out.println("This username already existed. Please try with another one.");
            }
        }

        String password;
        System.out.print("Please type your password: ");
        while (true) {
            password = input.nextLine();

            if (!checkCustomerPasswordExisted(password)) break;
            else {
                System.out.println("This password already existed. Please try with another one.");
            }
        }

        System.out.print("Please type your full name: ");
        String fullName = input.nextLine();

        String phoneNum;
        System.out.print("Please type your phone number: ");
        while (true) {
            phoneNum = input.nextLine();

            if (phoneNum.matches("^\\d{10}$")) break;
            else {
                System.out.println("Please enter the correct 10 digit phone number format. Try again.");
            }
        }

        String email;
        System.out.println("Please type your email, eg: abc@gmail.com ");
//        while (true) {
//            email = input.nextLine();
//
//            if (email.matches("[A-Z]+[a-zA-Z_]+@\b([a-zA-Z]+.){2}\b?.[a-zA-Z]+")) {
//                break;
//            } else {
//                System.out.println("Invalid email. Try again.");
//            }
//        }
        email = input.nextLine();

        System.out.print("Please type your address: ");
        String add = input.nextLine();

        String newMember = String.join(",", fullName, add, phoneNum, "Normal", username, password);

        Writer output = new BufferedWriter(new FileWriter("./src/customers.txt", true));
        output.append(System.lineSeparator() + newMember);

        System.out.println("Successfully registered.");
        output.close();

    }

    public static boolean login() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("\nLOGIN AS CUSTOMER ROLE\n");
        System.out.print("Enter your username: ");
        String usernameCus = sc.nextLine();
        System.out.print("Enter your password: ");
        String passwordCus = sc.nextLine();

        Scanner scannerCustomer = new Scanner(new File("./src/customers.txt"));

        while (scannerCustomer.hasNextLine()) {
            String currentCustomer = scannerCustomer.nextLine();
            String[] currentCustomerInfo = currentCustomer.split(",");
            String currentCustomerUsername = currentCustomerInfo[5];
            String currentCustomerPassword = currentCustomerInfo[6];

            if (usernameCus.equals(currentCustomerUsername) && passwordCus.equals(currentCustomerPassword)) {
                System.out.println("LOGIN SUCCESSFULLY");
                scannerCustomer.close();
                return true;
            }
        }

        System.out.println("Login unsuccessfully, please check your username and password and try again");
        scannerCustomer.close();

        return false;
    }

    public static boolean checkCustomerUsernameExisted(String username) throws IOException {
        Scanner scannerCustomer = new Scanner(new File("./src/customers.txt"));

        while (scannerCustomer.hasNextLine()) {
            String currentCustomer = scannerCustomer.nextLine();
            String[] currentCustomerInfo = currentCustomer.split(",");
            String currentMemberUsername = currentCustomerInfo[5];

            if (username.equals(currentMemberUsername)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkCustomerPasswordExisted(String password) throws IOException {
        Scanner scannerCustomer = new Scanner(new File("./src/customers.txt"));

        while (scannerCustomer.hasNextLine()) {
            String currentCustomer = scannerCustomer.nextLine();
            String[] currentCustomerInfo = currentCustomer.split(",");
            String currentCustomerPassword = currentCustomerInfo[6];

            if (password.equals(currentCustomerPassword)) {
                return true;
            }
        }
        return false;
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

    public static void listProductInPriceRange() throws IOException {
        System.out.println("Listing all the available products in a price range");
        Scanner scannerProduct = new Scanner(new File("./src/items.txt"));
        System.out.print("Type your category to search for the needed product: ");
        Scanner input = new Scanner(System.in);
        String categoryInput = input.nextLine();
    }

//    public static List<Product> filterProducts(BigInteger minPrice, BigInteger maxPrice){
//        List<Product> results = new ArrayList();
//        products.stream().filter(p -> p.getPrice() > minPrice && p.getPrice() < maxPrice).forEach(results::add);
//        return results;
//    }

    public static void sortProduct() throws IOException {
        System.out.println("Sorting all products by product price following an increasing or decreasing order.");
        Scanner scannerProduct = new Scanner(new File("./src/items.txt"));
        ArrayList<Product> productList = new ArrayList<>();

        while (scannerProduct.hasNextLine()) {
            String items = scannerProduct.nextLine();
            String[] itemsInfo = items.split(",");
            String itemID = itemsInfo[0];
            String itemTitle = itemsInfo[1];
            double itemPrice = Double.parseDouble(itemsInfo[2]);
            String itemCategory = itemsInfo[3];

            Product sortedProduct = new Product(itemID, itemTitle, itemPrice, itemCategory);
            productList.add(sortedProduct);
        }

        while (true) {
            System.out.print("Enter 'up' or 'down' to sort: ");
            Scanner input = new Scanner(System.in);
            String sort = input.nextLine();
            if (!(sort.equals("up") || sort.equals("down"))) {
                System.out.print("Please only enter 'up' or 'down': \n");
                continue;
            }

            productList.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    if (sort.equals("up")) {
                        return (int) (o1.getPrice() - o2.getPrice());
                    } else {
                        return (int) (o2.getPrice() - o1.getPrice());
                    }
                }
            });
            break;
        }
        for (Product product : productList
        ) {
            System.out.println("Id: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Price: " + product.getPrice());
            System.out.println("Category: " + product.getCategory());
            System.out.println("-----------------");
        }
    }
    public static void createOrder() throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("Welcome to our store");
        System.out.print("Type your address for delivering: ");
        String orderAdd = sc.nextLine();

        HashMap<String, Integer> orderItemDetail = new HashMap<>();

        boolean buyMoreItem = false;
        double total = 0;

        while (!buyMoreItem) {
            String itemName = null;
            double itemPrice = 0.0;
            boolean itemCheck = false;

            while (!itemCheck) {
                Scanner scannerProduct = new Scanner(new File("./src/items.txt"));
                System.out.print("\nPlease type the name of the item you want to buy: ");
                String nameInput = sc.nextLine();

                while (scannerProduct.hasNextLine()) {
                    String currentProduct = scannerProduct.nextLine();
                    String[] currentProductInfo = currentProduct.split(",");
                    String currentProductName = currentProductInfo[1];

                    if (currentProductName.equals(nameInput)) {
                        itemName = currentProductName;
                        itemPrice = Double.parseDouble(currentProductInfo[2]);
                        itemCheck = true;
                    }
                }
                if (!itemCheck) {
                    System.out.println("This item cannot found. Please try with another one.");
                }
            }

            int numbersOfItem = InputValidator.getIntInput(
                    "How many this items you would like to buy?", "Please type only number.");
            total += itemPrice * numbersOfItem;
            orderItemDetail.put(itemName, numbersOfItem);
        }
    }

    public static void main(String[] args) throws IOException {
        Customer lam = Customer.generateCus("C001,Minh Hoang,18 Irwin Street,0421473243,Silver,minhhoang,123456");
        System.out.println(lam.getProductBought());
    }

}