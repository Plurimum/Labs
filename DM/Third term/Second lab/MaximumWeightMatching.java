import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class MaximumWeightMatching {

    private static boolean[] used;
    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<Integer> leftPart;
    private static ArrayList<Integer> rightPart;
    private static ArrayList<Vertex> weights;
    private static int n;

    private static class Vertex implements Comparable<Vertex> {
        int weight;
        int index;

        Vertex(int weight, int index) {
            this.weight = weight;
            this.index = index;
        }

        @Override
        public int hashCode() {
            return (int) (((long) (weight * index)) % 2_147_483_647 % 1_760_777);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Vertex edge = (Vertex) obj;

            return weight == edge.weight && index == edge.index;
        }

        @Override
        public String toString() {
            return "Edge " + " " + index + " " + weight;
        }

        @Override
        public int compareTo(Vertex vertex) {
            return Long.compare(this.weight, vertex.weight);
        }
    }

    private static boolean findMatching(int from) {
        if (used[from]) {
            return false;
        }
        used[from] = true;
        for (Integer to : graph.get(from)) {
            int prevMatchingVertex = leftPart.get(to);
            if (prevMatchingVertex == -228 || findMatching(prevMatchingVertex)) {
                leftPart.set(to, from);
                rightPart.set(from, to);
                return true;
            }
        }
        return false;
    }

    private static void init(Scanner scanner) {
        weights = new ArrayList<>();
        leftPart = new ArrayList<>();
        rightPart = new ArrayList<>();
        graph = new ArrayList<>();
        n = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
            leftPart.add(-228);
            rightPart.add(-228);
        }
        for (int i = 0; i < n; i++) {
            int w = scanner.nextInt();
            weights.add(new Vertex(w, i));
        }
        for (int i = 0; i < n; i++) {
            int edgesCount = scanner.nextInt();
            for (int j = 0; j < edgesCount; j++) {
                int to = scanner.nextInt() - 1;
                graph.get(i).add(to);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("matching.in")));
        PrintWriter writer = new PrintWriter(new FileWriter("matching.out"));
        init(scanner);
        Collections.sort(weights);
        Collections.reverse(weights);
        for (Vertex v : weights) {
            used = new boolean[n];
            findMatching(v.index);
        }
        for (int rightVertex : rightPart) {
            rightVertex = rightVertex < 0 ? 0 : rightVertex + 1;
            writer.write(rightVertex + " ");
        }
        scanner.close();
        writer.close();
    }
}
