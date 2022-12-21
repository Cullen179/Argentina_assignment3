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

    public static void productDetails(String item) {
        Product product = Product.generateProduct(item);
        System.out.println("ID: " + product.getId());
        System.out.println("Title: " + product.getName());
        System.out.println("Price: " + product.getPrice());
        System.out.println("Category: " + product.getCategory());
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

    public static String newIDNumber(int year) throws IOException{
        Scanner sc = new Scanner(new File("./src/File/items.txt"));
        // Initialize lastItem
        String lastItem = "";
        // Loop through items file
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            // Check if the current line is the last line
            if (!sc.hasNextLine()) {
                lastItem = item;
            }
        }
        // Get item ID
        String itemID = lastItem.split(",")[0];
        // Get number of item ID
        int idNumber = Integer.parseInt(itemID.substring(1, 4));
        // Return ID
        return String.format("I%03d-%d", idNumber + 1, year);
    }

    public static Product createProduct() throws IOException{
        Scanner sc = new Scanner(System.in);
        System.out.println("What is the name of the product:");
        String name = sc.next();
        System.out.println("What is the price of the product:");
        double price = sc.nextDouble();
        System.out.println("What is the category of the product:");
        String category = sc.next();
        System.out.println("What is the year of the product:");
        int year = sc.nextInt();
        return new Product(newIDNumber(year), name, price, category);
    }

    // Generate product from item line
    public static Product generateProduct(String item) {
        String[] productInfo = item.split(",");
        String productID = productInfo[0];
        String productName = productInfo[1];
        double productPrice = Double.parseDouble(productInfo[2]);
        String productCategory = productInfo[3];
        return new Product(productID, productName, productPrice, productCategory);
    }

    public String getId() { return id;
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
