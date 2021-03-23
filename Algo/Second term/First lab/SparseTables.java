import java.util.Scanner;

import static java.lang.Integer.min;

public class SparseTables {

    private static int log(int value) {
        return value != 1 && value != 0 ? log(value / 2) + 1 : 0;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        int[] a = new int[n];
        a[0] = scanner.nextInt();
        for (int i = 1; i < n; i++) {
            a[i] = (23 * a[i - 1] + 21563) % 16714589;
        }
        int[][] sparseTable = new int[n][log(n) + 1];
        for (int j = 0; j < log(n) + 1; j++) {
            for (int i = 0; i < n; i++) {
                if (j == 0){
                    sparseTable[i][j] = a[i];
                } else {
                    sparseTable[i][j] = min(sparseTable[i][j - 1], sparseTable[(i + (1 << (j - 1))) < n ? (i + (1 << (j - 1))) : i][j - 1]);
                }
            }
        }
        int u = scanner.nextInt();
        int v = scanner.nextInt();
        int answer;
        u--;
        v--;
        if (u > v) {
            int k = log(u - v + 1);
            answer = min(sparseTable[v][k], sparseTable[u - (1 << k) + 1][k]);
        } else {
            int k = log(v - u + 1);
            answer = min(sparseTable[u][k], sparseTable[v - (1 << k) + 1][k]);
        }
        u++;
        v++;
        for (int i = 1; i < m; i++) {
            u = ((17 * u + 751 + answer + 2 * i) % n);
            v = ((13 * v + 593 + answer + 5 * i) % n);
            if (u > v) {
                int k = log(u - v + 1);
                answer = min(sparseTable[v][k], sparseTable[u - (1 << k) + 1][k]);
            } else {
                int k = log(v - u + 1);
                answer = min(sparseTable[u][k], sparseTable[v - (1 << k) + 1][k]);
            }
            u++;
            v++;
        }
        System.out.println(u + " " + v + " " + answer);
    }
}