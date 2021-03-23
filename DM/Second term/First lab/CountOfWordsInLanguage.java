import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class CountOfWordsInLanguage{

    public CountOfWordsInLanguage() throws FileNotFoundException {
    }

    public static class Vertex{
        char visited;
        boolean notDevil, term;
        long count;
        int number;
        ArrayList<Integer> reverseTransfer, transfer;
        Vertex(){
            term = false;
            visited = 0;
            count = 0;
            notDevil = false;
            reverseTransfer = new ArrayList<>();
            transfer = new ArrayList<>();
        }
    }

    public static final int MOD = 1_000_000_007;

    public static ArrayList<Vertex> aut;

    public static void markReachable(int v){
        aut.get(v).notDevil = true;
        for (int i : aut.get(v).reverseTransfer){
            if (!aut.get(i).notDevil){
                markReachable(i);
            }
        }
    }

    public static PrintWriter writer;

    static {
        try {
            writer = new PrintWriter("problem3.out");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static long count(int v){
        switch (aut.get(v).visited){
            case 0:
                aut.get(v).visited = 1;
                for (int next : aut.get(v).transfer){
                    if (aut.get(next).notDevil){
                        aut.get(v).count = (aut.get(v).count + count(next)) % MOD;
                    }
                }
                aut.get(v).visited = 2;
                return aut.get(v).count;
            case 1:
                writer.write("-1");
                writer.close();
                System.exit(0);
            case 2:
                return aut.get(v).count;
            default:
                writer.close();
                System.exit(0);
        }
        return 0;
    }

    public static int n;

    private static void initAut(Scanner scanner, int m, int k, ArrayList<Vertex> aut) {
        for (int i = 0; i < n + 1; i++){
            aut.add(new Vertex());
        }
        for (int i = 0; i < k; i++){
            int tmp = scanner.nextInt();
            aut.get(tmp).term = true;
            aut.get(tmp).count = 1;
            aut.get(tmp).number = tmp;
        }
        for (int i = 0; i < m; i++){
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            char symbol = scanner.next().charAt(0);
            aut.get(from).transfer.add(to);
            aut.get(to).reverseTransfer.add(from);
        }
    }

    public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner(new BufferedReader(new FileReader("problem3.in")));

        aut = new ArrayList<>();
        n = scanner.nextInt();
        int m = scanner.nextInt();
        int k = scanner.nextInt();
        initAut(scanner, m, k, aut);
        for (Vertex i : aut){
            if (i.term){
                markReachable(i.number);
            }
        }
        long answer = count(1);
        writer.write(String.valueOf(answer % MOD));
        writer.close();
    }
}