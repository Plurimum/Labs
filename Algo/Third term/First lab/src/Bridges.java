import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Bridges {

    private static ArrayList<ArrayList<Integer>> graph;
    private static boolean[] used;
    private static int n;
    private static int[] timeIn;
    private static int[] ups;
    private static int time;
    private static HashMap<Edge, Integer> edges;
    private static TreeSet<Integer> answer;

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
        if (p != -1 && ups[v] > timeIn[p]) {
            //addEdge(v, p);
            Edge tmp = new Edge(v, p);
            if (!edges.containsKey(tmp)) {
                tmp.from = p;
                tmp.to = v;
            }
            answer.add(edges.get(tmp));
        }
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        int m = scanner.nextInt();
        graph = new ArrayList<>();
        used = new boolean[n];
        timeIn = new int[n];
        ups = new int[n];
        edges = new HashMap<>();
        answer = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        time = 0;
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1, second = scanner.nextInt() - 1;
            graph.get(first).add(second);
            graph.get(second).add(first);
            edges.put(new Edge(first, second), i);
            edges.put(new Edge(second, first), i);
        }
    }

    static void findBridges() {
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs(i, -1);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        findBridges();
        System.out.println(answer.size());
        for (Integer index : answer) {
            System.out.print((index + 1) + " ");
        }
    }
}
