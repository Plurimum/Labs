import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class IsomorphismDFA {

    public static class Vertex {
        boolean term;
        HashMap<Character, Integer> transfer;
        TreeSet<Character> symbols;
        Vertex(boolean term) {
            this.term = term;
            this.transfer = new HashMap<>();
            this.symbols = new TreeSet<>();
        }

        void setTermTrue(){
            term = true;
        }
    }

    public static int n;
    public static int MAX = 100000;
    public static ArrayList<Vertex> firstAut, secondAut;
    public static ArrayList<Boolean> visited = new ArrayList<>();

    private static void initAut(Scanner scanner, int m, int k, ArrayList<Vertex> aut) {
        for (int i = 0; i < n + 1; i++){
            aut.add(new Vertex(false));
        }
        for (int i = 0; i < k; i++){
            aut.get(scanner.nextInt()).setTermTrue();
        }
        for (int i = 0; i < m; i++){
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            char symbol = scanner.next().charAt(0);
            aut.get(from).transfer.put(symbol, to);
            aut.get(from).symbols.add(symbol);
        }
    }

    private static boolean dfs(int first, int second){
        visited.set(first, true);
        if (firstAut.get(first).term != secondAut.get(second).term){
            return false;
        }
        boolean isomorphism = true;
        for (Character symbol: firstAut.get(first).symbols){
            int firstNext = firstAut.get(first).transfer.get(symbol);
            int secondNext = secondAut.get(second).transfer.get(symbol) != null ?
                    secondAut.get(second).transfer.get(symbol) : -1;
            if (secondNext == -1){
                return false;
            }
            if (!visited.get(firstNext)){
                isomorphism = isomorphism && dfs(firstNext, secondNext);
            }
        }
        return isomorphism;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("isomorphism.in")));
        PrintWriter writer = new PrintWriter("isomorphism.out");
        n = scanner.nextInt();
        int m = scanner.nextInt(), k = scanner.nextInt();
        firstAut = new ArrayList<>();
        secondAut = new ArrayList<>();
        for (int i = 0; i < MAX + 1; i++){
            visited.add(false);
        }
        initAut(scanner, m, k, firstAut);
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        initAut(scanner, m, k, secondAut);
        if (dfs(1, 1)){
            writer.write("YES");
        } else {
            writer.write("NO");
        }
        writer.close();
    }
}
