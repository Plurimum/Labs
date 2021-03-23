import java.io.*;
import java.util.*;

public class FairyTale {

    private static class Pair {
        int first;
        int second;

        Pair(int first, int second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public int hashCode() {
            return (int) (((long) ((second + 13) * (first + 17))) % 2_147_483_647 % 1_760_777);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Pair pair = (Pair) obj;
            return first == pair.first && second == pair.second;
        }
    }

    private static int[] lamps;
    private static int[] tmp;
    private static int N;
    private static Set<Pair> comparisons;
    private static Scanner scanner;

    private static void mergeSort(int[] a, int n) {
        if (n < 2) {
            return;
        }
        int mid = n / 2;
        int[] l = new int[mid];
        int[] r = new int[n - mid];
        for (int i = 0; i < mid; i++) {
            l[i] = a[i];
        }
        for (int i = mid; i < n; i++) {
            r[i - mid] = a[i];
        }
        mergeSort(l, mid);
        mergeSort(r, n - mid);
        merge(a, l, r, mid, n - mid);
    }

    private static void merge(int[] a, int[] l, int[] r, int left, int right) {
        int i = 0, j = 0, k = 0;
        while (i < left && j < right) {
            if (request(l[i], r[j])) {
                a[k++] = l[i++];
            } else {
                a[k++] = r[j++];
            }
        }
        while (i < left) {
            a[k++] = l[i++];
        }
        while (j < right) {
            a[k++] = r[j++];
        }
    }

    private static boolean request(int first, int second) {
        if (comparisons.contains(new Pair(first, second))) {
            return true;
        }
        if (comparisons.contains(new Pair(second, first))) {
            return false;
        }
        System.out.println("1 " + first + " " + second);
        System.out.flush();
        String answer = scanner.nextLine();
        if (answer.equals("YES")) {
            comparisons.add(new Pair(first, second));
            return true;
        } else if (answer.equals("NO")) {
            comparisons.add(new Pair(second, first));
            return false;
        }
        return false;
    }

    public static void main(String[] args) {
        scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
        N = Integer.parseInt(scanner.nextLine());
        comparisons = new HashSet<>();
        lamps = new int[N];
        tmp = new int[N];
        for (int i = 0; i < N; i++) {
            lamps[i] = i + 1;
        }
        mergeSort(lamps, N);
        boolean possible = true;
        for (int i = 1; i < N; i++) {
            if (!(request(lamps[i - 1], lamps[i]))) {
                possible = false;
                break;
            }
        }
        if (possible) {
            System.out.print("0 ");
            for (int i : lamps) {
                System.out.print(i + " ");
            }
        } else {
            for (int i = 0; i < N + 1; i++) {
                System.out.print("0 ");
            }
        }
    }
}
