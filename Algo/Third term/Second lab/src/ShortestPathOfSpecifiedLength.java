import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ShortestPathOfSpecifiedLength {

    private static int n, m, k, s;
    private static ArrayList<ArrayList<Edge>> graph;
    private static long[][][] d;

    static class Edge implements Comparable<Edge> {
        int to;
        int weight;

        Edge(int to, int weight) {
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
            return "Edge " + " " + to + " " + weight;
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

    private static void init(Scanner scanner) {
        graph = new ArrayList<>();
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        s = scanner.nextInt() - 1;
        d = new long[n][101][2];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            graph.get(scanner.nextInt() - 1).add(new Edge(scanner.nextInt() - 1, scanner.nextInt()));
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 101; j++) {
                d[i][j][0] = Integer.MAX_VALUE;
            }
        }
    }

    private static void dynamics() {
        d[s][0][0] = 0;
        d[s][0][1] = 1;
        for (int i = 1; i < 101; i++) {
            for (int u = 0; u < n; u++) {
                for (int v = 0; v < graph.get(u).size(); v++) {
                    int to = graph.get(u).get(v).to;
                    int weight = graph.get(u).get(v).weight;
                    if (d[to][i][1] == 1) {
                        if (d[u][i - 1][0] + weight < d[to][i][0]) {
                            d[to][i][0] = d[u][i - 1][0] + weight;
                        }
                    } else {
                        if (d[u][i - 1][1] == 1) {
                            d[to][i][0] = d[u][i - 1][0] + weight;
                            d[to][i][1] = 1;
                        }
                    }
                }
            }
        }
    }

    private static void printAnswer() {
        for (int i = 0; i < n; i++) {
            if (d[i][k][1] != 1) {
                System.out.println(-1);
            } else {
                System.out.println(d[i][k][0]);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        dynamics();
        printAnswer();
    }
}
