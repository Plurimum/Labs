import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.util.*;

public class Dragons {

    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<EnemiesPair> enemiesPairs;
    private static boolean[] used;
    private static int[] p;
    private static int m, k, n, t, q;

    private static boolean dfs(int v) {
        if (used[v]) {
            return false;
        }
        used[v] = true;
        for (Integer u : graph.get(v)) {
            if (p[u] == -1 || dfs(p[u])) {
                p[u] = v;
                return true;
            }
        }
        return false;
    }

    private static class EnemiesPair {
        int green;
        int yellow;

        EnemiesPair(int green, int yellow) {
            this.green = green;
            this.yellow = yellow;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            EnemiesPair enemiesPair = (EnemiesPair) obj;
            return enemiesPair.green == green && enemiesPair.yellow == yellow;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 53 * hash + this.green + this.yellow;
            return hash;
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        m = scanner.nextInt();
        k = scanner.nextInt();
        n = scanner.nextInt();
        t = scanner.nextInt();
        p = new int[m + k - n];
        Arrays.fill(p, -1);
        graph = new ArrayList<>();
        enemiesPairs = new ArrayList<>();
        for (int i = 0; i < t; i++) {
            int green = scanner.nextInt() - 1;
            int yellow = scanner.nextInt() - 1;
            enemiesPairs.add(new EnemiesPair(green, yellow));
        }
        q = scanner.nextInt();
        ArrayList<Integer> cantAlone = new ArrayList<>();
        for (int i = 0; i < q; i++) {
            cantAlone.add(scanner.nextInt() - 1);
        }
        for (int i = 0; i < m + k - n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            for (int j = m; j < m + k; j++) {
                if (!enemiesPairs.contains(new EnemiesPair(i, j))) {
                    graph.get(i).add(j - m);
                }
                if (!cantAlone.contains(i)) {
                    for (int l = m + k; l < m * 2 + k - n; l++) {
                        graph.get(i).add(l - m);
                    }
                }
            }
        }
        for (int i = m; i < m + k; i++) {
            if (!cantAlone.contains(i)) {
                for (int j = m; j < m + k - n; j++) {
                    graph.get(j).add(i - m);
                }
            }
        }
        used = new boolean[m + k - n];
        for (int i = 0; i < m + k - n; i++) {
            Arrays.fill(used, false);
            dfs(i);
        }
        ArrayList<Integer> answer = new ArrayList<>();
        for (int i = 0; i < k + m - n; i++) {
            if (p[i] == -1) {
                System.out.println("NO");
                return;
            } else {
                if (p[i] < m && i < k) {
                    answer.add(p[i] + 1);
                    answer.add(i + m + 1);
                }
            }
        }
        System.out.println("YES");
        for (int i = 0; i < answer.size(); i += 2) {
            System.out.println(answer.get(i) + " " + answer.get(i + 1));
        }
    }
}
