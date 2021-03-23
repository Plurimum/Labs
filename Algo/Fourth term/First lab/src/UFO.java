import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class UFO {

    private static ArrayList<ArrayList<Integer>> graph;
    private static int[] p;
    private static int v, n;
    private static boolean used[];

    private static class Meeting {
        int x;
        int y;
        int time;

        Meeting(int x, int y, int time) {
            this.x = x;
            this.y = y;
            this.time = time;
        }
    }

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
        v = scanner.nextInt();
        scanner.nextLine();
        graph = new ArrayList<>();
        p = new int[n];
        Arrays.fill(p, -1);
        used = new boolean[2 * n];
        Meeting[] time = new Meeting[n];
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int i = 0; i < n; i++) {
            String[] split = scanner.nextLine().split("[\\s]");
            String[] str = split[0].split(":");
            time[i] = new Meeting(Integer.parseInt(split[1]), Integer.parseInt(split[2]), Integer.parseInt(str[0]) * 60 + Integer.parseInt(str[1]));
        }
        Arrays.sort(time, (Meeting e1, Meeting e2) -> Integer.compare(e1.time, e2.time));
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if ((time[j].time - time[i].time) >=
                        (Math.sqrt(
                                (time[i].x - time[j].x) *
                                        (time[i].x - time[j].x) +
                                        (time[i].y - time[j].y) *
                                                (time[i].y - time[j].y)) / v)
                                * 60) {
                    graph.get(i).add(j);
                }
            }
        }
        int count = 0;
        for (int i = 0; i < n; i++) {
            Arrays.fill(used, false);
            if (dfs(i)) {
                count++;
            }
        }
        System.out.println(n - count);
    }
}
