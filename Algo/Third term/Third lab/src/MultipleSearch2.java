import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class MultipleSearch2 {
    private static ArrayList<String> patterns;
    private static int n;
    private static String mainString;
    private static Node root;
    private static ArrayList<Boolean> term;

    private static class Node {
        HashMap<Character, Node> next;
        char parentSymbol;
        boolean term;
        Node sufLink;
        Node parent;
        HashSet<Integer> terminals;
        boolean used;

        Node(Node sufLink, Node parent, char symbol) {
            parentSymbol = symbol;
            this.parent = parent;
            this.sufLink = sufLink;
            next = new HashMap<>();
            term = false;
            used = false;
        }

        boolean contains(char chr) {
            return next.containsKey(chr);
        }

        Node getNext(char chr) {
            return next.get(chr);
        }

        void add(char chr, Node node) {
            next.put(chr, new Node(null, node, chr));
        }
    }

    private static HashSet<Integer> writeAllTerminalsFromSufLinks(Node vertex) {
        if (vertex.used) {
            return new HashSet<>();
        } else {
            if (vertex == root) {
                vertex.used = true;
                vertex.terminals = new HashSet<>();
                return new HashSet<>();
            } else {
                if (vertex.terminals == null) {
                    vertex.terminals = new HashSet<>();
                }
                for (Integer terminal : writeAllTerminalsFromSufLinks(getSufLink(vertex))) {
                    vertex.terminals.add(terminal);
                }
                vertex.used = true;
                for (int j : vertex.terminals) {
                    term.set(j, true);
                }
                return vertex.terminals;
            }
        }
    }

    private static Node getSufLink(Node node) {
        if (node.sufLink == null) {
            Node parent = node.parent;
            char symbol = node.parentSymbol;
            while (true) {
                if (node != root && parent != root) {
                    parent = getSufLink(parent);
                    if (!parent.contains(symbol)) {
                        continue;
                    }
                    node.sufLink = parent.getNext(symbol);
                    return node.sufLink;
                } else {
                    node.sufLink = root;
                    return root;
                }
            }
        } else {
            return node.sufLink;
        }
    }

    private static void buildBor() {
        root = new Node(null, null, ' ');
        for (int i = 0; i <= n - 1; ++i) {
            Node node = root;
            for (int j = 0; j <= patterns.get(i).length() - 1; ++j) {
                if (!node.contains(patterns.get(i).charAt(j))) {
                    node.add(patterns.get(i).charAt(j), node);
                }
                node = node.getNext(patterns.get(i).charAt(j));
                if (j != patterns.get(i).length() - 1) {
                    continue;
                }
                if (node.terminals == null) {
                    node.terminals = new HashSet<>();
                }
                node.term = true;
                node.terminals.add(i);
            }
        }
        root = new Node(null, null, ' ');
        for (int i = 0; i < n; i++) {
            if (patterns.get(i).length() > str.length()) {
                continue;
            }
            Node node = root;
            for (int j = 0; j < patterns.get(i).length(); j++) {
                if (!node.contains(patterns.get(i).charAt(j))) {
                    node.add(patterns.get(i).charAt(j), node);
                }
                node = node.get(patterns.get(i).charAt(j));
                if (j == patterns.get(i).length() - 1) {
                    if (node.i == null) {
                        node.i = new Int();
                        kek.addLast(node.i);
                        node.term = true;
                    }
                    node.i.list.add(i);
                }
            }
        }
    }

    private static void stringTraversal() {
        Node node = root;
        for (int i = 0; i < mainString.length(); i++) {
            char chr = mainString.charAt(i);
            if (node.contains(chr)) {
                node = node.getNext(chr);
            } else {
                while (node != root && !node.contains(chr)) {
                    node = getSufLink(node);
                }
                if (node.contains(chr)) {
                    node = node.getNext(chr);
                } else {
                    node = root;
                }
            }
            writeAllTerminalsFromSufLinks(node);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter writer = new PrintWriter(System.out);
        n = Integer.parseInt(scanner.nextLine());
        patterns = new ArrayList<>();
        term = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            patterns.add(scanner.nextLine());
            term.add(false);
        }
        mainString = scanner.nextLine();
        buildBor();
        scanner.close();
        stringTraversal();
        for (int i = 0; i < n; i++) {
            writer.write((term.get(i) ? "YES" : "NO") + '\n');
        }
        writer.close();
    }
}
