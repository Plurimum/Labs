import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class WordAndDEA {

    static class Vertex{
        boolean term;
        Map<Character, Integer> transfer;
        Vertex(){
            this.term = false;
            transfer = new HashMap<>();
        }

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

        Scanner scanner = new Scanner(new BufferedReader(new FileReader("problem1.in")));
        PrintWriter writer = new PrintWriter("problem1.out");
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
        for (int i = 0; i < m; ++i) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            char symbol = scanner.next().charAt(0);
            if (aut[from] != null) aut[from].transfer.put(symbol, to);
        }
        int cur = 1;
        boolean rejects = false;
        for (int i = 0; i < word.length(); ++i){
            if (!aut[cur].transfer.containsKey(word.charAt(i))){
                rejects = true;
                break;
            }
            cur = aut[cur].transfer.get(word.charAt(i));
        }
        if (rejects){
            writer.write("Rejects");
        } else {
            if (aut[cur].term){
                writer.write("Accepts");
            } else {
                writer.write("Rejects");
            }
        }
        writer.close();
    }
}