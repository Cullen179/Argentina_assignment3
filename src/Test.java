import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> test = new HashMap<String, Integer>();
        for (int i = 0; i < 5; i++) {
            test.put("hello" + i, i);
        }
        test.put("hello1", test.get("hello1") + 3);
        System.out.println(test.get("hello1"));
        System.out.println(test);
        System.out.println(test.get("hellof"));

    }
}
