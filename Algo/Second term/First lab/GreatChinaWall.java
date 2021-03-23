import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.IllegalCharsetNameException;
import java.util.Scanner;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class GreatChinaWall {

    private static int t[][];
    private static final int NEUTRAL = Integer.MAX_VALUE;


    private static int leftChild(int v) {
        return 2 * v + 1;
    }

    private static int rightChild(int v) {
        return 2 * v + 2;
    }

    /*
    0 - minHeight
    1 - indexOfMin
    2 - flag
     */

    private static int[] initVertex(int minHeight,int indexOfMin, int flag){
        int[] result = new int[3];
        result[0] = minHeight;
        result[1] = indexOfMin;
        result[2] = flag;
        return result;
    }

    private static int[] merge(int[] first, int[] second){
        int index;
        if (first[0] >= second[0]) {
            index = second[1];
        } else {
            index = first[1];
        }
        return initVertex(min(first[0], second[0]), index, NEUTRAL);
    }

    private static void build(int v, int l, int r){
        if (l == r){
            t[v][0] = 0;
            t[v][1] = l;
            return;
        }
        int m = l + (r - l) / 2;
        build(leftChild(v), l, m);
        build(rightChild(v), m + 1, r);
        t[v][0] = 0;
        t[v][1] = t[leftChild(v)][1];
        //t[v] = merge(t[leftChild(v)], t[rightChild(v)]);
    }

    private static int[] query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (vr < l || r < vl) {
            return initVertex(NEUTRAL, -1, NEUTRAL);
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        int[] ql = query(leftChild(v), vl, vm, l, r);
        int[] qr = query(rightChild(v), vm + 1, vr, l, r);
        return merge(ql, qr);
    }

    private static void push(int v, int vl, int vr) {
        if (t[v][2] != NEUTRAL) {
            if (t[v][0] < t[v][2]) {
                int flag = t[v][2];
                t[v][0] = flag;
                if (vl != vr) {
                    t[leftChild(v)][2] = flag;
                    t[rightChild(v)][2] = flag;
                }
            }
            t[v][2] = NEUTRAL;
        }
    }

    private static void set(int v, int vl, int vr, int l, int r, int data) {
        push(v, vl, vr);
        if (r < vl || vr < l) {
            return;
        }
        if (l <= vl && vr <= r) {
            t[v][2] = data;
            push(v, vl, vr);
            return;
        }
        int vm = (vl + (vr - vl) / 2);
        set(leftChild(v), vl, vm, l, r, data);
        set(rightChild(v), vm + 1, vr, l, r, data);
        t[v] = merge(t[leftChild(v)], t[rightChild(v)]);
    }

    private static void printTree(int v, String indent, int n) {
        if (rightChild(v) < 4 * n) {
            printTree(leftChild(v), indent + "       ", n);
            System.out.println(indent + "[" + t[v][0] + " " + t[v][1] + " " + t[v][2] + "]");
            printTree(rightChild(v), indent + "       ", n);
        }
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n, m;
        n = scanner.nextInt();
        m = scanner.nextInt();
        t = new int[4 * n][3];
        build(0, 0, n - 1);
        for (int i = 0; i < t.length; i++){
            t[i][2] = NEUTRAL;
        }
        String word;
        //printTree(0, "", n);
        //System.out.println("\n===================\n");
        for (int i = 0; i < m; i++){
            word = scanner.next();
            switch (word){
                case "defend":
                    int a, b, c;
                    a = scanner.nextInt() - 1;
                    b = scanner.nextInt() - 1;
                    c = scanner.nextInt();
                    set(0, 0, n - 1, a, b, c);
                    //printTree(0, "", n);
                    //System.out.println("\n===================\n");
                    break;
                case "attack":
                    int d, e;
                    d = scanner.nextInt() - 1;
                    e = scanner.nextInt() - 1;
                    int[] answer = query(0, 0, n - 1, d, e);
                    //printTree(0, "", n);
                    //System.out.println("\n===================\n");
                    System.out.println(answer[0] + " " + (answer[1] + 1));
                    break;
            }
        }
    }
}
