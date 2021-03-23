import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Request {

    public static class Triple {
        int start, end, duration;

        Triple(int start, int end) {
            this.start = start;
            this.end = end;
            this.duration = end - start;
        }
    }

    public static Comparator<Triple> comparator = new Comparator<Triple>() {
        @Override
        public int compare(Triple o1, Triple o2) {
            return Integer.compare(o1.end, o2.end);
        }
    };

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("request.in")));
        PrintWriter writer = new PrintWriter("request.out");
        int n;
        n = scanner.nextInt();
        Triple[] pairs = new Triple[n];
        for (int i = 0; i < n; i++) {
            int a, b;
            a = scanner.nextInt();
            b = scanner.nextInt();
            Triple tmp = new Triple(a, b);
            pairs[i] = tmp;
        }
        int count = 0;
        Arrays.sort(pairs, comparator);
        /*for (Triple triple : pairs){
            writer.write(triple.start + " " + triple.end + '\n');
        }*/
        for (int i = 0; i < n; i++) {
            if (pairs[i] != null) {
                int current = pairs[i].end;
                count++;
                for (int j = i + 1; j < n; j++) {
                    if (pairs[j] != null) {
                        if (pairs[j].end == current || pairs[j].start < current) {
                            pairs[j] = null;
                        }
                    }
                }
            }
        }
        writer.write(String.valueOf(count));
        writer.close();
    }
}
