import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

public class GrundyFunction {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<Integer> order;
    private static int[] timeIn;
    private static int[] timeOut;
    private static int[] used;
    private static int[] g;
    private static int time;

    private static void init(BufferedReader reader) throws IOException {
        String[] split = reader.readLine().split("[\\s]");
        n = Integer.parseInt(split[0]);
        m = Integer.parseInt(split[1]);
        graph = new ArrayList<>();
        timeIn = new int[n];
        timeOut = new int[n];
        used = new int[n];
        g = new int[n];
        order = new ArrayList<>();
        time = -1;
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            split = reader.readLine().split("[\\s]");
            int from = Integer.parseInt(split[0]) - 1;
            int to = Integer.parseInt(split[1]) - 1;
            graph.get(from).add(to);
        }
    }

    private static void dfs(int v) {
        used[v] = 1;
        time++;
        timeIn[v] = time;
        for (int u : graph.get(v)) {
            if (used[u] == 0) {
                dfs(u);
            }
        }
        used[v] = 2;
        time++;
        timeOut[v] = time;
        order.add(v);
    }

    private static void traversal() {
        for (int i = 0; i < n; i++) {
            if (used[i] == 0) {
                dfs(i);
            }
        }
    }

    private static void grundy() {
        for (int v : order) {
            if (graph.get(v).isEmpty()) {
                g[v] = 0;
            } else {
                TreeSet<Integer> set = new TreeSet<>();
                for (int u : graph.get(v)) {
                    set.add(g[u]);
                }
                int min = 0;
                for (int u : set) {
                    if (u == min) {
                        min++;
                    } else {
                        break;
                    }
                }
                g[v] = min;
            }
        }
    }

    private static void printAnswer() {
        for (int i = 0; i < n; i++) {
            System.out.println(g[i]);
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        init(reader);
        traversal();
        grundy();
        printAnswer();
    }
}
