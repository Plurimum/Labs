import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class RSQ {

    private static long t[] = new long[4 * 500_000];

    private static void build(int v, int l, int r, int a[]){
        if (l == r){
            t[v] = a[l];
            return;
        }
        int m = l + (r - l) / 2;
        build(2 * v + 1, l, m, a);
        build(2 * v + 2, m + 1, r, a);
        t[v] = t[2 * v + 1] + t[2 * v + 2];
    }

    private static long query(int v, int vl, int vr, int l, int r){
        if (r < vl || vr < l){
            return 0;
        }
        if (l <= vl && vr <= r){
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        long ql = query(2 * v + 1, vl, vm, l, r);
        long qr = query(2 * v + 2, vm + 1, vr, l , r);
        return ql + qr;
    }

    private static void update(int v, int vl, int vr, int pos, int data){
        if (vl == vr){
            t[v] = data;
            return;
        } else {
            int vm = vl + (vr - vl) / 2;
            if (pos <= vm){
                update(2 * v + 1, vl, vm, pos, data);
            } else{
                update(2 * v + 2, vm + 1, vr, pos, data);
            }
            t[v] = t[2 * v + 1] + t[2 * v + 2];
        }
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
        scanner.nextLine();
        int firstPar, secondPar;
        while (scanner.hasNext()){
            s = scanner.nextLine();
            String[] requests = s.split(" ");
            word = requests[0];
            firstPar = Integer.parseInt(requests[1]);
            secondPar = Integer.parseInt(requests[2]);
            if (word.equals("set")){
                update(0, 0, n - 1, firstPar - 1, secondPar);
            } else {
                System.out.println(query(0, 0, n - 1,
                        firstPar - 1, secondPar - 1));
            }
        }
    }
}
