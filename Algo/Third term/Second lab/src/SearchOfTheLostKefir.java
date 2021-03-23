import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.TreeSet;

public class SearchOfTheLostKefir {

    private static ArrayList<ArrayList<Edge>> graph;
    private static TreeSet<Edge> set;
    private static double[] d;
    private static int n, m, a, b, c;
    private static double pathAB, pathAC, pathBC;

    static class Edge implements Comparable<Edge> {
        double weight;
        int to;

        Edge(double weight, int to) {
            this.weight = weight;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return (int) (((long) (weight + to)) % 2_147_483_647 % 1_760_777);
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

            return weight == edge.weight && to == edge.to;
        }

        @Override
        public String toString() {
            return "Edge " + " " + to + " " + weight;
        }

        @Override
        public int compareTo(Edge edge) {
            int result = Double.compare(this.weight, edge.weight);
            if (result == 0) {
                result = Integer.compare(this.to, edge.to);
            }
            return result;
        }
    }

    private static void init(BufferedReader reader) throws IOException {
        String[] split = reader.readLine().split("[\\s]");
        n = Integer.parseInt(split[0]);
        m = Integer.parseInt(split[1]);
        graph = new ArrayList<>();
        d = new double[n];
        set = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            split = reader.readLine().split("[\\s]");
            int from = Integer.parseInt(split[0]) - 1, to = Integer.parseInt(split[1]) - 1, weight = Integer.parseInt(split[2]);
            graph.get(from).add(new Edge(weight, to));
            graph.get(to).add(new Edge(weight, from));
        }
        split = reader.readLine().split("[\\s]");
        a = Integer.parseInt(split[0]) - 1;
        b = Integer.parseInt(split[1]) - 1;
        c = Integer.parseInt(split[2]) - 1;

    }

    private static void dijkstra(int start) {
        for (int i = 0; i < n; i++) {
            d[i] = Double.POSITIVE_INFINITY;
        }
        d[start] = 0;
        set.clear();
        set.add(new Edge(0, start));
        while (!set.isEmpty()) {
            int v = set.first().to;
            set.remove(set.first());
            for (int j = 0; j < graph.get(v).size(); j++) {
                int to = graph.get(v).get(j).to;
                double weight = graph.get(v).get(j).weight;
                if (d[v] + weight < d[to]) {
                    set.remove(new Edge(d[to], to));
                    d[to] = d[v] + weight;
                    set.add(new Edge(d[to], to));
                }
            }
        }
    }

    private static void printAnswer() {
        double ans = Math.min(pathAB + pathAC, Math.min(pathAC + pathBC, pathAB + pathBC));
        if (ans == Double.POSITIVE_INFINITY) {
            System.out.print(-1);
        } else {
            System.out.print((long) ans);
        }

    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        init(reader);
        dijkstra(a);
        pathAB = d[b];
        pathAC = d[c];
        dijkstra(b);
        pathBC = d[c];
        printAnswer();
    }
}
