import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Customer {
    public static void registerCustomer() {
    }

    public static boolean login() {

    }

    public static boolean checkCustomerUsernameExisted(String username) {

    }

    public static boolean checkCustomerPasswordExisted(String password) {

    }


    public static void viewProducts() throws IOException {
        File file = new File("./src/File/products.txt");
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
        File file = new File("./src/File/products.txt");
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

        File file = new File("./src/File/products.txt");
        Scanner productScanner = new Scanner(file);

        // initialise an array list storing the products
        List<Product> productsList = new ArrayList<>();

        // Reading products.txt file and create new Product to put in Array List productsList
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

            File productFile = new File("./src/File/products.txt");
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

}
