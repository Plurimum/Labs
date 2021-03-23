import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Dominoes {

    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int[][] board;
    private static int[] p;
    private static int n, m, a, b;

    private static boolean dfs(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (Integer u : graph.get(v)) {
            if (p[u] == -1 || dfs(p[u])) {
                p[u] = v;
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        m = scanner.nextInt();
        a = scanner.nextInt();
        b = scanner.nextInt();
        scanner.nextLine();
        board = new int[n][m];
        used = new boolean[n * m];
        p = new int[n * m];
        Arrays.fill(p, -1);
        graph = new ArrayList<>();
        for (int i = 0; i < n * m; i++) {
            graph.add(new ArrayList<>());
        }
        int countFree = 0;
        for (int i = 0; i < n; i++) {
            String string = scanner.nextLine();
            for (int j = 0; j < m; j++) {
                board[i][j] = string.charAt(j) != '.' ? 0 : 1;
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == 0) {
                    countFree++;
                    if (j + 1 < m && board[i][j + 1] == 0) {
                        graph.get(i * m + j).add(i * m + j + 1);
                    }
                    if (j - 1 >= 0 && board[i][j - 1] == 0) {
                        graph.get(i * m + j).add(i * m + j - 1);
                    }
                    if (i + 1 < n && board[i + 1][j] == 0) {
                        graph.get(i * m + j).add(i * m + j + m);
                    }
                    if (i - 1 >= 0 && board[i - 1][j] == 0) {
                        graph.get(i * m + j).add(i * m + j - m);
                    }
                }
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                Arrays.fill(used, false);
                if ((i + j) % 2 == 0) {
                    if (dfs(i * m + j)) {
                        count++;
                    }
                }
            }
        }
        System.out.println(Math.min(count * a + b * (countFree - count * 2), countFree * b));
    }
}
