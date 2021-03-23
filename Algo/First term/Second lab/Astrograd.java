import java.util.*;
import java.io.*;

public class Astrograd {
    public static class MyFastScanner {
        public String newLine;
        public BufferedReader consoleIn;

        MyFastScanner(InputStream stream) {
            consoleIn = new BufferedReader(new InputStreamReader(stream));
        }

        String  nextLine() throws IOException {
            return consoleIn.readLine();
        }

        void close() throws IOException {
            consoleIn.close();
        }
    }

    public static void main(String[] args) throws IOException{
        int n, i, operation, id, ask, exited = 0;
        ArrayDeque<Integer> queue = new ArrayDeque<>();
        MyFastScanner scanner = new MyFastScanner(System.in);
        HashMap<Integer, Integer> askers = new HashMap<>();
        String string;
        String[] numbers;
        string = scanner.nextLine();
        n = Integer.parseInt(string);
        for (i = 0; i < n; i++) {
            string = scanner.nextLine();
            numbers = string.split("[^-0-9]");
            operation = Integer.parseInt(numbers[0]);
            if (operation == 1) {
                id = Integer.parseInt(numbers[1]);
                queue.addLast(id);
                askers.put(id, queue.size() - 1 + exited);
            }
            if (operation == 2) {
                queue.removeFirst();
                exited++;
            }
            if (operation == 3) {
                queue.removeLast();
            }
            if (operation == 4) {
                ask = Integer.parseInt(numbers[1]);
                if (ask == queue.getFirst()) {
                    System.out.println(0);
                } else {
                    System.out.println(askers.get(ask) - exited);
                }
            }
            if (operation == 5) {
                System.out.println(queue.getFirst());
            }
        }
        scanner.close();
    }
}
