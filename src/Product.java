import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Product {
    private final String id;
    private final String name;
    private double price;
    private String category;
    private static ArrayList<String> categoryList = new ArrayList<>();

    public Product(String id, String name, double price, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public void getProductDetails() {
        System.out.println("ID: " + this.getId());
        System.out.println("Title: " + this.getName());
        System.out.println("Price: " + this.getPrice());
        System.out.println("Category: " + this.getCategory());
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
        int idNumber = Integer.parseInt(itemID.split("-")[0].replace("I", ""));

        // Return ID
        return String.format("I%03d-%d", idNumber + 1, year);
    }

    public static Product createProduct() throws IOException{
        String name = checkNewProductName();
        // Validate price input
        double price = InputValidator.getDoubleInput("What is the price of the product:", "Price must be of integer or double value");
        String category = checkCategory();
        int year = InputValidator.getIntInput("What is the year of the product:", "Year must be of integer value");
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

    public static String generateItem(Product product) {
        return String.format("%s,%s,%.1f,%s", product.getId(), product.getName(), product.getPrice(), product.getCategory());
    }

    public static ArrayList<String> getCategoryList() throws IOException{
        // Scan items file
        Scanner sc = new Scanner(new File("./src/File/items.txt"));
        // Loop through items file
        while (sc.hasNextLine()) {
            String item = sc.nextLine();
            Product product = Product.generateProduct(item);
            // If category is not in the list, add category
            if (!categoryList.contains(product.getCategory())) {
                categoryList.add(product.getCategory());
            }
        }
        return categoryList;
    }

    public static String checkNewProductName() throws IOException {
        Scanner sc = new Scanner(System.in);
        String name;
        while (true) {
            System.out.println("What is the name of the product:");
            name = sc.next();
            // Check if product name exist
            if (checkProductExisted(name)) {
                System.out.println("Product name already exist");
                System.out.println("Please enter different name");
                // Stop if product name is valid
            } else {
                break;
            }
        }
        return name;
    }

    public static String checkCategory() throws IOException {
        String inputCategory;
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("What is the category of the product:");
            inputCategory = sc.next();
            Scanner fileScanner = new Scanner(new File ("./src/File/category.txt"));
            boolean matchCategory = false;

            // Loop through category file
            while (fileScanner.hasNextLine()) {
                String category = fileScanner.nextLine();

                // Check if input category equal category
                if (inputCategory.equals(category)) {
                    matchCategory = true;
                }
            }
            fileScanner.close();

            // Check if category is available
            if (!matchCategory) {
                System.out.println("The category does not exist");
                System.out.println("Please enter a valid category");
            } else {
                // Stop if category is valid
                break;
            }
        }
        return inputCategory;
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

    public static void addCategory(String category) throws IOException{
        getCategoryList().add(category);
    }
}
