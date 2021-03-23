import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Printing {

    private static final int[] a = new int[7];
    private static final int[] pows = new int[7];

    private static int greedybruhforce(int n) {
        if (n == 0) {
            return 0;
        }
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 6; i >= 0; i--){
            if (pows[i] > n){
                list.add(a[i]);
            } else {
                list.add(((n - (n % pows[i])) / pows[i]) * a[i] + greedybruhforce(n % pows[i]));
                list.add(((n - (n % pows[i])) / pows[i] + 1) * a[i]);
            }
        }
        int sum = Integer.MAX_VALUE;
        for (Integer integer : list) {
            sum = Math.min(sum, integer);
        }
        return sum;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("printing.in")));
        PrintWriter writer = new PrintWriter("printing.out");
        int n = scanner.nextInt(), pow = 1;
        for (int i = 0; i < 7; i++){
            a[i] = scanner.nextInt();
            pows[i] = pow;
            pow *= 10;
        }
        int ans = greedybruhforce(n);
        writer.write(String.valueOf(ans));
        writer.close();
    }
}
