import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class Order {
    private String ID;
    private String customerID;
    private String date;
    private String address;
    private String productName;
    private String quantity;
    private double price;
    private String status;

    public Order(String ID, String customerID, String date, String address, String productName, String quantity, double price, String status) {
        this.ID = ID;
        this.customerID = customerID;
        this.date = date;
        this.address = address;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.status = status;
    }

    public static Order generateOrder(String line) {

        // getting all the details from the product line
        String[] orderAttrs = line.split(",");
        String orderId = orderAttrs[0];
        String customerId = orderAttrs[1];
        String date = orderAttrs[2];
        String address = orderAttrs[3];
        String productName = orderAttrs[4];
        String quantity = orderAttrs[5];
        double orderTotal = Double.parseDouble(orderAttrs[6]);
        String orderStatus = orderAttrs[7];
        return new Order(orderId, customerId, date, address, productName, quantity, orderTotal, orderStatus);
    }


    public String getOrderID() {
        return ID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getOrderDate() {
        return date;
    }

    public String getOrderAddress() {
        return address;
    }

    public HashMap<String, Integer> getProductList() {
        String[] productNameList = this.productName.split(":");
        String[] quantityList = this.quantity.split(":");
        HashMap<String, Integer> productList = new HashMap<String, Integer>();

        // Loop through name and quantity list
        for (int i = 0; i < productNameList.length; i++) {
            productList.put(productNameList[i], Integer.parseInt(quantityList[i]));
        }
        return productList;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void displayOrderInfo() throws IOException {
        System.out.println("-----------------");
        System.out.println("Order detail");
        System.out.println("-----------------");
        System.out.println("OrderID: " + this.getOrderID());
        System.out.println("Order date: " + this.getOrderDate());
        System.out.println("Customer ID:" + this.getCustomerID());
        System.out.println("Shipping address: " + this.getOrderAddress());
        System.out.println("Product list: " + this.getProductList());
        System.out.println("Total: " + this.getPrice());
    }

    public String generateOrderLine() throws IOException {
        return String.format("%s,%s,%s,%s,%s,%s,%.1f,%s", ID, customerID, date, address, productName, quantity, price, status);
    }

        // generating the order ID while still maintaining the format
    public static String newOrderId() throws IOException {
        Scanner sc = new Scanner(new File("./src/File/orders.txt"));
        // Initialize last order
        String lastOrder = "";
        // Loop through orders.txt file
        if (!sc.hasNextLine()) {
            return "O001";
        } else {
            while (sc.hasNextLine()) {
                String order = sc.nextLine();
                // Check if the current line is the last line
                if (!sc.hasNextLine()) {
                    lastOrder = order;
                }
            }
            // Get order ID
            String orderID = lastOrder.split(",")[0];
            // Get number of order ID
            int idNumber = Integer.parseInt(orderID.substring(1, 4));
            // Return ID
            return String.format("O%03d", idNumber + 1);
        }
    }
}
