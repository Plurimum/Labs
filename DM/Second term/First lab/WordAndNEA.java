import java.io.*;
import java.util.*;

public class WordAndNEA {

    static class Vertex{
        boolean term;
        HashMap<Character, TreeSet<Integer>> transfer;

        Vertex(boolean value){
            this.term = value;
            transfer = new HashMap<>();
        }

        public void setTerm(boolean value){
            this.term = value;
        }
    }

    public static Vertex[] aut;

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("problem2.in")));
        PrintWriter writer = new PrintWriter("problem2.out");
        String word = scanner.nextLine();
        int n = scanner.nextInt(), m = scanner.nextInt(), k = scanner.nextInt();
        aut = new Vertex[n + 1];
        for (int i = 0; i < n + 1; i++) {
            aut[i] = new Vertex(false);
        }
        for (int i = 0; i < k; ++i) {
            int tmp = scanner.nextInt();
            if (aut[tmp] != null) aut[tmp].setTerm(true);
        }
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            char symbol = scanner.next().charAt(0);
            TreeSet<Integer> tmp = aut[from].transfer.get(symbol);
            if (tmp == null) {
                tmp = new TreeSet<>();
            }
            tmp.add(to);
            aut[from].transfer.put(symbol, tmp);
        }
        TreeSet<Integer> prev = new TreeSet<>(), cur = new TreeSet<>();
        prev.add(1);
        for (int i = 0; i < word.length(); i++){
            cur.clear();
            for (Integer j : prev){
                TreeSet<Integer> tmp = aut[j].transfer.get(word.charAt(i));
                if (tmp != null) cur.addAll(tmp);
            }
            prev.clear();
            prev.addAll(cur);
        }
        boolean accepts = false;
        for (Integer i : cur){
            if (aut[i].term){
                writer.write("Accepts");
                accepts = true;
                break;
            }
        }
        if (!accepts){
            writer.write("Rejects");
        }
        writer.close();
    }
}