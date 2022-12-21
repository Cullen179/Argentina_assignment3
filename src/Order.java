import java.io.IOException;

public class Order {
    public static void displayOrderInfo(String productInfo) {
        String[] orderInfo = productInfo.split(",");
        String orderId = orderInfo[0];
        String customerId = orderInfo[1];
        String customerName = orderInfo[2];
        String orderDate = orderInfo[3];
        String orderAdd = orderInfo[4];
        String orderItem = orderInfo[5];
        String NumberOfItems = orderInfo[6];
        String orderTotal = orderInfo[7];
        String orderStatus = orderInfo[8];

        // getting each product name and number from the order
        String[] orderProductList = orderItem.split(":");
        String [] orderProductNumList = NumberOfItems.split(":");

        System.out.println("Order Id: " + orderId);
        System.out.println("Customer Id: "  + customerId);
        System.out.println("Customer Name: "  + customerName);
        System.out.println("Order date: " + orderDate);
        System.out.println("Address: " + orderAdd);
        System.out.println("Order products: ");

        for (int i = 0; i < orderProductList.length; i++) {
            System.out.println("\t" + orderProductList[i] + " - " + orderProductNumList[i]);
        }

        System.out.println("Order total: " + orderTotal + " mil VND");
        System.out.println("Order status: " + orderStatus + "\n");
    }

}
