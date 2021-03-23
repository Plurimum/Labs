import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Painter {

    private static int t[][] = new int[8 * 500_000][6];

    /*
    0 is value
    1 is hasSegmentLeft
    2 is hasSegmentRight
    3 is count
    4 is length
    5 is newValue
     */

    private static void build() {
        for (int i = 0; i < 2 * 500_000; i++){
            t[0][1] = 0;
            t[0][2] = 0;
            t[0][3] = 0;
            t[0][4] = 0;
            t[0][5] = -1;
        }
        /*if (l == r) {
            t[v][1] = 0;
            t[v][2] = 0;
            t[v][3] = 0;
            t[v][4] = 0;
            t[v][5] = -1;
            return;
        }
        int m = l + (r - l) / 2;
        build(leftChild(v), l, m);
        build(rightChild(v), m + 1, r);*/
    }

    private static int trueSum(int v, int l, int r) {
        if (t[v][5] == -1) {
            return t[v][4];
        } else {
            return (r - l + 1) * t[v][5];
        }
    }

    private static int[] trueValue(int v, int l, int r) {
        int[] tV = new int[2];
        if (t[v][5] == -1) {
            tV[0] = t[v][3];
            tV[1] = t[v][4];
        } else {
            tV[0] = t[v][5];
            tV[1] = (r - l + 1) * t[v][5];
        }
        return tV;
    }

    private static void printTree(int v, String indent, int level) {
        if (rightChild(v) <= 20) {
            printTree(leftChild(v), indent + "          ", level + 1);
            System.out.println(indent + "[" + t[v][3] + " " + t[v][4] + "]");
            printTree(rightChild(v), indent + "          ", level + 1);
        }
    }

    private static int leftChild(int v) {
        return 2 * v + 1;
    }

    private static int rightChild(int v) {
        return 2 * v + 2;
    }

    private static void merge(int v) {
        t[v][1] = t[leftChild(v)][1];
        t[v][2] = t[rightChild(v)][2];
        t[v][3] = t[leftChild(v)][3] + t[rightChild(v)][3];
        if ((t[leftChild(v)][2] == 1) && (t[rightChild(v)][1] == 1)) {
            t[v][3]--;
        }
        t[v][4] = t[leftChild(v)][4] + t[rightChild(v)][4];
    }

    private static int[] query(int v, int vl, int vr, int l, int r) {
        push(v, vl, vr);
        if (vl > r || vr < l) {
            int[] result = new int[6];
            result[5] = -1;
            return result;
        }
        if (vl >= l && vr <= r) {
            //push(v, vl, vr);
            //System.out.println("get out answer at v = " + v);
            //return trueValue(v, vl, vr);
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        query(leftChild(v), vl, vm, l, r);
        query(rightChild(v), vm + 1, vr, l, r);
        merge(v);
        //System.out.println("get out answer at v = " + v);
        return t[v];
    }

    private static void push(int v, int l, int r) {
        int data = t[v][5];
        if (data != -1) {
            t[v][1] = data;
            t[v][2] = data;
            t[v][3] = data;
            t[v][4] = (r - l + 1) * data;
            if (l != r) {
                t[leftChild(v)][5] = data;
                t[rightChild(v)][5] = data;
            }
            t[v][5] = -1;
        }
    }

    private static void set(int v, int vl, int vr, int l, int r, int data) {
        push(v, vl, vr);
        if (vl > r || vr < l) {
            return;
        }
        if (vl >= l && vr <= r) {
            t[v][5] = data;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        set(leftChild(v), vl, vm, l, r, data);
        set(rightChild(v), vm + 1, vr, l, r, data);
        merge(v);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = scanner.nextInt();
        build();
        String s, word;
        int firstPar, secondPar;
        scanner.nextLine();
        for (int i = 0; i < n; i++){
            s = scanner.nextLine();
            String[] request = s.split(" ");
            word = request[0];
            firstPar = Integer.parseInt(request[1]) + 500_000;
            secondPar = Integer.parseInt(request[2]);
            switch (word) {
                case "W":
                    set(0, 0, 2 * 500_000 - 1, firstPar, firstPar + secondPar - 1, 0);
                    break;
                case "B":
                    set(0, 0, 2 * 500_000 - 1, firstPar, firstPar + secondPar - 1, 1);
                    break;
            }
            int[] res = query(0, 0, 2 * 500_000 - 1, 0, 2 * 500_000 - 1);
            System.out.println(res[3] + " " + res[4]);
        }
    }
}
