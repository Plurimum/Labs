import java.io.*;
import java.util.Scanner;

public class CountOfWordsWithLengthL {

    private static int n, count;
    private static int[][] transfers;
    private static int[][] dp;
    private static boolean[][] visited;

    static class Vertex {
        boolean ending;

        Vertex(boolean ending) {
            this.ending = ending;
        }
    }

    private static void solve(Vertex[] states, int l){
        for (int i = 0; i < n; i++) {
            if (states[i].ending) {
                count = (count + solve(i, l)) % 1000000007;
            }
        }
    }

    private static int solve(int v, int len) {
        if (len < 0) {
            return 0;
        }
        if (visited[v][len]) {
            return dp[v][len];
        }
        visited[v][len] = true;
        for (int i = 0; i < n; i++) {
            if (transfers[v][i] != 0) {
                for (int j = 0; j < transfers[v][i]; j++) {
                    dp[v][len] = (dp[v][len] + solve(i, len - 1)) % 1000000007;
                }
            }
        }
        return dp[v][len];
    }

    public static void initStates(Vertex[] states, Scanner scanner, int k){
        for (int i = n - 1; i >= 0; --i) {
            states[i] = new Vertex(false);
        }
        for (int i = k - 1; i >= 0; --i) {
            states[scanner.nextInt() - 1].ending = true;
        }
    }

    public static void initTransfers(Scanner scanner, int m){
        for (int i = m - 1; i >= 0; --i) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            scanner.next();
            transfers[--to][--from]++;
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new BufferedReader(new FileReader("problem4.in")));
        PrintWriter writer = new PrintWriter("problem4.out");

        n = scanner.nextInt();
        int m = scanner.nextInt(), k = scanner.nextInt(), l = scanner.nextInt();

        Vertex[] states = new Vertex[n];
        transfers = new int[n][n];
        dp = new int[n][l + 3];
        visited = new boolean[n][l + 3];
        visited[0][0] = true;
        dp[0][0] = 1;

        initStates(states, scanner, k);
        initTransfers(scanner, m);
        solve(states, l);

        writer.print(count);
        writer.close();
    }
}