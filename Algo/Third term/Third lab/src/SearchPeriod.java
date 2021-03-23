import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Scanner;

public class SearchPeriod {

    private static String string;
    private static int[] z;

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
        string = scanner.nextLine();
        z = new int[string.length()];
        Arrays.fill(z, 0);
        computeZFunction();
        int answer = -1;
        for (int i = 0; i < string.length(); i++) {
            if (i + z[i] == string.length() && string.length() % i == 0) {
                answer = i;
                break;
            }
        }
        if (answer == -1) {
            answer = string.length();
        }
        System.out.println(answer);
    }
}
