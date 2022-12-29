import java.io.IOException;
import java.util.HashMap;

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

    public static Order generateOrderDetail(String productInfo) {

        // getting all the details from the product line
        String[] orderAttrs = productInfo.split(",");
        String orderId = orderAttrs[0];
        String customerId = orderAttrs[1];
        String date = orderAttrs[2];
        String address = orderAttrs[3];
        String productName = orderAttrs[4];
        String quantity = orderAttrs[5];
        double orderTotal = Double.parseDouble(orderAttrs[6]);
        String orderStatus = orderAttrs[7];
        return new Order(orderId, customerId, date,address, productName, quantity, orderTotal, orderStatus);
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
    }
