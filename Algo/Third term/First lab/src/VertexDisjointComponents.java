import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

public class VertexDisjointComponents {
    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int n;
    private static int[] timeIn;
    private static int[] ups;
    private static int time;
    private static TreeSet<Integer> answer;
    private static HashMap<Edge, Integer> colors;
    private static ArrayList<Edge> edges;
    private static int maxColor;

    static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return (int)(((long)(from * to)) % 2_147_483_647 % 1_760_777);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Edge edge = (Edge) obj;

            return from == edge.from && to == edge.to;
        }
    }

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
        colors = new HashMap<>();
        edges = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        time = -1;
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1, second = scanner.nextInt() - 1;
            if (first <= second) {
                colors.put(new Edge(first, second), 0);
                edges.add(new Edge(first, second));
            } else {
                colors.put(new Edge(second, first), 0);
                edges.add(new Edge(second, first));
            }
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

    static void paint(int v, int color, int parent) {
        used[v] = true;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (to == parent) {
                continue;
            }
            if (!used[to]) {
                if (ups[to] >= timeIn[v]) {
                    int newColor = ++maxColor;
                    colors.put(new Edge(Math.min(v, to), Math.max(v, to)), newColor);
                    paint(to, newColor, v);
                } else {
                    colors.put(new Edge(Math.min(v, to), Math.max(v, to)), color);
                    paint(to, color, v);
                }
            } else {
                if (timeIn[to] < timeIn[v]) {
                    colors.put(new Edge(Math.min(v, to), Math.max(v, to)), color);
                }
            }
        }
    }

    static void findComponents() {
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                paint(i, maxColor, -1);
            }
        }
    }

    public static void main (String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        findPoints();
        used = new boolean[n];
        findComponents();
        System.out.println(maxColor);
        for (Edge edge: edges){
            System.out.print(colors.get(edge) + " ");
        }
    }
}
