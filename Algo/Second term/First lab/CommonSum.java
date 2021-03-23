import java.util.Scanner;

public class CommonSum {

    private static long[] sums;
    private static long sum = 0;
    private static final int mod1 = (1 << 16);
    private static final int mod2 = (1 << 30);
    private static int z, t, x, y;

    private static int getElem(int ai){
        return (x * ai + y) % mod1;
    }

    private static long getSum(int left, int right) {
        return sums[right + 1] - sums[left];
    }

    private static int getRequest(int bi){
        return (z * bi + t) % mod2;
    }

    public static void main(String[] args) {
        
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        x = scanner.nextInt();
        y = scanner.nextInt();
        int[] a = new int[n];
        a[0] = scanner.nextInt();
        sums = new long[n + 1];
        sums[0] = 0;
        sums[1] = a[0];
        for (int i = 1; i < n; i++) {
            a[i] = getElem(a[i - 1]);
            sums[i + 1] = sums[i] + a[i];
        }
        int m = scanner.nextInt();
        z = scanner.nextInt();
        t = scanner.nextInt();
        int b = scanner.nextInt();
        int[] c = new int[2 * m];
        if (m > 0){
            c[0] = (b % n);
        } else {
            System.out.println(0);
            return;
        }
        for (int i = 1; i < 2 * m; i++) {
            int bNew = getRequest(b);
            if (bNew < 0) {
                c[i] = (bNew + mod2) % n;
            } else {
                c[i] = bNew % n;
            }
            b = bNew;
        }
        for (int i = 0; i < m; i++) {
            sum += getSum(Integer.min(c[2 * i], c[2 * i + 1]), Integer.max(c[2 * i], c[2 * i + 1]));
        }

        System.out.println(sum);
    }

}