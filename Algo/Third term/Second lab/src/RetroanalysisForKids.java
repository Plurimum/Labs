import java.io.*;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;

public class RetroanalysisForKids {

    private static int n, m;
    private static HashSet<Integer>[] graph;
    private static HashSet<Integer>[] transposedGraph;
    private static Queue<Integer> queue;
    private static int[] d, degree;
    private static boolean[] used;

    static class FastScanner {

        InputStream in;
        int curPos, curSize;

        public FastScanner(InputStream input) {
            in = input;
        }

        private int SIZE = 256;
        private byte[] buffer = new byte[SIZE];


        private void readBytes() throws IOException {
            curSize = in.read(buffer);
            curPos = 0;
        }

        public boolean hasNextLine() throws IOException {
            if (curSize == curPos) {
                readBytes();
            }
            return curSize > curPos;
        }

        public String nextLine() throws IOException {
            if (curPos == curSize) {
                readBytes();
            }
            StringBuilder line = new StringBuilder();
            while (curPos < curSize && !((buffer[curPos] == '\n'))) {
                char c = (char) buffer[curPos];
                line.append(c);
                curPos++;
                if (curPos == curSize) {
                    readBytes();
                }
            }
            curPos++;
            return line.toString();
        }
    }



    private static void solve(FastScanner reader, BufferedWriter writer) throws IOException {
        String line;
        while (reader.hasNextLine()) {
            line = reader.nextLine();
            String[] split = line.split("[\\s]");
            n = Integer.parseInt(split[0]);
            m = Integer.parseInt(split[1]);
            graph = new HashSet[n];
            transposedGraph = new HashSet[n];
            queue = new ArrayDeque<>();
            used = new boolean[n];
            d = new int[n];
            degree = new int[n];
            for (int i = 0; i < n; i++) {
                graph[i] = new HashSet<>();
                transposedGraph[i] = new HashSet<>();
                used[i] = false;
                degree[i] = 0;
                d[i] = -1;
            }
            for (int i = 0; i < m; i++) {
                split = reader.nextLine().split("[\\s]");
                int from = Integer.parseInt(split[0]) - 1;
                int to = Integer.parseInt(split[1]) - 1;
                graph[from].add(to);
                transposedGraph[to].add(from);
            }
            for (int v = 0; v < n; v++) {
                if (graph[v].isEmpty()) {
                    queue.add(v);
                    d[v] = 0;
                    used[v] = true;
                }
            }
            while (!queue.isEmpty()) {
                int v = queue.peek();
                queue.remove();
                if (d[v] == 0) {
                    for (int u : transposedGraph[v]) {
                        if (!used[u]) {
                            queue.add(u);
                            d[u] = 1;
                            used[u] = true;
                        }
                    }
                } else {
                    if (d[v] == 1) {
                        for (int u : transposedGraph[v]) {
                            degree[u]++;
                            if (degree[u] == graph[u].size() && !used[u]) {
                                d[u] = 0;
                                queue.add(u);
                                used[u] = true;
                            }
                        }
                    }
                }
            }
            StringBuilder result = new StringBuilder();
            for (int i = 0; i < n; i++) {
                if (d[i] == -1) {
                    result.append("DRAW\n");
                } else {
                    result.append(d[i] == 0 ? "SECOND\n" : "FIRST\n");
                }
            }
            result.append("\n");
            writer.write(result.toString());
            writer.flush();
        }
    }

    public static void main(String[] args) throws IOException {
        FastScanner reader = new FastScanner(System.in);
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(System.out));
        solve(reader, writer);
        writer.close();
    }
}
