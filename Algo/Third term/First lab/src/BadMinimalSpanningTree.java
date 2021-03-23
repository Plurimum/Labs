import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class BadMinimalSpanningTree {

    private static ArrayList<ArrayList<Edge>> graph;
    private static int[] min_e;
    private static int[] sel_e;
    private static ArrayDeque<Pair> queue;
    private static int n, m;
    private static int minWeight = 0;

    static class Pair {
        int weight;
        int to;

        Pair(int weight, int to) {
            this.weight = weight;
            this.to = to;
        }
    }

    static class Edge {
        int to;
        int weight;

        Edge(int to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        @Override
        public int hashCode() {
            return (int) (((long) (to * weight)) % 2_147_483_647 % 1_760_777);
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
            return to == edge.to && weight == edge.weight;
        }
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        graph = new ArrayList<>();
        queue = new ArrayDeque<>();
        min_e = new int[n];
        sel_e = new int[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        Arrays.fill(min_e, 1000000000);
        Arrays.fill(sel_e, -1);
        queue.add(new Pair(0, 0));
        min_e[0] = 0;
        for (int i = 0; i < m; i++) {
            int first = scanner.nextInt() - 1, second = scanner.nextInt() - 1;
            int weight = scanner.nextInt();
            graph.get(first).add(new Edge(second, weight));
            graph.get(second).add(new Edge(first, weight));
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);

        for (int i = 0; i < n; i++) {
            if (queue.isEmpty()) {
                System.out.print("no mst");
                return;
            }
            Pair cur = queue.peekFirst();
            int v = queue.peekFirst().to;
            //System.out.println("on " + i + " iteration pair is: " + (cur.to + 1) + " " + cur.weight);
            queue.remove();
            if (sel_e[v] != -1) {
                //System.out.println((v + 1) + " -> " + (sel_e[v] + 1) + " weight: " + min_e[v]);
                minWeight += min_e[v];
                graph.remove(v);
            }
            for (int j = 0; j < graph.get(v).size(); j++) {
                int to = graph.get(v).get(j).to;
                int cost = graph.get(v).get(j).weight;
                //System.out.println("checking " + (v + 1) + " -> " + (to + 1));
                if (cost < min_e[to]) {
                    queue.remove(new Pair(min_e[to], to));
                    //System.out.println("updating " + (v + 1) + " -> " + (to + 1) + " with cost " + cost);
                    min_e[to] = cost;
                    sel_e[to] = v;
                    queue.addLast(new Pair(min_e[to], to));
                }
            }
            //System.out.println();
        }
        //System.out.println("MST: " + minWeight);
        System.out.print(minWeight);
    }
}
