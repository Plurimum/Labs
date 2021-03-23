import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class HamiltonCycle {

    private static int n;
    private static int start;
    private static int[][] graph;
    private static ArrayList<Integer> queue;

    private static void findHamiltonianCycle() {
        for (int i = 0; i < n; i++) {
            queue.add(i);
        }
        for (int k = 0; k < n * (n - 1); k++) {
            if (graph[queue.get(start)][queue.get(start + 1)] != 1) {
                int i = 2;
                while (graph[queue.get(start)][queue.get(start + i)] != 1 ||
                        graph[queue.get(start + 1)][queue.get(start + i + 1)] != 1) {
                    i++;
                }
                int x1 = 1;
                int x2 = i;
                while (x1 < x2) {
                    int tmp = queue.get(start + x1);
                    queue.set(start + x1, queue.get(start + x2));
                    queue.set(start + x2, tmp);
                    x1++;
                    x2--;
                }
            }
            queue.add(queue.get(start));
            start++;
            if (start > 100) {
                queue.subList(0, start).clear();
                start = 0;
            }
        }
    }

    private static void init(Scanner scanner) {
        queue = new ArrayList<>();
        n = scanner.nextInt();
        graph = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                graph[i][j] = 0;
            }
        }
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < line.length(); j++) {
                int edge = line.charAt(j) - 48;
                if (edge == 1) {
                    graph[i][j] = 1;
                    graph[j][i] = 1;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        findHamiltonianCycle();
        for (int i = start; i < queue.size(); i++) {
            System.out.print((queue.get(i) + 1) + " ");
        }
    }
}
