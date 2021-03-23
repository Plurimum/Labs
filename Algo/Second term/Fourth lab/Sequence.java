import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Sequence {
    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("sequence.in")));
        PrintWriter writer = new PrintWriter("sequence.out");
        int n = scanner.nextInt();
        int[] sequence = new int[n];
        int sum = 0;
        for (int i = 0; i < n; i++){
            sequence[i] = scanner.nextInt();
            sum += sequence[i];
        }
        ArrayList<Integer> result = new ArrayList<>();
        boolean partitionable = false;
        if (sum % 2 != 1) {
            sum = sum / 2;
            for (int i = n - 1; i >= 0; i--) {
                if (sequence[i] <= sum) {
                    sum -= sequence[i];
                    result.add(i + 1);
                }
                if (sum == 0) {
                    partitionable = true;
                    break;
                }
            }
        }
        if (!partitionable){
            writer.write("-1");
        } else {
            writer.write(String.valueOf(result.size()) + '\n');
            for (int el : result){
                writer.write(el + " ");
            }
        }
        writer.close();
    }
}
