import java.io.IOException;

public class Order {
    public static void displayOrderInfo(String orderLine) throws IOException {
            System.out.println("-----------------");
            System.out.println("Order detail");
            System.out.println("-----------------");
            System.out.println("OrderID: " + orderLine.split(",")[0]);
            System.out.println("Order date: " + orderLine.split(",")[1]);
            System.out.println("Customer name:" + orderLine.split(",")[3]);
            System.out.println("Shipping address: " + orderLine.split(",")[7]);
            System.out.println("Items: " + orderLine.split(",")[4]);
            System.out.println("Quantity: " + orderLine.split(",")[5]);
            System.out.println("Total: " + orderLine.split(",")[6]);
        }
    }
