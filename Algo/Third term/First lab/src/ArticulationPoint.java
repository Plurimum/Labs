import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

public class ArticulationPoint {

    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int n;
    private static int[] timeIn;
    private static int[] ups;
    private static int time;
    private static TreeSet<Integer> answer;

    static void dfs (int v, int p) {
        used[v] = true;
        ++time;
        timeIn[v] = time;
        ups[v] = time;
        int outEdges = 0;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (to == p) {
                continue;
            }
            if (!used[to]) {
                dfs(to, v);
                ups[v] = Math.min(ups[v], ups[to]);
                ++outEdges;
                if (ups[to] >= timeIn[v] && p != -1) {
                    answer.add(v);
                }
            } else {
                ups[v] = Math.min(ups[v], timeIn[to]);
            }
        }
        if (p != -1 || outEdges <= 1) {
            return;
        }
        answer.add(v);
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        int m = scanner.nextInt();
        graph = new ArrayList<>();
        used = new boolean[n];
        timeIn = new int[n];
        ups = new int[n];
        answer = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        time = 0;
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1, second = scanner.nextInt() - 1;
            graph.get(first).add(second);
            graph.get(second).add(first);
        }
    }

    static void findPoints() {
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs(i, -1);
            }
        }
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        findPoints();
        System.out.println(answer.size());
        for (Integer index : answer) {
            System.out.print((index + 1) + " ");
        }
    }
}
