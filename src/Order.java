import java.io.IOException;

public class Order {
    public static void orderDetail(String productInfo) {
        String[] orderInfo = productInfo.split(",");
        String orderId = orderInfo[0];
        String customerId = orderInfo[1];
        String customerName = orderInfo[2];
        String customerPhone = orderInfo[3];
        String orderDate = orderInfo[4];
        String orderItem = orderInfo[5];
        String orderAdd = orderInfo[6];
        String numberOfItems = orderInfo[7];
        String orderTotal = orderInfo[8];
        String orderStatus = orderInfo[9];

        // Getting each product name and number from the order.
        String[] orderProductList = orderItem.split(":");
        String [] orderProductNumList = numberOfItems.split(":");
        // Format and print out the information of the order.
        System.out.println("-----------------");
        System.out.println("ORDER ID: " + orderId);
        System.out.println("Customer ID: "  + customerId);
        System.out.println("Customer Name: "  + customerName);
        System.out.println("Customer Phone: " + customerPhone);
        System.out.println("Order date: " + orderDate);
        System.out.println("Delivery Address: " + orderAdd);
        System.out.println("Order products: ");

        for (int i = 0; i < orderProductList.length; i++) {
            System.out.println(orderProductList[i] + " | " + orderProductNumList[i]);
        }
        System.out.println("Order total: " + orderTotal + " million VND.");
        System.out.println("Order status: " + orderStatus);
    }

}
