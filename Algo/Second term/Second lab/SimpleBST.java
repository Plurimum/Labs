import java.io.*;

public class SimpleBST {

    private static class Node {

        Node left, right, parent;
        int value;

        Node(int value) {
            this.value = value;
        }
    }

    private static class Tree {
        private Node root;

        Node search(Node x, int key) {
            if (x == null || key == x.value) {
                return x;
            }
            if (key < x.value) {
                return search(x.left, key);
            } else {
                return search(x.right, key);
            }
        }

        Node minimum(Node x) {
            if (x.left == null) {
                return x;
            }
            return minimum(x.left);
        }

        Node maximum(Node x) {
            if (x.right == null) {
                return x;
            }
            return maximum(x.right);
        }

        Node next(int key) {
            /*if (x != null) {
                if (x.right != null) {
                    return minimum(x.right);
                }
                Node y = x.parent;
                while (y != null && x == y.right) {
                    x = y;
                    y = y.parent;
                }
                return y;
            } else {*/
                Node cur = root;
                Node result = null;
                while (cur != null) {
                    if (cur.value > key) {
                        result = cur;
                        cur = cur.left;
                    } else {
                        cur = cur.right;
                    }
                }
                return result;
            //}
        }

        Node prev(int key) {
            /*if (x != null) {
                if (x.left != null) {
                    return maximum(x.left);
                }
                Node y = x.parent;
                while (y != null && x == y.left) {
                    x = y;
                    y = y.parent;
                }
                return y;
            } else {*/
                Node cur = root;
                Node result = null;
                while (cur != null) {
                    if (cur.value < key) {
                        result = cur;
                        cur = cur.right;
                    } else {
                        cur = cur.left;
                    }
                }
                return result;
            //}
        }

        void insert(Node z) {
            if (root == null) {
                root = z;
                return;
            }
            Node x = root;
            while (true) {
                if (x.value == z.value) {
                    return;
                }
                if (z.value < x.value) {
                    if (x.left == null) {
                        x.left = z;
                        z.parent = x;
                    } else {
                        x = x.left;
                    }
                } else {
                    if (x.right == null) {
                        x.right = z;
                        z.parent = x;
                    } else {
                        x = x.right;
                    }
                }
            }
        }

        void delete(int x) {
            if (root == null) {
                return;
            }
            Node node = root, parent = null;
            while (node != null) {
                if (node.value == x) {
                    break;
                }
                parent = node;
                if (x < node.value) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            if (node == null) {
                return;
            }
            if (node.right == null) {
                if (parent == null) {
                    root = node.left;
                } else {
                    if (node == parent.left) {
                        parent.left = node.left;
                    } else {
                        parent.right = node.left;
                    }
                }
            } else {
                Node mostLeft = node.right;
                parent = null;
                while (mostLeft.left != null) {
                    parent = mostLeft;
                    mostLeft = mostLeft.left;
                }
                if (parent != null) {
                    parent.left = mostLeft.right;
                } else {
                    node.right = mostLeft.right;
                }
                node.value = mostLeft.value;
            }
        }
    }

    public static void main(String[] args) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Tree tree = new Tree();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] command = line.split(" ");
            String action = command[0];
            int value = Integer.parseInt(command[1]);
            switch (action) {
                case "insert":
                    Node insertion = new Node(value);
                    tree.insert(insertion);
                    break;
                case "delete":
                    tree.delete(value);
                    break;
                case "next":
                    Node next = tree.next(value);
                    if (next != null) {
                        System.out.println(next.value);
                    } else {
                        System.out.println("none");
                    }
                    break;
                case "prev":
                    Node prev = tree.prev(value);
                    if (prev != null) {
                        System.out.println(prev.value);
                    } else {
                        System.out.println("none");
                    }
                    break;
                case "exists":
                    Node elem = tree.search(tree.root, value);
                    System.out.println(elem != null);
            }
        }
    }
}
