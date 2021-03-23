import java.io.*;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;

public class kMax {

    private static Node root;
    private static final Stack<Integer> depths = new Stack<>();

    static class Node {
        Node left;
        Node right;

        int priority;
        int value;
        int depth;

        Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
            this.depth = 1;
        }
    }

    static class Pair {
        Node left;
        Node right;

        Pair(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    private static Node merge(Node l, Node r) {
        if (r == null) {
            return l;
        }
        if (l == null) {
            return r;
        }
        if (l.priority < r.priority) {
            r.left = merge(l, r.left);
            r.depth = (r.left != null ? r.left.depth : 0) + (r.right != null ? r.right.depth : 0) + 1;
            return r;
        }
        l.right = merge(l.right, r);
        l.depth = (l.left != null ? l.left.depth : 0) + (l.right != null ? l.right.depth : 0) + 1;
        return l;
    }

    private static void insert(int value, int priority) {
        Node x = root;
        while (x != null && x.value != value) {
            if (value < x.value)
                x = x.left;
            else
                x = x.right;
        }
        if (!depths.empty()) {
            depths.push(depths.peek() + depths.pop());
        } else {
            depths.push(value);
        }
        if (x == null) {
            Node m = new Node(value, priority);
            Pair p = split(root, value);
            root = merge(p.left, merge(m, p.right));
            root.depth = (root.left != null ? root.left.depth : 0) + (root.right != null ? root.right.depth : 0) + 1;
        }
    }

    private static Pair split(Node tree, int value) {
        if (tree == null) {
            return new Pair(null, null);
        }

        if (tree.value < value) {
            Pair pair = split(tree.right, value);
            tree.right = null;
            tree.depth = (tree.left != null ? tree.left.depth : 0) + 1;
            return new Pair(merge(tree, pair.left), pair.right);
        } else {
            Pair pair = split(tree.left, value);
            tree.left = null;
            tree.depth = (tree.right != null ? tree.right.depth : 0) + 1;
            return new Pair(pair.left, merge(pair.right, tree));
        }
    }

    private static Node delete(Node current, int value) {
        if (!depths.empty()) {
            depths.pop();
        } else {
            depths.push(value);
        }
        if (value == current.value) {
            return merge(current.left, current.right);
        }
        if (value < current.value) {
            current.left = delete(current.left, value);
        } else {
            current.right = delete(current.right, value);
        }
        current.depth = (current.left != null ? current.left.depth : 0) +
                (current.right != null ? current.right.depth : 0) + 1;
        if (!depths.empty()) {
            depths.pop();
        } else {
            depths.push(current.depth);
        }
        return current;
    }

    private static int getKMaximum(Node current, int value) {
        if ((current.right != null ? current.right.depth : 0) + 1 == value){
            return current.value;
        }
        if (current.right == null){
            return getKMaximum(current.left, value - 1);
        }
        if (current.right.depth >= value){
            return getKMaximum(current.right, value);
        }
        return getKMaximum(current.left, value - current.right.depth - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; ++i) {
            String opInStr = scanner.nextLine();
            String[] operation = opInStr.split(" ");
            String symbol = operation[0];
            int value = Integer.parseInt(operation[1]);
            int rand;
            switch (symbol) {
                case "1":
                case "+1":
                    rand = new Random().nextInt();
                    insert(value, rand);
                    break;
                case "-1":
                    root = delete(root, value);
                    break;
                case "0":
                    System.out.println(getKMaximum(root, value));
                    break;
            }
        }
    }

}