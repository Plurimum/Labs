import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FastSearchSubstring {

    private static String p, t;
    private static int[] z;
    private static int pLength;
    private static ArrayList<Integer> answer;

    private static void computeZFunction(String string) {
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

    private static int count() {
        int count = 0;
        for (int i = 1; i < p.length(); i++) {
            if (z[i] == pLength) {
                count++;
                answer.add(i - pLength);
            }
        }
        return count;
    }

    private static void init(Scanner scanner) {
        answer = new ArrayList<>();
        p = scanner.nextLine();
        pLength = p.length();
        t = scanner.nextLine();
        p = p + '$' + t;
        z = new int[p.length()];
        Arrays.fill(z, 0);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter writer = new PrintWriter(System.out);
        init(scanner);
        computeZFunction(p);
        writer.write(count() + "\n");
        for (Integer index : answer) {
            writer.write(index + " ");
        }
        writer.close();
    }
}
