import java.io.*;
import java.util.Scanner;
import java.util.Stack;

public class MoveToFront {

    static class Pair {
        Node left;
        Node right;

        Pair(Node left, Node right) {
            this.left = left;
            this.right = right;
        }
    }

    static class Node {
        Node left;
        Node right;

        int value;
        int key;
        int shift;

        Node(int key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    private static Node merge(Node left, Node right) {
        if (right == null && left == null) {
            return null;
        }
        if (right == null) {
            push(left);
            return left;
        }
        if (left == null) {
            push(right);
            return right;
        }
        push(left);
        push(right);

        if (left.value < right.value) {
            right.left = merge(left, right.left);
            push(left);
            push(right);
            return right;
        }
        left.right = merge(left.right, right);
        push(left);
        push(right);
        return left;
    }

    private static Stack<Integer> shifts = new Stack<>();

    private static Pair split(Node tree, int key) {
        if (tree == null) {
            return new Pair(null, null);
        }
        push(tree);
        if (tree.key < key) {
            Pair pair = split(tree.right, key);
            tree.right = pair.left;
            push(tree);
            return new Pair(tree, pair.right);
        } else {
            Pair pair = split(tree.left, key);
            tree.left = pair.right;
            push(tree);
            return new Pair(pair.left, tree);
        }
    }

    private static void push(Node vertex) {
        if (vertex == null)
            return;
        if (vertex.left != null)
            vertex.left.shift += vertex.shift;
        if (vertex.right != null)
            vertex.right.shift += vertex.shift;
        if (!shifts.empty()) {
            shifts.pop();
        } else {
            shifts.push(vertex.shift);
        }
        vertex.key += vertex.shift;
        vertex.shift = 0;
    }

    private static void printTree(Node current) {
        try {
            if (current == null) {
                return;
            }
            if (!shifts.empty()) {
                shifts.pop();
                shifts.push(current.shift);
            } else {
                shifts.push(current.shift);
            }
            printTree(current.left);
            System.out.print(current.value + " ");
            printTree(current.right);
        } catch (NullPointerException e){
            System.err.println(e);
        }

    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        String commands[];
        String string = scanner.nextLine();
        commands = string.split(" ");
        int n = Integer.parseInt(commands[0]);
        int m = Integer.parseInt(commands[1]);

        Node root = new Node(1, 1);
        Node first = root;

        for (int i = 1; i < n; i++) {
            root.right = new Node(i + 1, i + 1);
            root = root.right;
        }
        root = first;
        for (int i = 0; i < m; i++) {
            string = scanner.nextLine();
            commands = string.split(" ");
            int left = Integer.parseInt(commands[0]);
            int right = Integer.parseInt(commands[1]) + 1;
            shifts.push(left);
            shifts.push(right);
            Pair splitLeft = split(root, left);
            Pair splitRight = split(splitLeft.right, right);

            if (splitLeft.left != null) {
                splitLeft.left.shift += (right - left);
            }
            if (splitRight.left != null) {
                splitRight.left.shift -= (left - 1);
            }
            root = merge(splitRight.left, merge(splitLeft.left, splitRight.right));
        }
        printTree(root);
    }
}