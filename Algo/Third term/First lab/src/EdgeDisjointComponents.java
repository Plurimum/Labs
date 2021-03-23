import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class EdgeDisjointComponents {
    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int n;
    private static int[] colors;
    private static int[] timeIn;
    private static int[] ups;
    private static int time;
    private static HashSet<Edge> edges;
    private static HashSet<Edge> duplicates;
    private static TreeSet<Integer> answer;
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

    static void dfs(int v, int p) {
        ++time;
        used[v] = true;
        timeIn[v] = time;
        ups[v] = time;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (to == p) {
                continue;
            }
            if (!used[to]) {
                dfs(to, v);
                ups[v] = Math.min(ups[v], ups[to]);
            } else {
                ups[v] = Math.min(ups[v], timeIn[to]);
            }
        }
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        int m = scanner.nextInt();
        colors = new int[n];
        graph = new ArrayList<>();
        used = new boolean[n];
        timeIn = new int[n];
        ups = new int[n];
        edges = new HashSet<>();
        duplicates = new HashSet<>();
        answer = new TreeSet<>();
        time = -1;
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        time = 0;
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1, second = scanner.nextInt() - 1;
            if ((edges.contains(new Edge(first, second)) || edges.contains(new Edge(second, first))) &&
                    (!duplicates.contains(new Edge(first, second)) && !duplicates.contains(new Edge(second, first)))) {
                duplicates.add(new Edge(first, second));
                duplicates.add(new Edge(second, first));
            } else {
                graph.get(first).add(second);
                graph.get(second).add(first);
                edges.add(new Edge(first, second));
                edges.add(new Edge(second, first));
            }
        }
    }

    static void paint(int v, int color) {
        colors[v] = color;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (colors[to] == 0) {
                if (ups[to] > timeIn[v] && !duplicates.contains(new Edge(to, v))) {
                    maxColor++;
                    paint(to, maxColor);
                } else {
                    paint(to, color);
                }
            }
        }
    }

    static void findBridges() {
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs(i, -1);
            }
        }
    }

    static void findComponents() {
        for (int i = 0; i < n; i++) {
            if (colors[i] == 0) {
                maxColor++;
                paint(i, maxColor);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        findBridges();
        findComponents();
        System.out.println(maxColor);
        for (int color : colors) {
            System.out.print(color + " ");
        }
    }
}
