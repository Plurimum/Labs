import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class GraphColoring {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph;
    private static int[] degrees;
    private static int[] answer;
    private static PriorityQueue<Integer> queue;
    private static boolean[] used;

    private static void bfs() {
        queue.add(0);
        while (!queue.isEmpty()) {
            int v = queue.peek();
            queue.remove();
            if (used[v]) {
                continue;
            }
            TreeSet<Integer> set = new TreeSet<>();
            for (Integer vertex : graph.get(v)) {
                set.add(answer[vertex]);
                queue.add(vertex);
            }
            int min = 1;
            for (int color : set) {
                if (color == min) {
                    min++;
                } else {
                    break;
                }
            }
            answer[v] = min;
            used[v] = true;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        m = scanner.nextInt();
        graph = new ArrayList<>();
        answer = new int[n];
        Arrays.fill(answer, Integer.MAX_VALUE);
        queue = new PriorityQueue<>();
        used = new boolean[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        degrees = new int[n];
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            degrees[from]++;
            degrees[to]++;
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        bfs();
        int maxDegree = 0;
        for (int i = 0; i < n; i++) {
            if (degrees[i] > maxDegree) {
                maxDegree = degrees[i];
            }
        }
        System.out.println(maxDegree | 1);
        for (int i = 0; i < n; i++) {
            System.out.println(answer[i]);
        }
    }
}
