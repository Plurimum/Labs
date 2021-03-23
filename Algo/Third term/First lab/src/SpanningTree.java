import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class SpanningTree {

    private static int n;
    private static Point[] points;
    private static double minimalSpanningTree;
    private static final int MAX_COORDINATE = 10_000;
    private static final double MAX_DISTANCE = getDistance(
            new Point(-MAX_COORDINATE, -MAX_COORDINATE),
            new Point(MAX_COORDINATE, MAX_COORDINATE)) + 1;
    private static double[] edgeWeight;
    private static boolean[] used;

    static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    static double getDistance(Point first, Point second) {
        return Math.sqrt(
                (second.x - first.x) * (second.x - first.x) +
                (second.y - first.y) * (second.y - first.y)
        );
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        points = new Point[n];
        edgeWeight = new double[n];
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(scanner.nextInt(), scanner.nextInt());
            edgeWeight[i] = MAX_DISTANCE;
        }
        edgeWeight[0] = 0;
        int a = 0;
        for (int i = 1; i < n; i++) {
            for (int b = 0; b < n; b++) {
                if (!used[b] && edgeWeight[b] != 0) {
                    edgeWeight[b] = Math.min(edgeWeight[b], getDistance(points[a], points[b]));
                }
            }
            used[a] = true;
            double currentMinimum = MAX_DISTANCE;
            for (int j = 0; j < n; j++) {
                if (used[j]) {
                    continue;
                }
                if (edgeWeight[j] != 0 && edgeWeight[j] < currentMinimum) {
                    currentMinimum = edgeWeight[j];
                    a = j;
                }
            }
            minimalSpanningTree += currentMinimum;
        }
        System.out.print(minimalSpanningTree);
    }
}
