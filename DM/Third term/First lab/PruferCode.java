import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class PruferCode {

    private static final int MAXN = 100_001;
    private static int n;
    private static ArrayList<ArrayList<Integer>> graph;
    private static int[] degree;
    private static boolean[] killed;

    private static ArrayList<Integer> pruferCode() {
        TreeSet<Integer> leaves = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            degree[i] = graph.get(i).size();
            if (degree[i] == 1) {
                leaves.add(i);
            }
            killed[i] = false;
        }
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < n - 2; i++) {
            result.add(0);
        }
        for (int j = 0; j < n - 2; j++) {
            if (!leaves.isEmpty()) {
                int leaf = leaves.stream().iterator().next();
                leaves.remove(leaf);
                killed[leaf] = true;
                int v = -1;
                for (int i = 0; i < graph.get(leaf).size(); i++) {
                    if (!killed[graph.get(leaf).get(i)]) {
                        v = graph.get(leaf).get(i);
                    }
                }
                result.set(j, v);
                degree[v]--;
                if (degree[v] == 1) {
                    leaves.add(v);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        graph = new ArrayList<>();
        degree = new int[MAXN];
        killed = new boolean[MAXN];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n - 1; i++) {
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            graph.get(from).add(to);
            graph.get(to).add(from);
        }
        ArrayList<Integer> answer = pruferCode();
        for (Integer i : answer) {
            System.out.print((i + 1) + " ");
        }
    }
}
