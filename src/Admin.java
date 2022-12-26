import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Admin {
    public void viewProduct() throws IOException {
        // Scan items file
        Scanner sc = new Scanner(new File("./src/File/items.txt"));
        // Loop through items file
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            Product.productDetails(item);
        }
    }


    public static void viewOrders() {

    }

    public static void viewMembers() {

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



    public static void getOrderByCustomerID() {

    }
    public static void changeStatus() {

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
    public static void main(String[] args) throws IOException {
        Admin admin = new Admin();
        admin.customerHighestProduct();
    }

}
