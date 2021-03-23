import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class PrefixFunction {

    private static ArrayList<Integer> prefix;
    private static String string;

    private static void prefixFunction() {
        for (int i = 1; i < string.length(); i++) {
            int k = prefix.get(i - 1);
            while (k > 0 && string.charAt(i) != string.charAt(k)) {
                k = prefix.get(k - 1);
            }
            if (string.charAt(i) == string.charAt(k)) {
                k++;
            }
            prefix.set(i, k);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        string = scanner.nextLine();
        prefix = new ArrayList<>();
        for (int i = 0; i < string.length(); i++) {
            prefix.add(0);
        }
        prefixFunction();
        for (int i = 0; i < string.length(); i++) {
            writer.write(prefix.get(i) + " ");
        }
        writer.close();
    }
}
