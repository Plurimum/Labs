import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.TreeSet;

public class GraphDestroying {

    static class Edge implements Comparable<Edge> {
        int from;
        int to;
        int index;
        long weight;

        Edge(int from, int to, int index, long weight) {
            this.from = from;
            this.to = to;
            this.index = index;
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

            return weight == edge.weight && to == edge.to && from == edge.from && index == edge.index;
        }

        @Override
        public String toString(){
            return "Edge " + from + " " + to + " " + index + " " + weight;
        }

        @Override
        public int compareTo(Edge edge) {
            return Long.compare(this.weight, edge.weight);
        }
    }

    private static class SDS {
        int size;
        ArrayList<Integer> parents = new ArrayList<>();
        ArrayList<Integer> rank = new ArrayList<>();

        SDS(int n) {
            size = n;
            for (int i = 0; i < n; i++) {
                rank.add(1);
                parents.add(i);
            }
        }

        public int getId(int x) {
            if (parents.get(x) != x) {
                parents.set(x, getId(parents.get(x)));
            }
            return parents.get(x);
        }

        public void join(int x, int y) {
            int xId = getId(x);
            int yId = getId(y);
            if (xId != yId) {
                if (rank.get(xId) < rank.get(yId)) {
                    parents.set(xId, yId);
                } else {
                    if (rank.get(xId).equals(rank.get(yId))) {
                        rank.set(xId, rank.get(xId) + 1);
                    }
                    parents.set(yId, xId);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("destroy.in")));
        PrintWriter writer = new PrintWriter(new FileWriter("destroy.out"));
        int n, m;
        long s;
        n = scanner.nextInt();
        m = scanner.nextInt();
        s = scanner.nextLong();
        ArrayList<Edge> edges = new ArrayList<>();
        ArrayList<Edge> notUsed = new ArrayList<>();
        SDS dSet = new SDS(n);
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            long weight = scanner.nextLong();
            edges.add(new Edge(from - 1, to - 1, i + 1, weight));
        }
        Collections.sort(edges);
        Collections.reverse(edges);
        for (Edge edge : edges) {
            if (dSet.getId(edge.from) != dSet.getId(edge.to)) {
                dSet.join(edge.from, edge.to);
            } else {
                notUsed.add(edge);
            }
        }
        TreeSet<Integer> sortedIndexes = new TreeSet<>();
        Collections.reverse(notUsed);
        for (Edge edge : notUsed) {
            if ((s -= edge.weight) >= 0) {
                sortedIndexes.add(edge.index);
            } else {
                break;
            }
        }
        writer.write(sortedIndexes.size() + "\n");
        for (int index : sortedIndexes) {
            writer.write(index + " ");
        }
        scanner.close();
        writer.close();
    }
}
