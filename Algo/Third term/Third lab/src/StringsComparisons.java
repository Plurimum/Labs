import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class StringsComparisons {

    private static int m;
    private static String string;
    private static final int p = 31;
    private static final long M = 1760777L;
    private static ArrayList<Long> hashes;
    private static ArrayList<Long> pows;

    private static void computePowsAndHashes() {
        pows.add(Long.parseLong("1"));
        hashes.add(Long.parseLong(String.valueOf(string.charAt(0) - 'a' + 1)));
        for (int i = 1; i < string.length(); i++) {
            hashes.add(((hashes.get(i - 1) * p) % M + string.charAt(i) - 'a' + 1) % M);
            pows.add(pows.get(i - 1) * p % M);
        }
    }

    private static Long getHash(int l, int r) {
        if (l == 0) {
            return hashes.get(r);
        }
        return (hashes.get(r) + 2 * M - ((hashes.get(l - 1) * pows.get(r - l + 1)) % M)) % M;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        string = reader.readLine();
        hashes = new ArrayList<>();
        pows = new ArrayList<>();
        m = Integer.parseInt(reader.readLine());
        computePowsAndHashes();
        for (int i = 0; i < m; i++) {
            String[] request = reader.readLine().split(" ");
            int a, b, c, d;
            a = Integer.parseInt(request[0]) - 1;
            b = Integer.parseInt(request[1]) - 1;
            c = Integer.parseInt(request[2]) - 1;
            d = Integer.parseInt(request[3]) - 1;
            Long hash1 = getHash(a, b);
            Long hash2 = getHash(c, d);
            if (hash1.equals(hash2)) {
                writer.write("Yes\n");
            } else {
                writer.write("No\n");
            }
        }
        reader.close();
        writer.close();
    }
}
