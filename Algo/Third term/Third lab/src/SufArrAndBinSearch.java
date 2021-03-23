import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class SufArrAndBinSearch {

    public static class Suffix implements Comparable<Suffix> {
        int index;
        int rank;
        int next;

        public Suffix(int ind, int r, int nr) {
            index = ind;
            rank = r;
            next = nr;
        }

        public int compareTo(Suffix s) {
            if (rank != s.rank) return Integer.compare(rank, s.rank);
            return Integer.compare(next, s.next);
        }
    }

    public static int[] suffixArray(String s) {
        int n = s.length();
        Suffix[] su = new Suffix[n];

        for (int i = 0; i < n; i++) {
            su[i] = new Suffix(i, s.charAt(i) - '$', 0);
        }
        for (int i = 0; i < n; i++) {
            su[i].next = (i + 1 < n ? su[i + 1].rank : -1);
        }
        Arrays.sort(su);
        int[] ind = new int[n];
        for (int length = 4; length < 2 * n; length <<= 1) {
            int rank = 0, prev = su[0].rank;
            su[0].rank = rank;
            ind[su[0].index] = 0;
            for (int i = 1; i < n; i++) {
                if (su[i].rank == prev && su[i].next == su[i - 1].next) {
                    prev = su[i].rank;
                    su[i].rank = rank;
                } else {
                    prev = su[i].rank;
                    su[i].rank = ++rank;
                }
                ind[su[i].index] = i;
            }
            for (int i = 0; i < n; i++) {
                int nextP = su[i].index + length / 2;
                su[i].next = nextP < n ? su[ind[nextP]].rank : -1;
            }
            Arrays.sort(su);
        }
        int[] suf = new int[n];
        for (int i = 0; i < n; i++) {
            suf[i] = su[i].index;
        }
        return suf;
    }

    private static int binarySearch(String t, int[] sufArrT, String key) {
        int left = -1;
        int right = sufArrT.length;
        int middle = left;
        while (left < right - 1) {
            middle = (left + right) / 2;
            if (t.substring(sufArrT[middle]).compareTo(key) < 0) {
                left = middle;
            } else {
                right = middle;
            }
        }
        if (right == t.length()) {
            right--;
        }
        return right;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter writer = new PrintWriter(System.out);
        int n = Integer.parseInt(scanner.nextLine());
        String[] strings = new String[n];
        for (int i = 0; i < n; i++) {
            strings[i] = scanner.nextLine();
        }
        String t = scanner.nextLine();
        int[] sufArrT = suffixArray(t);
        for (String subString : strings) {
            String leastKey = t.substring(sufArrT[binarySearch(t, sufArrT, subString)]);
            if (leastKey.contains(subString)) {
                writer.write("YES\n");
            } else {
                writer.write("NO\n");
            }
        }
        writer.close();
    }
}
