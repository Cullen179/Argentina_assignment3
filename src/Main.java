import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("1:Login");
        System.out.println("2:Register");
        String askCustomer = scanner.nextLine();
        while (true){
            if(askCustomer.equals("1")){
                System.out.println("Enter ussername:");
                String username = scanner.nextLine();
                System.out.println("Enter password");
                String password = scanner.nextLine();
                Customer newCustomer = new Customer(username,password);
                newCustomer.login(username,password);
                break;
            } else if(askCustomer.equals("2")){
                Customer customerRegister = new Customer();
                customerRegister.registerMember();
                break;
            } else {
                System.out.println("Please enter 1 or 2");
            }


        }
    }
}
