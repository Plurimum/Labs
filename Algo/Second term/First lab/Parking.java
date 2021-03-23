import java.io.*;
import java.util.Scanner;

import static java.lang.Integer.min;

public class Parking {
    private static Vertex t[];

    private static class Vertex{
        int hasFree;
        int minFreeIndex;

        Vertex(){
            hasFree = Integer.MAX_VALUE;
            minFreeIndex = Integer.MAX_VALUE;
        }

        Vertex(int min, int minFreeIndex){
            this.hasFree = min;
            this.minFreeIndex = minFreeIndex;
        }

        static Vertex merge(Vertex first, Vertex second){
            return new Vertex(min(first.hasFree, second.hasFree), min(first.minFreeIndex, second.minFreeIndex));
        }
    }

    private static void build(int v, int l, int r){
        if (l == r){
            t[v] = new Vertex(0, l);
            return;
        }
        int m = l + (r - l) / 2;
        build(leftChild(v), l, m);
        build(rightChild(v), m + 1, r);
        t[v] = Vertex.merge(t[leftChild(v)], t[rightChild(v)]);
    }

    private static int leftChild(int v) {
        return 2 * v + 1;
    }

    private static int rightChild(int v) {
        return 2 * v + 2;
    }

    private static void update(int v, int vl, int vr, int pos, int data){
        if (vl == vr){
            if (data == 0){
                t[v] = new Vertex(0, vl);
            } else {
                t[v] = new Vertex();
            }
            return;
        }
        int vm = vl + (vr - vl) / 2;
        if (pos > vm) {
            update(rightChild(v), vm + 1, vr, pos, data);
        } else {
            update(leftChild(v), vl, vm, pos, data);
        }
        t[v] = Vertex.merge(t[leftChild(v)], t[rightChild(v)]);
    }

    private static Vertex query(int v, int vl, int vr, int l, int r){
        if (vr < l || r < vl){
            return new Vertex();
        }
        if (l <= vl && vr <= r) {
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        Vertex ql = query(leftChild(v), vl, vm, l, r);
        Vertex qr = query(rightChild(v), vm + 1, vr, l, r);
        return Vertex.merge(ql, qr);
    }

    private static int nextFree(int index, int n){
        Vertex left = query(0, 0, n - 1, 0, index - 1);
        Vertex right = query(0, 0, n - 1, index, n - 1);
        if (right.hasFree == Integer.MAX_VALUE){
            return left.minFreeIndex;
        }
        return right.minFreeIndex;
    }

    private static void printTree(int v, String indent, int n){
        if (rightChild(v) < 4 * n){
            printTree(leftChild(v), indent + "      ", n);
            System.out.println(indent + "[" + t[v].hasFree + "; " + t[v].minFreeIndex + "]");
            printTree(rightChild(v), indent + "      ", n);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("parking.in")));
        PrintWriter writer = new PrintWriter(new File("parking.out"));
        int m, index;
        int n = scanner.nextInt();
        m = scanner.nextInt();
        String word;
        int SIZE = 4 * n;
        t = new Vertex[SIZE];
        build(0, 0, n - 1);
        //printTree(0, "", n);
        //System.out.println("\n-------------------\n");
        for (int i = 0; i < m; i++) {
            word = scanner.next();
            index = scanner.nextInt() - 1;
            switch (word) {
                case "enter":
                    int answer = nextFree(index, n);
                    update(0, 0, n - 1, answer, 1);
                    //printTree(0, "", n);
                    //System.out.println("\n-------------------\n");
                    writer.write(answer + 1 + "\n");
                    break;
                case "exit":
                    update(0, 0, n - 1, index, 0);
                    //printTree(0, "", n);
                    //System.out.println("\n-------------------\n");
                    break;
            }
        }
        writer.close();
    }
}
