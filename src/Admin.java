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
        admin.viewProduct();
    }

    public static void viewOrders() {

    }

    public static void viewMembers() {

    }

    public static void addProduct() {

    }


    public static void addCategory() {

    }

    public static void removeCategory() {

    }

    public static void removeProduct() {

    }

    public static void getOrderByCusID() {

    }
    public static void changeStatus() {

    }

}
