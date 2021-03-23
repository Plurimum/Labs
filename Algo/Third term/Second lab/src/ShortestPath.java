import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class ShortestPath {

    private static ArrayList<ArrayList<Edge>> graph;
    private static TreeSet<Edge> set;
    private static int n, m;
    private static int[] d;
    private static boolean[] used;

    static class Edge implements Comparable<Edge> {
        int to;
        int weight;

        Edge(int weight, int to) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int hashCode() {
            return (int) (((long) (weight * to)) % 2_147_483_647 % 1_760_777);
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
        public String toString(){
            return "Edge " + to + " " + weight;
        }

        @Override
        public int compareTo(Edge edge) {
            int result = Integer.compare(this.weight, edge.weight);
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
        set = new TreeSet<>();
        graph = new ArrayList<>();
        d = new int[n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            d[i] = Integer.MAX_VALUE;
        }
        d[0] = 0;
        for (int i = 0; i < m; i++) {
            split = reader.readLine().split("[\\s]");
            int from = Integer.parseInt(split[0]) - 1, to = Integer.parseInt(split[1]) - 1, weight = Integer.parseInt(split[2]);
            graph.get(from).add(new Edge(weight, to));
            graph.get(to).add(new Edge(weight, from));
        }
        set.add(new Edge(d[0], 0));
    }

    private static void dijkstra() {
        while (!set.isEmpty()) {
            int v = set.first().to;
            set.remove(set.first());
            for (int j = 0; j < graph.get(v).size(); j++) {
                int to = graph.get(v).get(j).to;
                int weight = graph.get(v).get(j).weight;
                if (d[v] + weight < d[to]) {
                    set.remove(new Edge(d[to], to));
                    d[to] = d[v] + weight;
                    set.add(new Edge(d[to], to));
                    for (int value : d) {
                        System.out.print(value + " ");
                    }
                    System.out.print("\n");
                }
            }
        }
    }

    private static void printAnswer() {
        for (int i = 0; i < n; i++) {
            System.out.print(d[i] + " ");
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        init(reader);
        dijkstra();
        //printAnswer();
    }
}
