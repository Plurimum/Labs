import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Apples {
    private static class Triple {
        int decrease;
        int increase;
        int number;

        Triple(int a, int b, int p) {
            decrease = a;
            increase = b;
            number = p;
        }
    }

    private static boolean compare(Triple a, Triple b) {
        int first = a.increase - a.decrease;
        int second = b.increase - b.decrease;
        if (first < 0) {
            if (second < 0) {
                return a.increase > b.increase;
            }
            return false;
        }
        if (second < 0) {
            return true;
        }
        return a.decrease < b.decrease;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("apples.in")));
        PrintWriter writer = new PrintWriter("apples.out");
        int n = scanner.nextInt();
        int s = scanner.nextInt();
        Triple[] apples = new Triple[n];
        for (int i = 0; i < n; i++) {
            int a = scanner.nextInt();
            int b = scanner.nextInt();
            apples[i] = new Triple(a, b, i + 1);
        }
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (compare(apples[i], apples[j])) {
                    Triple tmp = apples[i];
                    apples[i] = apples[j];
                    apples[j] = tmp;
                }
            }
        }
        boolean flag = false;
        for (int i = n - 1; i >= 0; i--) {
            s -= apples[i].decrease;
            if (s <= 0) {
                flag = true;
                break;
            }
            s += apples[i].increase;
        }
        if (flag) {
            writer.write("-1");
        } else {
            for (int i = n - 1; i >= 0; i--) {
                writer.write(apples[i].number + " ");
            }
        }
        writer.close();
    }
}
