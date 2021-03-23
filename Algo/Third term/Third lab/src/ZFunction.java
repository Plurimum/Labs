import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class ZFunction {

    private static int[] z;
    private static String string;

    private static void computeZFunction() {
        int left = 0, right = 0;
        for (int i = 1; i < string.length(); i++) {
            z[i] = Math.max(0, Math.min(right - i, z[i - left]));
            while (i + z[i] < string.length() && string.charAt(z[i]) == string.charAt(i + z[i])) {
                z[i]++;
            }
            if (i + z[i] > right) {
                left = i;
                right = i + z[i];
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter writer = new PrintWriter(System.out);
        string = scanner.nextLine();
        z = new int[string.length()];
        Arrays.fill(z, 0);
        computeZFunction();
        for (int i = 1; i < z.length; i++) {
            writer.write(z[i] + " ");
        }
        scanner.close();
        writer.close();
    }
}
