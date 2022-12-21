import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Product {
    private final String id;
    private final String name;
    private double price;
    private String category;

    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public static void productDetails(String productInfo) {
        String[] itemsInfo = productInfo.split(",");
        String itemID = itemsInfo[0];
        String itemName = itemsInfo[1];
        String itemPrice = itemsInfo[2];
        String itemCategory = itemsInfo[3];

        System.out.println("ID: " + itemID);
        System.out.println("Title: " + itemName);
        System.out.println("Price: " + itemPrice);
        System.out.println("Category: " + itemCategory);
        System.out.println("-----------------");
    }

    public static boolean checkProductExisted(String name) throws IOException {
        Scanner scannerProduct = new Scanner(new File("./src/File/items.txt"));

        while (scannerProduct.hasNextLine()) {
            String currentProduct = scannerProduct.nextLine();
            String[] currentProductInfo = currentProduct.split(",");
            String currentProductName = currentProductInfo[1];

            if (name.equals(currentProductName)) {
                return true;
            }
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
