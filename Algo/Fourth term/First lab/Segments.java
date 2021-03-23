import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Segments {

    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<ArrayList<Integer>> invertedGraph;
    private static boolean[] used;
    private static int[] p;
    private static int n;

    private static class Segment {
        int x1;
        int y1;
        int x2;
        int y2;
        boolean vertical;

        Segment(int x1, int y1, int x2, int y2, boolean vertical) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.vertical = vertical;
        }
    }

    private static boolean dfs(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (Integer u : invertedGraph.get(v)) {
            if (p[u] == -1 || dfs(p[u])) {
                p[u] = v;
                return true;
            }
        }
        return false;
    }

    private static boolean intersect(Segment vertical, Segment horizontal) {
        return vertical.x1 <= horizontal.x2 &&
                vertical.x1 >= horizontal.x1 &&
                vertical.y1 <= horizontal.y1 &&
                vertical.y2 >= horizontal.y2;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        graph = new ArrayList<>();
        invertedGraph = new ArrayList<>();
        p = new int[n];
        Arrays.fill(p, -1);
        used = new boolean[n];
        ArrayList<Segment> verticals = new ArrayList<>();
        ArrayList<Segment> horizontals = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            int x1, x2, y1, y2;
            x1 = scanner.nextInt();
            y1 = scanner.nextInt();
            x2 = scanner.nextInt();
            y2 = scanner.nextInt();
            Segment segment = new Segment(
                    Math.min(x1, x2),
                    Math.min(y1, y2),
                    Math.max(x2, x1),
                    Math.max(y2, y1),
                    x1 == x2
            );
            if (segment.vertical) {
                verticals.add(segment);
            } else {
                horizontals.add(segment);
            }
        }
        for (int i = 0; i < verticals.size(); i++) {
            graph.add(new ArrayList<>());
            invertedGraph.add(new ArrayList<>());
        }
        for (int i = 0; i < verticals.size(); i++) {
            for (int j = 0; j < horizontals.size(); j++) {
                if (!intersect(verticals.get(i), horizontals.get(j))) {
                    graph.get(i).add(j);
                }
            }
        }
        for (int i = 0; i < verticals.size(); i++) {
            for (int j = 0; j < horizontals.size(); j++) {
                if (!graph.get(i).contains(j)) {
                    invertedGraph.get(i).add(j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < verticals.size(); i++) {
            Arrays.fill(used, false);
            if (dfs(i)) {
                count++;
            }
        }
        System.out.println(n - count);
    }
}
