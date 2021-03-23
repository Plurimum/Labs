import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Jurassic {

    private static final ArrayList<Integer> list = new ArrayList<>();
    private static int n;

    private static void init(Scanner scanner){
        for (int i = 0; i < n; i++){
            String str;
            str = scanner.nextLine();
            list.add(0);
            for (int j = 0; j < str.length(); j++){
                list.set(i, list.get(i) + (1 << (str.charAt(j) - 'A')));
            }
        }
    }

    private static int bitMaskOfBones(){
        int result = -1;
        for (int i = 0; i < (1 << n); i++){
            int mask = 0;
            for (int j = 1, count = 0; j < (1 << n); ++count, j = (1 << count)){
                if ((j & i) == 0) {
                    continue;
                }
                mask = mask ^ list.get(count);
            }
            if (!(i <= result || mask != 0)){
                result = i;
            }
        }
        return result;
    }

    private static int countBits(int mask){
        int result = 0;
        for (int j = 1, count = 0; j < (1 << n); ++count, j = (1 << count))
        {
            if ((j & mask) != 0)
            {
                result++;
            }
        }
        return result;
    }

    public static void writeBones(PrintWriter writer, int bitAnswer){
        for (int j = 1, count = 0; j < (1 << n); ++count, j = (1 << count))
        {
            if ((j & bitAnswer) != 0)
            {
                writer.write((count + 1) + " ");
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("jurassic.in")));
        PrintWriter writer = new PrintWriter("jurassic.out");
        n = Integer.parseInt(scanner.nextLine());
        init(scanner);
        int bitAnswer = bitMaskOfBones();
        System.out.println(Integer.toBinaryString(bitAnswer));
        int ans = countBits(bitAnswer);
        writer.write(ans + " \n");
        writeBones(writer, bitAnswer);
        writer.close();
    }
}
