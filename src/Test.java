import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
//        int a  = InputValidator.getIntInput("test", "wrong");
        File itemFile = new File("./src/File/items.txt");

        PrintWriter pw = new PrintWriter(new FileWriter(itemFile, false));
        pw.printf("hello");
        pw.close();
    }

}
