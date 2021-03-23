import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Sqroot {

    public static int[][] a = new int[4][4];
    public static int[][] b = new int[4][4];
    public static int mask = 0b0000000000000000;

    public static int[][] getNewMatrix(){
        String curMask = String.format("%16s", Integer.toBinaryString(mask)).replace(' ', '0');
        int[][] result = new int[4][4];
        int k = 15;
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 4; j++){
                result[i][j] = curMask.charAt(k) - 48;
                k--;
            }
        }
        return result;
    }

    private static boolean check() {
        int[][] c = new int[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int t = 0; t < 4; t++) {
                    c[i][j] += b[i][t] * b[t][j];
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                c[i][j] %= 2;
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (c[i][j] != a[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("sqroot.in")));
        PrintWriter writer = new PrintWriter("sqroot.out");
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                a[i][j] = scanner.nextInt();
            }
        }
        boolean flag = false;
        int maxMask = 65536;
        for (int i = 0; i < maxMask; i++){
            b = getNewMatrix();
            if (check()){
                flag = true;
                break;
            }
            mask++;
        }
        System.out.println(String.format("%16s", Integer.toBinaryString(mask)).replace(' ', '0'));
        if (flag) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    writer.write(b[i][j] + " ");
                }
                writer.write('\n');
            }
        } else {
            writer.write("NO SOLUTION");
        }
        writer.close();
    }
}
