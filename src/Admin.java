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
        admin.addProduct();
    }

    public static void viewOrders() {

    }

    public static void viewMembers() {

    }

    public void addProduct() throws IOException{
        PrintWriter pw = new PrintWriter(new FileWriter("src/File/items.txt", true));
        Product newProduct = Product.createProduct();
        pw.printf("\n%s,%s,%f,%s", newProduct.getId(), newProduct.getName(), newProduct.getPrice(), newProduct.getCategory());
        pw.close();
    }

    public static void removeProduct() {

    }
    public static void addCategory() {

    }

    public static void removeCategory() {

    }


    public static void getOrderByCusID() {

    }
    public static void changeStatus() {

    }

}
