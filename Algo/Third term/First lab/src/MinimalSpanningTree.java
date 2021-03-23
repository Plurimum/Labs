import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

public class MinimalSpanningTree {
    private static int n;
    private static int m;
    private static ArrayList<ArrayList<Edge>> weights;
    private static int[] parents;
    private static int[] rank;
    private static long answer;
    private static int MAX_WEIGHT = 100_001;

    static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return (int) (((long) (to * from)) % 2_147_483_647 % 1_760_777);
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
            return to == edge.to && from == edge.from;
        }
    }

    private static int find(int x) {
        if (x != parents[x]) {
            parents[x] = find(parents[x]);
        }
        return parents[x];
    }

    private static void union(int x, int y) {
        x = find(x);
        y = find(y);
        if (x == y) {
            return;
        }
        if (rank[x] == rank[y]) {
            rank[x]++;
        }
        if (rank[x] < rank[y]) {
            parents[x] = y;
        } else {
            parents[y] = x;
        }
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        parents = new int[n];
        rank = new int[n];
        weights = new ArrayList<>();
        for (int i = 0; i < MAX_WEIGHT; i++) {
            weights.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            parents[i] = i;
            rank[i] = 0;
        }
        for (int i = 0; i < m; i++) {
            int first, second, weight;
            first = scanner.nextInt() - 1;
            second = scanner.nextInt() - 1;
            weight = scanner.nextInt();
            weights.get(weight).add(new Edge(first, second));
        }
    }

    static void prim() {
        for (int i = 0; i < MAX_WEIGHT; i++) {
            for (int j = 0; j < weights.get(i).size(); j++) {
                Edge current = weights.get(i).get(j);
                if (find(current.from) != find(current.to)) {
                    answer += i;
                    union(current.from, current.to);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        prim();
        System.out.println(answer);
    }
}
