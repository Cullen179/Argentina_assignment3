import java.io.*;
import java.util.*;

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
            fileScanner.nextLine();
        }

        // variable to count whether there is any matches
        int matched = 0;

        // Loop until the scanner reading the whole file
        while (fileScanner.hasNext()) {
            // Read the whole line of the file
            line = fileScanner.nextLine();

            String[] product = line.split(",");

            String category = product[1];
            Double price = Double.valueOf(product[2]);

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

    public static void sortProduct() {

    }
    public static void createOrder() {
    }
}


