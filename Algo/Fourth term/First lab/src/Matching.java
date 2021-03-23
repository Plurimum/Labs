import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Matching {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> graph;
    private static int[] p;
    private static boolean[] used;

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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        m = scanner.nextInt();
        graph = new ArrayList<>();
        p = new int[m];
        used = new boolean[n];
        int vertexB;
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            while (true) {
                vertexB = scanner.nextInt();
                if (vertexB == 0) {
                    break;
                }
                graph.get(i).add(vertexB - 1);
            }
        }
        Arrays.fill(p, -1);
        int count = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, false);
            if (dfs(i)) {
                count++;
            }
        }
        System.out.println(count);
        for (int i = 0; i < m; i++) {
            if (p[i] != -1) {
                System.out.println((p[i] + 1) + " " + (i + 1));
            }
        }
    }
}
