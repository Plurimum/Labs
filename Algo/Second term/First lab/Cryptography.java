import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Cryptography {

    private static class Matrix{
        int a11, a12, a21, a22;

        Matrix(){
            this.a11 = 0;
            this.a12 = 0;
            this.a21 = 0;
            this.a22 = 0;
        }

        Matrix(int a11, int a12, int a21, int a22){
            this.a11 = a11;
            this.a12 = a12;
            this.a21 = a21;
            this.a22 = a22;
        }

        Matrix multiply(Matrix first, Matrix second, int r){
            Matrix result = new Matrix();
            if (first.a11 > second.a11){
                System.out.println("kek");
            }
            result.a11 = ((first.a11 * second.a11) % r + (first.a12 * second.a21) % r) % r;
            result.a12 = ((first.a11 * second.a12) % r + (first.a12 * second.a22) % r) % r;
            result.a21 = ((first.a21 * second.a11) % r + (first.a22 * second.a21) % r) % r;
            result.a22 = ((first.a21 * second.a12) % r + (first.a22 * second.a22) % r) % r;
            return result;
        }

        Matrix readMatrix(Scanner scanner){
            return new Matrix(scanner.nextInt(), scanner.nextInt(),
                    scanner.nextInt(), scanner.nextInt());
        }

        void printMatrix(PrintWriter writer){
            writer.write(a11 + " " + a12 + "\n");
            writer.write(a21 + " " + a22 + "\n\n");
        }

        Matrix neutral(){
           return new Matrix(1, 0, 0, 1);
        }
    }

    private static final int ARRAY_SIZE = 200_000 * 4;
    private static final int SIZE = 200_000;
    private static Matrix t[] = new Matrix[ARRAY_SIZE];
    private static int MODULE;

    private static int leftChild(int v){
        return 2 * v + 1;
    }

    private static int rightChild(int v){
        return 2 * v + 2;
    }

    private static void fill(){
        for (int i = 0; i < t.length; i++){
            t[i] = new Matrix().neutral();
        }
    }

    private static void build(int v, int l, int r, Matrix[] a){
        if (l == r){
            t[v] = a[l];
            return;
        }
        int m = l + (r - l) / 2;
        build(leftChild(v), l, m, a);
        build(rightChild(v), m + 1, r, a);
        t[v] = new Matrix().multiply(t[leftChild(v)], t[rightChild(v)], MODULE);
    }

    private static Matrix query(int v, int vl, int vr, int l, int r){
        if (r < vl || vr < l){
            return new Matrix().neutral();
        }
        if (l <= vl && vr <= r){
            return t[v];
        }
        int vm = vl + (vr - vl) / 2;
        Matrix ql = query(2 * v + 1, vl, vm, l, r);
        Matrix qr = query(2 * v + 2, vm + 1, vr, l , r);
        return new Matrix().multiply(ql, qr, MODULE);
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File("crypto.in"));
        PrintWriter writer = new PrintWriter(new File("crypto.out"));
        MODULE = scanner.nextInt();
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Matrix[] mas = new Matrix[SIZE];
        for (int i = 0; i < n; i++){
            mas[i] = new Matrix().readMatrix(scanner);
        }
        fill();
        build(0, 0, n - 1, mas);
        int first, second;
        for (int i = 0; i < m; i++){
            first = scanner.nextInt() - 1;
            second = scanner.nextInt() - 1;
            query(0, 0,n - 1, first, second).printMatrix(writer);
        }
        writer.close();
    }
}
