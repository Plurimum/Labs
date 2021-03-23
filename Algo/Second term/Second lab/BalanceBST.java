import java.util.*;
import java.lang.*;
import java.io.*;

public class BalanceBST {

    private static Node root;

    private static class Node {
        private long key;
        private int balance;
        private int height;
        private Node left;
        private Node right;
        private Node parent;

        Node(long key) {
            this.key = key;
        }

        Node(){}
    }

    private static class AVL {
        Node root;

        public AVL(Node root) {
            this.root = root;
        }

        Node search(Node x, long key) {
            if (x == null) {
                return null;
            }
            if (x.key == key) {
                return x;
            }
            if (x.key > key) {
                return search(x.left, key);
            } else {
                return search(x.right, key);
            }
        }

        void insert(Node x, long key) {
            if (x == null) {
                root = new Node(key);
                return;
            }
            if (x.key == key) {
                return;
            }
            if (x.key > key) {
                if (x.left == null) {
                    x.left = new Node(key);
                    x.left.parent = x;
                    balance(x.left);
                    updateBalance(x);
                } else {
                    insert(x.left, key);
                }
            } else {
                if (x.right == null) {
                    x.right = new Node(key);
                    x.right.parent = x;
                    balance(x.right);
                    updateBalance(x);
                } else {
                    insert(x.right, key);
                }
            }
        }

        void balance(Node x) {
            if (x == null) {
                return;
            }
            updateBalance(x);
            if (x.balance == -2) {
                if (x.right.balance == 1) {
                    rotateRight(x.right);
                }
                rotateLeft(x);
            }
            if (x.balance == 2) {
                if (x.left.balance == -1) {
                    rotateLeft(x.left);
                }
                rotateRight(x);
            }
            updateBalance(x);
            balance(x.parent);
        }

        void rotateRight(Node x) {
            Node tmp = x.left;
            x.left = tmp.right;
            if (tmp.right != null) {
                tmp.right.parent = x;
            }
            tmp.right = x;
            rehangRoot(x, tmp);
        }

        void rotateLeft(Node x) {
            Node tmp = x.right;
            x.right = tmp.left;
            if (tmp.left != null) {
                tmp.left.parent = x;
            }
            tmp.left = x;
            rehangRoot(x, tmp);
        }

        private void rehangRoot(Node first, Node second) {
            if (first.parent == null) {
                root = second;
                second.parent = null;
            } else {
                if (first.parent.left == first) {
                    first.parent.left = second;
                } else {
                    first.parent.right = second;
                }
                second.parent = first.parent;
            }
            first.parent = second;
            updateBalance(first);
            updateBalance(second);
        }

        void updateBalance(Node x) {
            if (x == null) {
                return;
            }
            int leftHeight = 0, rightHeight = 0;
            if (x.left != null && x.right == null){
                leftHeight = x.left.height;
                rightHeight = 0;
            } else {
                if (x.left == null && x.right != null){
                    rightHeight = x.right.height;
                } else {
                    if (x.left != null){
                        leftHeight = x.left.height;
                        rightHeight = x.right.height;
                    }
                }
            }
            x.height = Math.max(leftHeight, rightHeight) + 1;
            x.balance = leftHeight - rightHeight;
        }

        void delete(Node x, long key) {
            if (x == null) {
                return;
            }
            if (x.left == null && x.right == null) {
                if (x.parent == null) {
                    root = null;
                    return;
                }
                if (x == x.parent.right) {
                    x.parent.right = null;
                } else {
                    x.parent.left = null;
                }
                balance(x.parent);
                return;
            }
            if (x.left != null && x.right != null) {
                Node nextNode = next(key);
                x.key = nextNode.key;
                delete(nextNode, key);
                return;
            }
            if (x.left != null) {
                if (x.parent == null) {
                    root = x.left;
                    x.left.parent = null;
                    return;
                }
                if (x == x.parent.right) {
                    x.parent.right = x.left;
                } else {
                    x.parent.left = x.left;
                }
                x.left.parent = x.parent;
            } else {
                if (x.parent == null) {
                    root = x.right;
                    x.right.parent = null;
                    return;
                }
                if (x == x.parent.right) {
                    x.parent.right = x.right;
                } else {
                    x.parent.left = x.right;
                }
                x.right.parent = x.parent;
            }
            balance(x.parent);
        }

        Node next(long key) {
            Node current = root;
            Node successor = null;
            while (current != null) {
                if (current.key > key) {
                    successor = current;
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return successor;
        }

        Node prev(Node x, long key) {
            Node current = root;
            Node successor = null;
            while (current != null) {
                if (current.key < key) {
                    successor = current;
                    current = current.right;
                } else {
                    current = current.left;
                }
            }
            return successor;
        }

    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer stringTokenizer;
        String s;
        AVL tree = new AVL(null);
        while ((s = bufferedReader.readLine()) != null) {
            stringTokenizer = new StringTokenizer(s);
            String command = stringTokenizer.nextToken();
            int value = Integer.parseInt(stringTokenizer.nextToken());
            Node root = tree.root;
            switch (command) {
                case "insert":
                    tree.insert(root, value);
                    break;
                case "exists":
                    System.out.println(tree.search(root, value) != null);
                    break;
                case "next":
                    if (tree.next(value) == null) {
                        System.out.println("none");
                    } else {
                        System.out.println(tree.next(value).key);
                    }
                    break;
                case "prev":
                    if (tree.prev(root, value) == null) {
                        System.out.println("none");
                    } else {
                        System.out.println(tree.prev(root, value).key);
                    }
                    break;
                case "delete":
                    tree.delete(tree.search(root, value), value);
                    break;
            }
        }
    }
}