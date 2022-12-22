import java.io.*;
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
        int line = 0;
        String id = "I001-2001";
        boolean matchedLine1 = false;

        // Loop through items file
        while (fileScanner.hasNextLine()) {
            String item = fileScanner.nextLine();
            line++;
            Product product = Product.generateProduct(item);

            // If product id equal remove item id, print writer skip that item
            if (product.getId().equals(id) && line == 1) {
                matchedLine1 = true;
                continue;
            } else if (product.getId().equals(id)) {
                continue;
            }

            // Trim the last item of the file to avoid adding new line
            if (line == 1 || (line == 2 && matchedLine1)) {
                pw.printf(item);
            } else {
                pw.printf("\n" + item);
            }
        }

        fileScanner.close();
        pw.close();
        itemFile.delete();
        removeItem.renameTo(itemFile);
    }
    public static void addCategory() {

    }

    public static void removeCategory() {

    }


    public static void getOrderByCustomerID() {

    }
    public static void changeStatus() {

    }

}
