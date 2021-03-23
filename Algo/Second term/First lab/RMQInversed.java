import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class RMQInversed {
    private static int[][] t;

    private static int leftChild(int v){
        return 2 * v + 1;
    }

    private static int rightChild(int v){
        return 2 * v + 2;
    }

    private static void build(int[][] queries, int n, int m) {
        Arrays.sort(queries, RMQInversed::comparator);
        for (int i = 0; i < m; i++){
            set(0, 0, n - 1, queries[i][0], queries[i][1], queries[i][2]);
        }
    }

    private static int query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return Integer.MAX_VALUE;
        }
        if (l <= vl && vr <= r) {
            return t[v][0];
        }
        int vm = vl + (vr - vl) / 2;
        int ql = query(leftChild(v), vl, vm, l, r);
        int qr = query(rightChild(v), vm + 1, vr, l, r);
        return Math.min(ql, qr);
    }

    private static void push(int v, int vl, int vr) {
        if (t[v][1] != Integer.MAX_VALUE) {
            t[v][0] = t[v][1];
            if (vl != vr) {
                t[leftChild(v)][1] = t[v][1];
                t[rightChild(v)][1] = t[v][1];
            }
            t[v][1] = Integer.MAX_VALUE;
        }
    }

    private static void set(int v, int vl, int vr, int l, int r, int data) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && vr <= r) {
            t[v][1] = data;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        set(leftChild(v), vl, vm, l, r, data);
        set(rightChild(v), vm + 1, vr, l, r, data);
        t[v][0] = Math.min(t[2 * v + 1][0], t[2 * v + 2][0]);
    }

    private static int comparator(int[] a, int[] b){
        return Integer.compare(a[2], b[2]);
    }

    private static void check(int[][] queries, int n, int m, FileWriter writer) throws IOException {
        for (int i = 0; i < m; i++){
            if (queries[i][2] != query(0,0, n - 1, queries[i][0], queries[i][1])){
                writer.write("inconsistent");
                writer.close();
                return;
            }
        }
        writer.write("consistent\n");
        for (int i = 0; i < n; i++){
            writer.write(query(0, 0, n - 1, i, i) + " ");
        }
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("rmq.in")));
        FileWriter writer = new FileWriter("rmq.out");
        int n = scanner.nextInt();
        int m = scanner.nextInt();t = new int[4 * n][2];
        for (int i = 0; i < 4 * n; i++) {
            t[i][0] = Integer.MAX_VALUE;
            t[i][1] = Integer.MAX_VALUE;
        }
        int[][] queries = new int[m][3];
        for (int i = 0; i < m; i++){
            queries[i][0] = scanner.nextInt() - 1;
            queries[i][1] = scanner.nextInt() - 1;
            queries[i][2] = scanner.nextInt();
        }
        build(queries, n, m);
        check(queries, n, m, writer);
    }
}
