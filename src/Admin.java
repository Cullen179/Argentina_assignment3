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

    public static void main(String[] args) throws IOException {
        Admin admin = new Admin();
        admin.removeProduct();
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
        // Add new file
        File removeItem = new File("./src/File/removeItems.txt");
        File itemFile = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(itemFile);
        PrintWriter pw = new PrintWriter(new FileWriter(removeItem, true));
        // Initiate line number
        int line = 0;
        String id = "I007-2003";
        // Boolean variable if remove item match line 1
        boolean matchedLine1 = false;

        // Loop through items file
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
        itemFile.delete();
        // Rename remove item file to item file
        removeItem.renameTo(itemFile);
    }
    public void updateProduct() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the product you want to update");

        // Add new file
        File updatePrice = new File("./src/File/update_items.txt");
        File itemFile = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(itemFile);
        PrintWriter pw = new PrintWriter(new FileWriter(updatePrice, true));
        // Initiate line number
        int line = 0;
        String id = "I007-2003";

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            if (product.getId().equals(id)) {
//                product.setPrice();
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
        ArrayList<String> categoryList = Product.getCategoryList();
        // Check if remove category is in category list
        if (categoryList.contains(removeCategory)) {
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).equals(removeCategory)) {
                    categoryList.remove(i);
                }
            }
        } else {
            System.out.println("Remove category is not in the list. Do you want to retry ?");
            boolean retry = sc.nextBoolean();
            // Call removeCategory method if admin want to retry
            if (retry) {
                removeCategory();
            }
        }
    }

    public void removeItemCategory(String category) throws IOException {
        File updateCategory = new File("./src/File/updateItems.txt");
        File itemFile = new File("./src/File/items.txt");
        Scanner fileScanner = new Scanner(itemFile);
        PrintWriter pw = new PrintWriter(new FileWriter(updateCategory, true));
        // Initiate line number
        int line = 0;

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            // Check if product category equal remove category
            if (product.getCategory().equals(category)) {
                product.setCategory("None");


                // if line number is 1 , avoid adding new line
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
            updateCategory.renameTo(itemFile);
        }
    }

    public void removeCustomer() throws IOException{
        // Add new file
        File removeCus = new File("./src/File/removeCustomers.txt");
        File customerFile = new File("./src/File/customers.txt");
        Scanner fileScanner = new Scanner(customerFile);
        PrintWriter pw = new PrintWriter(new FileWriter(removeCus, true));
        // Initiate line number
        int line = 0;
        String id = "C007";
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

}
