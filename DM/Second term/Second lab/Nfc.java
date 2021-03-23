import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Nfc {

    public static ArrayList<Rule> nonTermRules = new ArrayList<>();
    public static ArrayList<Rule> termRules = new ArrayList<>();
    public static String transition;
    public static int n;
    public static long[][][] dp;
    public static String word;
    public static int MOD = 1000000007;

    public static class Rule {
        char leftNonTerminal;
        ArrayList<Character> rightNonTerminals;

        Rule(char leftNonTerminal) {
            this.leftNonTerminal = leftNonTerminal;
            rightNonTerminals = new ArrayList<>();
        }
    }

    public static void printRules(ArrayList<Rule> rules) {
        for (Rule rule : rules) {
            System.out.print(rule.leftNonTerminal + " -> ");
            for (Character cur : rule.rightNonTerminals) {
                System.out.print(cur);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("nfc.in")));
        PrintWriter writer = new PrintWriter("nfc.out");
        String firstLine = scanner.nextLine();
        String[] splittedFirstLine = firstLine.split(" ");
        n = Integer.parseInt(splittedFirstLine[0]);
        char startSymbol = splittedFirstLine[1].charAt(0);
        for (int i = 0; i < n; i++) {
            transition = scanner.nextLine();
            char nonTerm = transition.charAt(0);
            Rule rule = new Rule(nonTerm);
            boolean containsTerm = false;
            int k = 1;
            while (!(('A' <= transition.charAt(k) && transition.charAt(k) <= 'Z') ||
                    ('a' <= transition.charAt(k) && transition.charAt(k) <= 'z'))){
                k++;
            }
            for (int j = k; j < transition.length(); j++) {
                rule.rightNonTerminals.add(transition.charAt(j));
                if ('a' <= transition.charAt(j) && transition.charAt(j) <= 'z') {
                    containsTerm = true;
                }
            }
            if (!containsTerm) {
                nonTermRules.add(rule);
            } else {
                termRules.add(rule);
            }
            //rules.get(nonTerm - 'A').add(rule);
        }
        /*printRules(nonTermRules);
        printRules(termRules);*/
        word = scanner.nextLine();
        dp = new long[26][100][100];
        for (int i = 0; i < word.length(); i++) {
            for (Rule rule : termRules) {
                if (rule.rightNonTerminals.get(0).equals(word.charAt(i))) {
                    dp[rule.leftNonTerminal - 'A'][i][i] = 1;
                }
            }
        }
        for (int len = 1; len < word.length(); len++) {
            for (int i = 0; i < word.length() - len; i++) {
                for (Rule rule : nonTermRules) {
                    for (int k = i; k < i + len; k++) {
                        dp[rule.leftNonTerminal - 'A'][i][i + len] +=
                                (dp[rule.rightNonTerminals.get(0) - 'A'][i][k] *
                           dp[rule.rightNonTerminals.get(1) - 'A'][k + 1][i + len]);
                        dp[rule.leftNonTerminal - 'A'][i][i + len] %= MOD;
                    }
                }
            }
        }
        /*System.out.println(Arrays.deepToString(dp));
        System.out.println(dp[startSymbol - 'A'][0][word.length() - 1]);
        */
        writer.write(String.valueOf(dp[startSymbol - 'A'][0][word.length() - 1] % MOD));
        writer.close();
    }
}