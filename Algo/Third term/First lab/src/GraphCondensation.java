import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class GraphCondensation {

    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<ArrayList<Integer>> transposedGraph;
    private static ArrayList<Integer> order;
    private static HashSet<Edge> answer;
    private static boolean[] used;
    private static int[] color;
    private static int n;
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
            return (int) (((long) (from * to)) % 2_147_483_647 % 1_760_777);
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

    static void dfs1(int v) {
        used[v] = true;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int to = graph.get(v).get(i);
            if (!used[to]) {
                dfs1(to);
            }
        }
        order.add(v);
    }

    static void dfs2(int v) {
        used[v] = true;
        color[v] = maxColor;
        for (int i = 0; i < transposedGraph.get(v).size(); i++) {
            int to = transposedGraph.get(v).get(i);
            if (!used[to]) {
                dfs2(to);
            }
        }
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        int m = scanner.nextInt();
        graph = new ArrayList<>();
        transposedGraph = new ArrayList<>();
        order = new ArrayList<>();
        color = new int[n];
        answer = new HashSet<>();
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            transposedGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1, second = scanner.nextInt() - 1;
            graph.get(first).add(second);
            transposedGraph.get(second).add(first);
        }
    }

    static void firstTraversal() {
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs1(i);
            }
        }
    }

    static void secondTraversal() {
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            int v = order.get(n - 1 - i);
            if (!used[v]) {
                dfs2(v);
                maxColor++;
            }
        }
    }

    static void addEdges() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < graph.get(i).size(); j++) {
                int to = graph.get(i).get(j);
                if (color[i] == color[to]) {
                    continue;
                }
                answer.add(new Edge(
                                Math.min(color[i], color[to]),
                                Math.max(color[i], color[to])
                        )
                );
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        firstTraversal();
        secondTraversal();
        addEdges();
        System.out.print(answer.size());
    }
}
