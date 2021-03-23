import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class TopologicalSort {

    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int[] color;
    private static ArrayList<Integer> answer;
    private static int n, m;
    private static boolean acyclic;

    static void dfs(int v) {
        used[v] = true;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (!used[to]) {
                dfs(to);
            }
        }
        answer.add(v);
    }

    static void topologicalSort() {
        for (int i = 0; i < n; i++) {
            used[i] = false;
        }
        answer.clear();
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs(i);
            }
        }
    }

    //colors
    //white is 0
    //grey is 1
    //black is 2

    static void isAcyclic(int v, int from) {
        color[v] = 1;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int u = graph.get(v).get(i);
            if (color[u] == 0) {
                isAcyclic(u, v);
            }
            if (color[u] == 1 && u != from) {
                acyclic = false;
            }
        }
        color[v] = 2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        m = scanner.nextInt();
        acyclic = true;
        graph = new ArrayList<>();
        used = new boolean[n];
        color = new int[n];
        answer = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt(), second = scanner.nextInt();
            if (first == second) {
                acyclic = false;
            }
            graph.get(first - 1).add(second - 1);
        }
        isAcyclic(0, -1);
        if (!acyclic) {
            System.out.println(-1);
            return;
        }
        topologicalSort();
        for (int i = answer.size() - 1; i >= 0; i--) {
            System.out.print((answer.get(i) + 1) + " ");
        }
    }
}
