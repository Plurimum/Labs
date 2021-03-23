import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import static java.lang.Math.*;

public class RMQ2 {

    private static long neutralForSet = Long.MAX_VALUE;
    private static int size = 4 * 500_000;
    private static long t[][] = new long[size][4];

    private static void build(int v, int l, int r, int a[]) {
        t[v][2] = neutralForSet;
        if (l == r) {
            t[v][0] = a[l];
            return;
        }
        int m = l + (r - l) / 2;
        build(2 * v + 1, l, m, a);
        build(2 * v + 2, m + 1, r, a);
        t[v][0] = min(t[2 * v + 1][0], t[2 * v + 2][0]);
    }

    private static long trueValue(int v){
        if (t[v][2] != neutralForSet){
            return t[v][2];
        }
        if (t[v][3] != 0){
            return t[v][0] + t[v][3];
        }
        return t[v][0];
    }

    private static void push(int v, int l, int r)
    {
        if (t[v][2] != neutralForSet){
            t[v][0] = t[v][2];
            if (l != r){
                t[2 * v + 1][2] = t[v][2];
                t[2 * v + 2][2] = t[v][2];
                t[2 * v + 1][3] = 0;
                t[2 * v + 2][3] = 0;
            }
            t[v][2] = neutralForSet;
        }
        if (t[v][3] != 0)
        {
            t[v][0] += t[v][3];
            if (l != r){
                t[2 * v + 1][3] += t[v][3];
                t[2 * v + 2][3] += t[v][3];
            }
            t[v][3] = 0;
        }
    }

    private static long query(int v, int vl, int vr, int l, int r)
    {
        push(v, vl, vr);
        if (vl > r || vr < l)
        {
            return neutralForSet;
        }
        if (vl >= l && vr <= r)
        {
            return trueValue(v);
        }
        int vm = vl + (vr - vl) / 2;
        long ql = query(2 * v + 1, vl, vm, l, r);
        long qr = query(2 * v + 2, vm + 1, vr, l, r);
        return min(ql, qr);
    }

    private static void add(int v, int vl, int vr, int l, int r, int data)
    {
        push(v, vl, vr);
        if (vl > r || vr < l)
        {
            return;
        }
        if (vl >= l && vr <= r)
        {
            t[v][3] += data;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        add(2 * v + 1, vl, vm, l, r, data);
        add(2 * v + 2, vm + 1, vr, l, r, data);
        t[v][0] = min(t[2 * v + 1][0], t[2 * v + 2][0]);
    }

    private static void set(int v, int vl, int vr, int l, int r, int data)
    {
        push(v, vl, vr);
        if (vl > r || vr < l)
        {
            return;
        }
        if (vl >= l && vr <= r)
        {
            t[v][2] = data;
            push(v, vl, vr);
            return;
        }
        int vm = vl + (vr - vl) / 2;
        set(2 * v + 1, vl, vm, l, r, data);
        set(2 * v + 2, vm + 1, vr, l, r, data);
        t[v][0] = min(t[2 * v + 1][0], t[2 * v + 2][0]);
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = scanner.nextInt();
        int[] mas = new int[n];
        for (int i = 0; i < n; i++){
            mas[i] = scanner.nextInt();
        }
        build(0, 0, n - 1, mas);
        String s, word;
        int firstPar, secondPar, value;
        scanner.nextLine();
        while (scanner.hasNext()){
            s = scanner.nextLine();
            String[] request = s.split(" ");
            word = request[0];
            firstPar = Integer.parseInt(request[1]);
            secondPar = Integer.parseInt(request[2]);
            switch (word){
                case "set":
                    value = Integer.parseInt(request[3]);
                    set(0, 0, n - 1, firstPar - 1, secondPar - 1, value);
                    break;
                case "add":
                    value = Integer.parseInt(request[3]);
                    add(0, 0, n - 1, firstPar - 1, secondPar - 1, value);
                    break;
                case "min":
                    System.out.println(query(0, 0, n - 1, firstPar - 1, secondPar - 1));
                    break;
            }
        }
    }
}
