import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class PruferTreeRecovering {

    private static int n;
    private static ArrayList<Integer> pruferCode;

    private static void pruferDecode() {
        ArrayList<Integer> degree = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            degree.add(1);
        }
        for (int i = 0; i < n - 2; i++) {
            degree.set(pruferCode.get(i), degree.get(pruferCode.get(i)) + 1);
        }
        TreeSet<Integer> leaves = new TreeSet<>();
        for (int i = 0; i < n; i++) {
            if (degree.get(i) == 1) {
                leaves.add(i);
            }
        }
        for (int i = 0; i < n - 2; i++) {
            int leaf = leaves.stream().iterator().next();
            leaves.remove(leaf);
            int v = pruferCode.get(i);
            System.out.println((leaf + 1) + " " + (v + 1));
            degree.set(v, degree.get(v) - 1);
            if (degree.get(v) == 1) {
                leaves.add(v);
            }
        }
        System.out.println((leaves.stream().iterator().next() + 1) + " " + (leaves.last() + 1));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        n = scanner.nextInt();
        pruferCode = new ArrayList<>();
        for (int i = 0; i < n - 2; i++) {
            pruferCode.add(scanner.nextInt() - 1);
        }
        pruferDecode();
    }
}
