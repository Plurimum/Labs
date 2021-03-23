import java.util.Scanner;

public class Stars {
    private static int[][][] t;

    private static int sum(int x, int y, int z) {
        int result = 0;
        int i = x, j = y, k = z;
        while (i >= 0){
            while (j >= 0){
                while (k >= 0){
                    result += t[i][j][k];
                    k = (k & (k + 1)) - 1;
                }
                k = z;
                j = (j & (j + 1)) - 1;
            }
            j = y;
            i = (i & (i + 1)) - 1;
        }
        return result;
    }

    private static void modify(int n, int x, int y, int z, long value) {
        int i = x, j = y, k = z;
        while (i < n) {
            while (j < n) {
                while (k < n) {
                    t[i][j][k] += value;
                    k = k | (k + 1);
                }
                k = z;
                j = j | (j + 1);
            }
            j = y;
            i = i | (i + 1);
        }
    }

    private static int getCube(int x1, int y1, int z1, int x2, int y2, int z2) {
        int total = sum(x2, y2, z2);
        int unnecessaryCube = sum(x1 - 1, y1 - 1, z1 - 1);
        int unnecessaryBricks =
                sum(x2, y2, z1 - 1) +
                        sum(x2, y1 - 1, z2) +
                        sum(x1 - 1, y2, z2);
        int extraSubtracted =
                sum(x1 - 1, y1 - 1, z2) +
                        sum(x1 - 1, y2, z1 - 1) +
                        sum(x2, y1 - 1, z1 - 1);
        return total - unnecessaryCube - unnecessaryBricks + extraSubtracted;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        t = new int[n][n][n];
        int m;
        int x, y, z, k;
        int x1, y1, z1, x2, y2, z2;
        for (int i = 0; i < 100003; i++) {
            m = scanner.nextInt();
            switch (m) {
                case 1:
                    x = scanner.nextInt();
                    y = scanner.nextInt();
                    z = scanner.nextInt();
                    k = scanner.nextInt();
                    modify(n, x, y, z, k);
                    break;
                case 2:
                    x1 = scanner.nextInt();
                    y1 = scanner.nextInt();
                    z1 = scanner.nextInt();
                    x2 = scanner.nextInt();
                    y2 = scanner.nextInt();
                    z2 = scanner.nextInt();
                    int result = getCube(x1, y1, z1, x2, y2, z2);
                    System.out.println(result);
                    break;
                case 3:
                    return;
            }
        }
    }
}