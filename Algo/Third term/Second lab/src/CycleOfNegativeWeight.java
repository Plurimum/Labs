import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class CycleOfNegativeWeight {

    private static int n, m;
    private static ArrayList<Edge> graph;
    private static ArrayList<Integer> d;
    private static ArrayList<Integer> p;
    private static final int INF = Integer.MAX_VALUE;

    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int weight;

        Edge(int from, int to, int weight) {
            this.from = from;
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

            return weight == edge.weight && to == edge.to && from == edge.from;
        }

        @Override
        public String toString(){
            return "Edge " + from + " " + to + " " + weight;
        }

        @Override
        public int compareTo(Edge edge) {
            int result = Integer.compare(this.weight, edge.weight);
            if (result == 0) {
                result = Integer.compare(this.to, edge.to);
                if (result == 0) {
                    result = Integer.compare(this.from, edge.from);
                }
            }
            return result;
        }
    }

    private static void init(Scanner scanner) {
        n = scanner.nextInt();
        graph = new ArrayList<>();
        d = new ArrayList<>();
        p = new ArrayList<>();
        String a = "abasf";
        String b = "afjkgjka";
        for (int i = 0; i < n; i++) {
            d.add(0);
            p.add(-1);
            for (int j = 0; j < n; j++) {
                graph.add(new Edge(i, j, scanner.nextInt()));
            }
        }
        m = n * n;
    }

    private static void fordBellman() {
        int x = -1;
        for (int i = 0; i < n; i++) {
            x = -1;
            for (int j = 0; j < m; j++) {
                int from = graph.get(j).from;
                int to = graph.get(j).to;
                int weight = graph.get(j).weight;
                if (weight == 100_000) {
                    continue;
                }
                if (d.get(to) > d.get(from) + weight) {
                    d.set(to, Math.max(-INF, d.get(from) + weight));
                    p.set(to, from);
                    x = from;
                }
            }
        }
        if (x == -1) {
            System.out.print("NO");
        } else {
            int y = x;
            for (int i = 0; i < n; i++) {
                y = p.get(y);
            }
            ArrayList<Integer> path = new ArrayList<>();
            for (int cur = y; ; cur = p.get(cur)) {
                path.add(cur);
                if (cur == y && path.size() > 1) {
                    break;
                }
            }
            Collections.reverse(path);
            System.out.print("YES\n");
            path.remove(path.size() - 1);
            System.out.print(path.size() + "\n");
            for (int i = 0; i < path.size(); i++) {
                System.out.print((path.get(i) + 1) + " ");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        fordBellman();
    }
}
