import java.io.IOException;

public class Order {
    private String orderID;
    private String customerID;
    private String orderDate;
    private String orderAddress;
    private String orderProduct;
    private int productNum;
    private double price;
    private String status;

    public Order(String orderID, String customerID, String orderDate, String orderAddress, String orderProduct, int productNum, double price, String status) {
        this.orderID = orderID;
        this.customerID = customerID;
        this.orderDate = orderDate;
        this.orderAddress = orderAddress;
        this.orderProduct = orderProduct;
        this.productNum = productNum;
        this.price = price;
        this.status = status;
    }

    public static Order displayOrderDetail(String productInfo) {

        // getting all the details from the product line
        String[] orderAttrs = productInfo.split(",");
        String orderId = orderAttrs[0];
        String customerId = orderAttrs[1];
        String orderDate = orderAttrs[2];
        String orderAddress = orderAttrs[3];
        String orderProducts = orderAttrs[4];
        int orderProductsNum = Integer.parseInt(orderAttrs[5]);
        double orderTotal = Double.parseDouble(orderAttrs[6]);
        String orderStatus = orderAttrs[7];
        return new Order(orderId, customerId, orderDate, orderAddress, orderProducts, orderProductsNum, orderTotal, orderStatus);
    }

    public String getOrderID() {
        return orderID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public String getOrderAddress() {
        return orderAddress;
    }

    public String getOrderProduct() {
        return orderProduct;
    }

    public int getProductNum() {
        return productNum;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }
}
