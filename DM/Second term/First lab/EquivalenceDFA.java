import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class EquivalenceDFA {

    public static class Vertex {
        boolean term;
        HashMap<Character, Integer> transfer;
        int[] transitions;
        TreeSet<Character> symbols;
        Vertex(boolean term) {
            this.term = term;
            this.transfer = new HashMap<>();
            this.symbols = new TreeSet<>();
            transitions = new int[26];
        }

        void setTermTrue(){
            term = true;
        }
    }

    public static class Pair {
        int first;
        int second;
        Pair(int first, int second){
            this.first = first;
            this.second = second;
        }
    }

    public static int n;
    public static int MAX = 100000;
    public static ArrayList<Vertex> firstAut, secondAut;
    public static ArrayList<ArrayList<Boolean>> visited = new ArrayList<>();
    public static LinkedList<Pair> queue = new LinkedList<>();

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
            aut.get(from).transitions[(symbol - 'a')] = to;
        }
    }

    private static boolean bfs(){
        queue.add(new Pair(1, 1));
        HashSet<Pair> states = new HashSet<>();
        while (!queue.isEmpty()){
            int u = queue.peek().first;
            int v = queue.peek().second;
            queue.poll();
            if (firstAut.get(u).term != secondAut.get(v).term){
                return false;
            }

            visited.get(u).set(v, true);
            for (int i = 0; i < 26; i++){
                char symbol = 'a';
                symbol += i;
                int firstNext = firstAut.get(u).transfer.get(symbol) != null ?
                        firstAut.get(u).transfer.get(symbol) : -1;
                int secondNext = secondAut.get(v).transfer.get(symbol) != null ?
                        secondAut.get(v).transfer.get(symbol) : -1;
                int firstTransit = firstAut.get(u).transitions[i];
                int secondTransit = secondAut.get(v).transitions[i];
                if (!visited.get(firstTransit).get(secondTransit)){
                    queue.add(new Pair(firstTransit, secondTransit));
//                Pair nextPair = new Pair(firstNext, secondNext);
//                if (!states.contains(nextPair)){
//                    states.add(nextPair);
//                    queue.addLast(nextPair);
//                    queue.add(nextPair);
                }
//                if (firstNext != -1 && secondNext != -1){
//                    if (!visited.get(firstNext).get(secondNext)){
//                        queue.add(new Pair(firstNext, secondNext));
//                    }
//                } else {
//                    if ((firstNext == -1) != (secondNext == -1)){
//                        if (firstNext != -1 || secondNext != -1){
//                            return false;
//                        }
//                    }
//                }
            }
        }
        return true;
    }

    private static boolean dfs(int first, int second){
        //visited.set(first, true);
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
//            if (!visited.get(firstNext)){
//                isomorphism = isomorphism && dfs(firstNext, secondNext);
//            }
        }
        return isomorphism;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("equivalence.in")));
        PrintWriter writer = new PrintWriter("equivalence.out");
        n = scanner.nextInt();
        int m = scanner.nextInt(), k = scanner.nextInt();
        firstAut = new ArrayList<>();
        secondAut = new ArrayList<>();
        for (int i = 0; i < 1002; i++){
            ArrayList<Boolean> tmp = new ArrayList<>();
            for (int j = 0; j < 1005; j++){
                tmp.add(false);
            }
            visited.add(tmp);
        }
        initAut(scanner, m, k, firstAut);
        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        initAut(scanner, m, k, secondAut);
        if (bfs()){
            writer.write("YES");
        } else {
            writer.write("NO");
        }
        writer.close();
    }
}
