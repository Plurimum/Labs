import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.*;

public class FastMinimizationDFA {

    public static class Vertex{
        int index;
        boolean accept, reach, reachable;
        int[] transitions = new int[26];
        ArrayList<ArrayList<Integer>> back = new ArrayList<>();
        Vertex(){
            for (int i = 0; i < 26; i++){
                transitions[i] = -1;
                back.add(new ArrayList<>());
            }
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

    public static class MegaPair{
        Integer first;
        ArrayList<Integer> second;
        MegaPair(int first, ArrayList<Integer> second){
            this.first = first;
            this.second = second;
        }
    }

    public static ArrayList<ArrayList<Pair>> tr;
    public static ArrayList<ArrayList<Integer>> b;
    public static ArrayList<Vertex> dka;

    public static int n;

    private static void initAut(Scanner scanner, int m, int k, ArrayList<Vertex> aut) {
        for (int i = 0; i < n + 1; i++){
            aut.add(new Vertex());
        }
        for (int i = 0; i < m; i++){
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            char symbol = scanner.next().charAt(0);
            aut.get(from).transitions[(symbol - 'a')] = to;
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("fastminimization.in")));
        PrintWriter writer = new PrintWriter("fastminimization.out");
        n = scanner.nextInt();
        int m, k, from, to;
        char c;
        m = scanner.nextInt();
        k = scanner.nextInt();
        dka = new ArrayList<>();
        tr = new ArrayList<>();
        b = new ArrayList<>();
        LinkedList<Integer> reach = new LinkedList<>();
        for (int i = 0; i < k; i++){
            from = scanner.nextInt() - 1;
            dka.get(from).accept = true;
            reach.add(from);
        }
        for (int i = 0; i < m; i++){
            from = scanner.nextInt() - 1;
            to = scanner.nextInt();
            c = scanner.next().charAt(0);
            tr.get(from).add(new Pair(c - 97, to - 1));
            b.get(to - 1).add(from);
        }
        while (!reach.isEmpty()){
            int pop = reach.pop();
            if (dka.get(pop).reach && !dka.get(pop).reachable){
                dka.get(pop).reachable = true;
                for (Pair i : tr.get(pop)){
                    reach.add(i.second);
                }
            }
        }
        HashSet<Integer> terms = new HashSet<>(), other = new HashSet<>();
        for (int i = 0; i < n; i++){
            if (dka.get(i).reachable){
                if (dka.get(i).accept) {
                    terms.add(i);
                } else {
                    other.add(i);
                }
                if (dka.get(i).accept){
                    dka.get(i).index = 0;
                } else {
                    dka.get(i).index = 1;
                }
//                dka.get(i).index = !dka.get(i).accept;
                for (Pair j : tr.get(i)){
                    if (dka.get(j.second).reachable){
                        dka.get(i).transitions[j.first] = j.second;
                        dka.get(j.second).back.get(j.first).add(i);
                    }
                }
            }
        }
        ArrayList<HashSet<Integer>> newDka = new ArrayList<>();
        if (!other.isEmpty()){
            newDka.add(other);
        }
        LinkedList<Pair> q = new LinkedList<>();
        for (int i = 0; i < 26; i++){
            q.add(new Pair(0, i));
            if (!other.isEmpty()){
                q.add(new Pair(1, i));
            }
        }
        while (!q.isEmpty()){
            Pair pop = q.pop();
            HashMap<Integer, ArrayList<Integer>> involved = new HashMap<>();
            for (int i : newDka.get(pop.first)){
                for (int r : dka.get(i).back.get(pop.second)){
                    involved.get(dka.get(r).index).add(r);
                }
            }
            for (MegaPair i : involved){
        }
    }
}
