import java.io.*;
import java.util.*;

public class AirTravels {

    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<ArrayList<Integer>> transposedGraph;
    private static ArrayList<Integer> order;
    private static boolean[] used;
    private static int[] color;
    private static int n;
    private static int maxColor;
    private static HashMap<Integer, ArrayList<Edge>> hashMap;
    private static int[] sortedVolumes;

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
        color[v] = maxColor;
        for (int i = 0; i < transposedGraph.get(v).size(); i++) {
            int to = transposedGraph.get(v).get(i);
            if (color[to] == 0) {
                dfs2(to);
            }
        }
    }

    static void firstTraversal() {
        maxColor = 0;
        used = new boolean[n];
        color = new int[n];
        for (int i = 0; i < n; i++) {
            if (!used[i]) {
                dfs1(i);
            }
        }
    }

    static void secondTraversal() {
        for (int i = 0; i < n; i++) {
            int v = order.remove(n - 1 - i);
            if (color[v] == 0) {
                maxColor++;
                dfs2(v);
            }
        }
    }

    static void init(BufferedReader reader) throws IOException {
        String[] split = reader.readLine().split("[\\s]");
        n = Integer.parseInt(split[0]);
        graph = new ArrayList<>();
        transposedGraph = new ArrayList<>();
        order = new ArrayList<>();
        hashMap = new HashMap<>();
        sortedVolumes = new int[n * n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            transposedGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            split = reader.readLine().split("[\\s]");
            for (int j = 0; j < n; j++) {
                if (i == j) {
                    continue;
                }
                int cur = Integer.parseInt(split[j]) + 1;
                sortedVolumes[i * n + j] = cur;
                ArrayList<Edge> list;
                if (hashMap.containsKey(cur)) {
                    list = hashMap.get(cur);
                } else {
                    list = new ArrayList<>();
                }
                list.add(new Edge(i, j));
                hashMap.put(cur, list);
            }
        }
        Arrays.sort(sortedVolumes);
    }

    public static void main(String[] args) throws IOException {
        //Scanner scanner = new Scanner(new BufferedReader(new FileReader("avia.in")));
        BufferedReader reader = new BufferedReader(new FileReader("avia.in"));
        //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter writer = new PrintWriter("avia.out");
        init(reader);
        int answer = 0;
        int current = 0;
        if (n <= 1) {
            reader.close();
            //System.out.print(Math.max((answer - 1), 0));
            writer.write("0");
            writer.close();
            return;
        }
        while (maxColor != 1) {
            while (sortedVolumes[current] <= answer) {
                current++;
            }
            for (Edge edge : hashMap.get(sortedVolumes[current])) {
                graph.get(edge.from).add(edge.to);
                transposedGraph.get(edge.to).add(edge.from);
                answer = sortedVolumes[current];
            }
            hashMap.remove(sortedVolumes[current]);
            firstTraversal();
            secondTraversal();
        }

        //scanner.close();
        reader.close();
        //System.out.print(Math.max((answer - 1), 0));
        writer.write(String.valueOf(Math.max((answer - 1), 0)));
        writer.close();
    }
}
