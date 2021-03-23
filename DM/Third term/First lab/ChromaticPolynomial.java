import java.util.*;

public class ChromaticPolynomial {

    private static int n, m;
    private static int[][] graph;
    private static int[] count;
    private static ArrayList<ArrayList<Integer>> sets;
    private static Map<Integer, int[]> coeffs;
    private static int[] answer;

    private static void recur(int v) {
        if (v == n) {
            count[sets.size()]++;
            return;
        }
        int size = sets.size();
        for (int i = 0; i < size; i++) {
            boolean permitted = true;
            for (Integer vertex : sets.get(i)) {
                if (graph[v][vertex] == 1) {
                    permitted = false;
                    break;
                }
            }
            if (permitted) {
                sets.get(i).add(v);
                recur(v + 1);
                sets.get(i).remove(sets.get(i).size() - 1);
            }
        }
        ArrayList<Integer> tmp = new ArrayList<>(10);
        tmp.add(v);
        sets.add(tmp);
        recur(v + 1);
        sets.remove(tmp);
    }

    private static void initCoeffs() {
        int[] x1 = {0, 1};
        int[] x2 = {0, -1, 1};
        int[] x3 = {0, 2, -3, 1};
        int[] x4 = {0, -6, 11, -6, 1};
        int[] x5 = {0, 24, -50, 35, -10, 1};
        int[] x6 = {0, -120, 274, -225, 85, -15, 1};
        int[] x7 = {0, 720, -1764, 1624, -735, 175, -21, 1};
        int[] x8 = {0, -5040, 13068, -13132, 6769, -1960, 322, -28, 1};
        int[] x9 = {0, 40320, -109584, 118124, -67284, 22449, -4536, 546, -36, 1};
        int[] x10 = {0, -362880, 1026576, -1172700, 723680, -269325, 63273, -9450, 870, -45, 1};
        coeffs.put(1, x1);
        coeffs.put(2, x2);
        coeffs.put(3, x3);
        coeffs.put(4, x4);
        coeffs.put(5, x5);
        coeffs.put(6, x6);
        coeffs.put(7, x7);
        coeffs.put(8, x8);
        coeffs.put(9, x9);
        coeffs.put(10, x10);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        n = scanner.nextInt();
        m = scanner.nextInt();
        sets = new ArrayList<>();
        count = new int[n + 1];
        graph = new int[n][n];
        answer = new int[n + 1];
        coeffs = new HashMap<>();
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt() - 1;
            int to = scanner.nextInt() - 1;
            graph[from][to] = 1;
            graph[to][from] = 1;
        }
        recur(0);
        initCoeffs();
        for (int i = 1; i < count.length; i++) {
            int degree = 0;
            for (int coef : coeffs.get(i)) {
                answer[degree] += count[i] * coef;
                degree++;
            }
        }
        for (int i = 0; i < answer.length / 2; i++) {
            int tmp = answer[i];
            answer[i] = answer[answer.length - 1 - i];
            answer[answer.length - 1 - i] = tmp;
        }
        System.out.println(n);
        for (int i : answer) {
            System.out.print(i + " ");
        }
    }
}
