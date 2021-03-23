import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Beautiful {

    private static int[] countOfDivisorsOf;
    private static boolean[] used;
    private static int mod = 0;
    private static int count = 0;
    private static int n;

    private static void countBeautiful(int[] permutation, boolean[] used, int place, int alreadySet) {
        if (alreadySet == n) {
            int worth = getWorth(permutation);
            //System.out.println("permutation is " + Arrays.toString(permutation));
            if (worth == 0 || countOfDivisorsOf[worth] % 3 == 0) {
                count++;
            }
            return;
        }
        alreadySet++;
        for (int i = 0; i < used.length; i++) {
            if (!used[i]) {
                permutation[place] = i;
                used[i] = true;
                //System.out.println(Arrays.toString(permutation));
                countBeautiful(permutation, used,place + 1, alreadySet);
                used[i] = false;
            }
        }
    }

    private static int getWorth(int[] numbers){
        int worth = 0;
        for (int j = 0; j < numbers.length - 1; j++) {
            worth += (numbers[j] + 1) * (numbers[j + 1] + 1);
        }
        worth %= mod;
        return worth;
    }

    private static int countDivisors(int n) {
        if (n < 2) {
            return 1;
        }
        int count = 0;
        int sqrt = (int) Math.sqrt(n);
        for (int i = 1; i <= sqrt; i++) {
            if (n % i == 0) {
                count++;
                if (i * i == n) {
                    continue;
                }
                count++;
            }
        }
        return count;
    }

    private static void init(){
        used = new boolean[n];
        countOfDivisorsOf = new int[mod];
        for (int i = 0; i < mod; i++) {
            countOfDivisorsOf[i] = countDivisors(i);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("beautiful.in")));
        PrintWriter writer = new PrintWriter("beautiful.out");
        n = scanner.nextInt();
        mod = scanner.nextInt();
        init();
        countBeautiful(new int[n], used, 0, 0);
        writer.write(String.valueOf(count));
        writer.close();
    }
}