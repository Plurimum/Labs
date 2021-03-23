import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

public class ShortestPaths {

    private static ArrayList<ArrayList<Edge>> graph;
    private static int n, m, s;
    private static long[][] d, dCheck;
    private static boolean[] used;

    static class Edge implements Comparable<Edge> {
        int to;
        long weight;

        Edge(int to, long weight) {
            this.to = to;
            this.weight = weight;
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
            int result = Long.compare(this.weight, edge.weight);
            if (result == 0) {
                result = Integer.compare(this.to, edge.to);
            }
            return result;
        }
    }

    private static void init(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        s = scanner.nextInt() - 1;
        graph = new ArrayList<>();
        d = new long[n][2];
        dCheck = new long[n][2];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            d[i][0] = Integer.MAX_VALUE;
            dCheck[i][0] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < m; i++) {
            graph.get(scanner.nextInt() - 1).add(new Edge(scanner.nextInt() - 1, scanner.nextLong()));
        }
    }

    private static void dynamics() {
        d[s][0] = 0;
        d[s][1] = 1;
        dCheck[s][0] = 0;
        dCheck[s][1] = 1;
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                for (int y = 0; y < graph.get(j).size(); y++) {
                    int u = graph.get(j).get(y).to;
                    long w = graph.get(j).get(y).weight;
                    if (d[u][1] == 1 && d[j][1] == 1) {
                        if (d[u][0] > d[j][0] + w) {
                            d[u][0] = d[j][0] + w;
                        }
                    } else {
                        if (d[j][1] == 1) {
                            d[u][0] = d[j][0] + w;
                            d[u][1] = 1;
                        }
                    }
                    if (dCheck[u][1] == 1 && dCheck[j][1] == 1) {
                        if (dCheck[u][0] > dCheck[j][0] + w) {
                            dCheck[u][0] = dCheck[j][0] + w;
                        }
                    } else {
                        if (dCheck[j][1] == 1) {
                            dCheck[u][0] = dCheck[j][0] + w;
                            dCheck[u][1] = 1;
                        }
                    }
                }
            }
        }
        for (int j = 0; j < n; j++) {
            for (int y = 0; y < graph.get(j).size(); y++) {
                int u = graph.get(j).get(y).to;
                long w = graph.get(j).get(y).weight;
                if (dCheck[u][1] == 1 && dCheck[j][1] == 1) {
                    if (dCheck[u][0] > dCheck[j][0] + w) {
                        dCheck[u][0] = dCheck[j][0] + w;
                    }
                } else {
                    if (dCheck[j][1] == 1) {
                        dCheck[u][0] = dCheck[j][0] + w;
                        dCheck[u][1] = 1;
                    }
                }
            }
        }
    }

    private static void deleteVertexes() {
        ArrayList<Integer> top = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (d[i][1] == 1) {
                if (dCheck[i][0] < d[i][0]) {
                    top.add(i);
                }
            }
        }
        for (Integer integer : top) {
            dfs(integer);
        }
    }

    private static void dfs(int u) {
        used[u] = true;
        for (int i = 0; i < graph.get(u).size(); i++) {
            int v = graph.get(u).get(i).to;
            if (!used[v]) {
                dCheck[v][1] = -1;
                dfs(v);
            }
        }
    }

    private static void printAnswer() {
        for (int i = 0; i < n; i++) {
            if (dCheck[i][1] == 1 && d[i][1] == 1) {
                if (dCheck[i][0] < d[i][0]) {
                    System.out.println("-");
                    continue;
                }
            } else {
                if (dCheck[i][1] == -1) {
                    System.out.println("-");
                    continue;
                }
            }
            if (d[i][1] == 0) {
                System.out.println("*");
                continue;
            }
            System.out.println(d[i][0]);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        dynamics();
        deleteVertexes();
        printAnswer();
    }
}
