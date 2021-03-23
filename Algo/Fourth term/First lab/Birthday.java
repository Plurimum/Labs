import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class Birthday {

    private static int m, n, k;
    private static ArrayList<HashSet<Integer>> graph;
    private static ArrayList<HashSet<Integer>> complGraph;
    private static int[] px;
    private static int[] py;
    private static boolean[] used;

    private static boolean dfs(int i) {
        if (used[i]) {
            return false;
        }
        used[i] = true;
        for (int j : graph.get(i)) {
            if (py[j - n] == -1) {
                py[j - n] = i;
                px[i] = j;
                return true;
            } else {
                if (dfs(py[j - n])) {
                    py[j - n] = i;
                    px[i] = j;
                    return true;
                }
            }
        }
        return false;
    }

    private static void fordFalkerson() {
        boolean isPath = true;
        while (isPath) {
            isPath = false;
            Arrays.fill(used, false);
            for (int i = 0; i < n; i++) {
                if (px[i] == -1) {
                    if (dfs(i)) {
                        isPath = true;
                    }
                }
            }
        }
    }

    private static void commonDFS(int v) {
        if (used[v]) {
            return;
        }
        used[v] = true;
        for (Integer i : complGraph.get(v)) {
            commonDFS(i);
        }
    }


    private static void complement(int start1, int end1, int start2, int end2) {
        for (int i = start1; i < end1; i++) {
            for (int j = start2; j < end2; j++) {
                if (graph.get(i).contains(j)) {
                    graph.get(i).remove(j);
                } else {
                    graph.get(i).add(j);
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        k = scanner.nextInt();
        int vertexB;
        StringBuilder answer = new StringBuilder();
        for (int t = 0; t < k; t++) {
            n = scanner.nextInt();
            m = scanner.nextInt();
            px = new int[n];
            py = new int[m];
            Arrays.fill(px, -1);
            Arrays.fill(py, -1);
            used = new boolean[m + n];
            graph = new ArrayList<>();
            complGraph = new ArrayList<>();
            HashSet<Integer> max = new HashSet<>();
            HashSet<Integer> set = new HashSet<>();
            TreeSet<Integer> boys = new TreeSet<>();
            TreeSet<Integer> girls = new TreeSet<>();
            for (int i = 0; i < n + m; i++) {
                graph.add(new HashSet<>());
                complGraph.add(new HashSet<>());
            }
            for (int j = 0; j < n; j++) {
                while (true) {
                    vertexB = scanner.nextInt();
                    if (vertexB == 0) {
                        break;
                    }
                    graph.get(j).add(vertexB + n - 1);
                    graph.get(vertexB + n - 1).add(j);
                }
            }
            complement(0, n, n, n + m);
            complement(n, n + m, 0, n);
            fordFalkerson();
            for (int i = 0; i < n + m; i++) {
                max.add(i);
            }
            for (int i = 0; i < n; i++) {
                if (px[i] != -1) {
                    complGraph.get(px[i]).add(i);
                    set.add(i);
                }
            }
            for (int i = 0; i < n; i++) {
                for (int j : graph.get(i)) {
                    if (!complGraph.get(j).contains(i)) {
                        complGraph.get(i).add(j);
                    }
                }
            }
            Arrays.fill(used, false);
            for (int i = 0; i < n; i++) {
                if (!set.contains(i)) {
                    commonDFS(i);
                }
            }
            set = new HashSet<>();
            for (int i = 0; i < n + m; i++) {
                if ((!used[i] && i < n) || (used[i] && i >= n)) {
                    set.add(i);
                }
            }
            max.removeAll(set);
            for (Integer i : max) {
                if (i < n) {
                    boys.add(i);
                } else {
                    girls.add(i - n);
                }
            }
            answer.append((girls.size() + boys.size())).append("\n");
            answer.append(boys.size()).append(" ").append(girls.size()).append("\n");
            String out = " ";
            for (int i : boys) {
                if (i == boys.last()) {
                    out = " \n";
                }
                answer.append((i + 1)).append(out);
            }
            out = " ";
            for (int i : girls) {
                answer.append((i + 1)).append(out);
            }
            answer.append("\n\n");
        }
        System.out.println(answer);
    }
}
