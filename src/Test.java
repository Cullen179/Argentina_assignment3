import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
//        Scanner sc = new Scanner(new File("./src/File/items.txt"));
//
//        // Initialize lastItem
//        // Loop through items file
//        while (sc.hasNextLine()) {
//            String item = sc.nextLine();
//            // Check if the current line is the last line
//            if (sc.nextLine().isEmpty()) {
//                System.out.println("end");
//            }
//            System.out.println(item);
//        }
//    }
        ArrayList<Integer> test = new ArrayList<Integer>();
        for (int i = 0; i < 5; i++) {
            test.add(i);
        }
        test.remove(2);
        System.out.println(test);
    }
}
