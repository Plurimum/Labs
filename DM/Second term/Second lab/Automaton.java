import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Automaton {

    public static ArrayList<ArrayList<ArrayList<Integer>>> automaton;
    public static String word, startLine;
    public static int n;
    public static char startSymbol;

    public static boolean dfs(int index, int vertex){
        if (index == word.length()){
            return (vertex == 26);
        }
        int cur = word.charAt(index) - 'a';
        for (int i = 0; i < automaton.get(vertex).get(cur).size(); i++){
            if (dfs(index + 1, automaton.get(vertex).get(cur).get(i))){
                return true;
            }
        }
        return false;
    }

    public static void init(){
        automaton = new ArrayList<>();
        for (int i = 0; i < 27; i++){
            ArrayList<ArrayList<Integer>> tmp = new ArrayList<>();
            for (int j = 0; j < 26; j++){
                ArrayList<Integer> littleTmp = new ArrayList<>();
                tmp.add(littleTmp);
            }
            automaton.add(tmp);
        }
    }

    public static void readGrammar(Scanner scanner){
        startLine = scanner.nextLine();
        String[] splittedLine = startLine.split(" ");
        n = Integer.parseInt(splittedLine[0]);
        startSymbol = splittedLine[1].charAt(0);
        for (int i = 0; i < n; i++){
            char c;
            String string;
            string = scanner.nextLine();
            String[] splittedString = string.split(" ");
            c = splittedString[0].charAt(0);
            String s = splittedString[2];
            if (s.length() == 1){
                automaton.get(c - 'A').get(s.charAt(0) - 'a').add(26);
            } else {
                automaton.get(c - 'A').get(s.charAt(0) - 'a').add(s.charAt(1) - 'A');
            }
        }
    }

    public static void solve(Scanner scanner, PrintWriter writer){
        String line = scanner.nextLine();
        int m;
        m = Integer.parseInt(line);
        for (int i = 0; i < m; i++){
            word = scanner.nextLine();
            writer.write(dfs(0, startSymbol - 'A') ? "yes" : "no");
            writer.write('\n');
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("automaton.in")));
        PrintWriter writer = new PrintWriter("automaton.out");
        init();
        readGrammar(scanner);
        solve(scanner, writer);
        writer.close();
    }
}
