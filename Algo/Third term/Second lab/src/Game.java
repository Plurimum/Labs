import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private static int n, m, s;
    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int[] d;

    private static void init(Scanner scanner) {
        graph = new ArrayList<>();
        n = scanner.nextInt();
        m = scanner.nextInt();
        s = scanner.nextInt() - 1;
        d = new int[n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            d[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < m; i++) {
            graph.get(scanner.nextInt() - 1).add(scanner.nextInt() - 1);
        }
    }

    private static void dfs(int u) {
        used[u] = true;
        for (int i = 0; i < graph.get(u).size(); i++) {
            int v = graph.get(u).get(i);
            if (!used[v]) {
                dfs(v);
            }
        }
        if (graph.get(u).size() == 0) {
            d[u] = 0;
        } else {
            boolean losingVertex = true;
            for (int i = 0; i < graph.get(u).size(); i++) {
                int v = graph.get(u).get(i);
                if (d[v] == 0) {
                    d[u] = 1;
                    losingVertex = false;
                    break;
                }
            }
            if (losingVertex) {
                d[u] = 0;
            }
        }
    }

    private static void printAnswer(PrintWriter writer) {
        String winner;
        if (d[s] == 0) {
            winner = "Second";
        } else {
            winner = "First";
        }
        writer.println(winner + " player wins");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("game.in")));
        PrintWriter writer = new PrintWriter("game.out");
        init(scanner);
        dfs(s);
        printAnswer(writer);
        writer.close();
    }
}
