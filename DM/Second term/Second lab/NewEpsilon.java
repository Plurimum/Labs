import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class NewEpsilon {

    public static ArrayList<Rule> rules = new ArrayList<>();
    public static ArrayList<Character> epsRules = new ArrayList<>();
    public static Set<Character> answer = new TreeSet<>();
    public static String transition;
    public static int n;

    public static class Rule {
        char leftNonTerminal;
        boolean canBeEpsilon;
        Set<Rule> rightNonTerminals;

        Rule(char leftNonTerminal) {
            this.leftNonTerminal = leftNonTerminal;
            rightNonTerminals = new HashSet<>();
        }
    }

    public static boolean brute(Rule rule) {
        boolean isEpsilon = true;
        for (Rule cur : rule.rightNonTerminals) {
            if (!answer.contains(cur.leftNonTerminal)) {
                isEpsilon = false;
                break;
            }
        }
        return isEpsilon;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("epsilon.in")));
        PrintWriter writer = new PrintWriter("epsilon.out");
        String firstLine = scanner.nextLine();
        String[] splittedFirstLine = firstLine.split(" ");
        n = Integer.parseInt(splittedFirstLine[0]);
        for (int i = 0; i < n; i++) {
            transition = scanner.nextLine();
            char nonTerm = transition.charAt(0);
            Rule rule = new Rule(nonTerm);
            if (transition.length() < 5) {
                epsRules.add(nonTerm);
            }
            for (int j = 5; j < transition.length(); j++) {
                if (('A' <= transition.charAt(j) && transition.charAt(j) <= 'Z') ||
                        ('a' <= transition.charAt(j) && transition.charAt(j) <= 'z')) {
                    rule.rightNonTerminals.add(new Rule(transition.charAt(j)));
                }
            }
            rules.add(rule);
        }
        int curSize = answer.size();
        int i = 0;
        while (!rules.isEmpty() && i < rules.size()){
            Rule rule = rules.get(i);
            i++;
            if (brute(rule)) {
                answer.add(rule.leftNonTerminal);
                if (answer.size() > curSize){
                    curSize = answer.size();
                    i--;
                    rules.remove(i);
                    i = 0;
                }
            }
        }
        for (char cur : answer) {
            writer.write(cur + " ");
            System.out.println(cur + " ");
        }
        writer.close();
    }

}