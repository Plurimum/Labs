import java.io.*;
import java.util.*;

public class CountOfWordsWithLengthLInNEA {

    private static final ArrayList<ArrayList<ArrayList<Integer>>> transfers = new ArrayList<>();
    private static final LinkedList<BitSet> queue = new LinkedList<>();
    private static final HashMap<BitSet, Integer> number = new HashMap<>();
    private static final ArrayList<Boolean> termN = new ArrayList<>();
    private static final ArrayList<Integer> from = new ArrayList<>();
    private static final ArrayList<Integer> to = new ArrayList<>();
    private static boolean[] term;
    private static int n, q, l;
    private static int[] answer;

    private static void dfs() {
        while (!queue.isEmpty()) {
            BitSet curr = queue.peek();
            queue.remove();
            int i = -1;
            while (++i < 26) {
                BitSet can = new BitSet(n + 1);
                boolean terminal = false;
                int j = 0;
                while (++j <= n) {
                    if (curr.get(j)) {
                        int curSize = transfers.get(j).get(i).size();
                        for (int k = 0; k < curSize; k++) {
                            int v = transfers.get(j).get(i).get(k);
                            can.set(v);
                            if (term[v]) {
                                terminal = true;
                            }
                        }
                    }
                }
                from.add(number.get(curr));
                if (!number.containsKey(can)) {
                    q++;
                    number.put(can, q);
                    termN.add(terminal);
                    queue.add(can);
                }
                to.add(number.get(can));
            }
        }
    }

    private static void computeAnswer() {
        for (int i = 0; i < l; i++) {
            int[] result = new int[q + 1];
            for (int j = 0; j < from.size(); j++) {
                result[from.get(j)] = (result[from.get(j)] + answer[to.get(j)]) % 1000000007;
            }
            System.arraycopy(result, 0, answer, 0, q + 1);
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("problem5.in")));
        PrintWriter writer = new PrintWriter("problem5.out");
        n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        l = scanner.nextInt();
        term = new boolean[n + 1];
        BitSet set = new BitSet(n + 1);
        q = 1;
        for (int i = 0; i < n + 1; i++) {
            transfers.add(new ArrayList<>());

            for (int j = 0; j < 26; j++) {
                transfers.get(i).add(new ArrayList<>());
            }
        }
        for (int i = 0; i < k; i++) {
            term[scanner.nextInt()] = true;
        }
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            int symbol = (int) scanner.next().charAt(0) - (int) 'a';
            ArrayList<Integer> tmp = transfers.get(from).get(symbol);
            tmp.add(to);
            transfers.get(from).remove(symbol);
            transfers.get(from).add(symbol, tmp);
        }
        termN.add(false);
        termN.add(term[1]);
        set.set(1);
        number.put(set, 1);
        queue.add(set);
        dfs();
        answer = new int[q + 1];
        for (int i = 1; i < q + 1; i++) {
            if (termN.get(i)) {
                answer[i] = 1;
            }
        }
        computeAnswer();
        writer.print(answer[number.get(set)]);
        writer.close();
    }
}