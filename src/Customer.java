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
        File file = new File("src/products.txt");
        Scanner fileScanner = new Scanner(file);
        String line;

        while (fileScanner.hasNext()) {
            // Read the whole line of the file
            line = fileScanner.nextLine();
            System.out.println(line);
        }
    }

    public static void searchProductByCategory() throws IOException {
            // Open and read each line of the Product file
            File file = new File("src/products.txt");
            Scanner fileScanner = new Scanner(file);
            String line;

            // A scanner to take user input (category)
            Scanner sc = new Scanner(System.in);
            String searchCategory;
            System.out.println("Enter what you want to search: ");
            searchCategory = sc.nextLine();
            System.out.println("--------------");

            // variable to count whether there is any matches
            int matched = 0;

            // Loop until the scanner reading the whole file
            while (fileScanner.hasNext()) {

                // Read the whole line of the file
                line = fileScanner.nextLine();

                String[] product = line.split(",");

                String category = product[1];

                // if the search category matches the category => display the product(s)
                if (category.equals(searchCategory)) {
                    System.out.println(line);
                    matched += 1;
                }
            }
            // display message to notify user
            if (matched == 0)
                System.out.println("No matched category. Please try again!");
    }

    public static void searchByPriceRange() throws IOException {
        // Open and read each line of the Product file
        File file = new File("src/products.txt");
        Scanner fileScanner = new Scanner(file);
        String line;

        // A scanner to take user input (category)
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the minimum price: ");
        double minimum = sc.nextDouble();
        System.out.println("Enter the maximum price: ");
        double maximum = sc.nextDouble();

        System.out.println("--------------");

        // variable to count whether there is any matches
        int matched = 0;

        // Loop until the scanner reading the whole file
        while (fileScanner.hasNext()) {

            // Read the whole line of the file
            line = fileScanner.nextLine();

            String[] product = line.split(",");

            Double price = Double.valueOf(product[2]);

            // if the search category matches the category => display the product(s)
            if (minimum < price && price < maximum) {
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


