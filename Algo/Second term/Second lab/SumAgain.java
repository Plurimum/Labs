import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class SumAgain {

    private static long sum;

    static class Node {

        Node left;
        Node right;
        int priority;
        int value;
        long sum;

        Node(int value, int priority) {
            this.value = value;
            this.priority = priority;
            this.sum = value;
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



    private static Node root;

    private static Node merge(Node l, Node r) {
        if (r == null) {
            return l;
        }
        if (l == null) {
            return r;
        }
        if (l.priority < r.priority) {
            r.left = merge(l, r.left);
            r.sum = (r.left == null ? 0 : r.left.sum) + (r.right == null ? 0 : r.right.sum) + r.value;
            return r;
        }
        l.right = merge(l.right, r);
        l.sum = (l.left == null ? 0 : l.left.sum) + (l.right == null ? 0 : l.right.sum) + l.value;
        return l;
    }

    static Pair split(Node tree, int value) {
        if (tree == null) {
            return new Pair(null, null);
        }
        if (tree.value < value) {
            Pair pair = split(tree.right, value);
            tree.right = null;
            tree.sum = (tree.left == null ? 0 : tree.left.sum) + tree.value;
            return new Pair(merge(tree, pair.left), pair.right);
        } else {
            Pair pair = split(tree.left, value);
            tree.left = null;
            tree.sum = (tree.right == null ? 0 : tree.right.sum) + tree.value;
            return new Pair(pair.left, merge(pair.right, tree));
        }
    }

    static void insert(Node x, int value) {
        /*if (x == null) {
            Node m = new Node(value, new Random().nextInt());
            Pair p = split(root, value);
            root = merge(p.left, merge(m, p.right));
        }
        //Node x = root;
        if (x != null && x.value != value) {
            if (value < x.value) {
                insert(x.left, value);
            } else {
                insert(x.right, value);
            }
        }*/
        while (x != null && x.value != value){
            if (value < x.value) {
                x = x.left;
            } else {
                x = x.right;
            }
        }
        if (x == null) {
            Node m = new Node(value, new Random().nextInt());
            Pair p = split(root, value);
            root = merge(p.left, merge(m, p.right));
        }
    }

    static Node minimum(Node v) {
        if (v.left != null) {
            return minimum(v.left);
        } else {
            return v;
        }
    }

    static Node maximum(Node v) {
        if (v.right != null) {
            return maximum(v.right);
        } else {
            return v;
        }
    }

    static void sum(int left, int right, Node v) {
        if (v == null) {
            return;
        }
        if (v.value <= left && v.value >= right) {
            return;
        }
        if (v.value >= left && v.value <= right) {
            if (minimum(v).value >= left && maximum(v).value <= right) {
                sum += v.sum;
            } else if (minimum(v).value >= left) {
                sum += v.value;
                sum += v.left != null ? v.left.sum : 0;
                sum(left, right, v.right);
            } else if (maximum(v).value <= right) {
                sum += v.value;
                sum += v.right != null ? v.right.sum : 0;
                sum(left, right, v.left);
            } else {
                sum += v.value;
                sum(left, right, v.left);
                sum(left, right, v.right);
            }
            return;
        }
        if (v.value > right) {
            sum(left, right, v.left);
            return;
        }
        sum(left, right, v.right);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        int n = Integer.parseInt(scanner.nextLine());
        long y = 0;
        for (int i = 0; i < n; i++) {
            String opInStr = scanner.nextLine();
            String[] operation = opInStr.split(" ");
            String symbol = operation[0];
            int value = Integer.parseInt(operation[1]);
            switch (symbol) {
                case "+":
                    long sum = (value + y) % 1000000000;
                    insert(root, (int) sum);
                    y = 0;
                    break;
                case "?":
                    int right = Integer.parseInt(operation[2]);
                    SumAgain.sum = 0;
                    sum(value, right, root);
                    y = SumAgain.sum;
                    System.out.println(y);
                    break;
            }
        }
    }
}