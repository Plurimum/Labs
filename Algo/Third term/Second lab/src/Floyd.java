import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Floyd {

    private static int[][] weights;
    private static int n;

    private static void init(Scanner scanner) {
        n = scanner.nextInt();
        weights = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                weights[i][j] = scanner.nextInt();
            }
        }
    }

    private static void floyd() {
        for (int i = 0; i < n; i++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < n; v++) {
                    weights[u][v] = Math.min(weights[u][v], weights[u][i] + weights[i][v]);
                }
            }
        }
    }

    private static void printAns() {
        for (int[] i : weights) {
            for (long j : i) {
                System.out.print(j + " ");
            }
            System.out.print("\n");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        floyd();
        printAns();
    }
}
