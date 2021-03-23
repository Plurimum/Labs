import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

public class G {
    private static int n;
    private static int m;
    private static int maxColor;
    private static int number;
    private static int[] timeOut;
    private static int time = -1;
    private static ArrayList<ArrayList<Integer>> graph;
    private static ArrayList<ArrayList<Integer>> transposedGraph;
    private static ArrayList<ArrayList<Integer>> condensation;
    private static int[] color;
    private static int[] condensationColor;
    private static int[] used;
    private static int[] sort;
    private static ArrayList<Integer> order;
    private static ArrayList<String> answer = new ArrayList<>();
    private static HashSet<Edge> condensationEdges = new HashSet<>();
    private static HashSet<String> ans = new HashSet<>();
    private static HashMap<Integer, String> names = new HashMap<>();
    private static HashMap<String, Integer> commonNames = new HashMap<>();
    private static HashMap<Integer, Integer> hashMap = new HashMap<>();

    static class Edge {
        int from;
        int to;

        Edge(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public int hashCode() {
            return (int) (((long) ((to + 13) * (from + 17))) % 2_147_483_647 % 1_760_777);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (obj == null || obj.getClass() != this.getClass()) {
                return false;
            }
            Edge edge = (Edge) obj;
            return to == edge.to && from == edge.from;
        }
    }

    private static void dfs1(int v) {
        used[v] = 1;
        for (int i = 0; i < graph.get(v).size(); i++) {
            int u = graph.get(v).get(i);
            if (used[u] == 0) {
                dfs1(u);
            }
        }
        order.add(v);
    }

    private static void dfs2(int v) {
        color[v] = maxColor;
        for (int i = 0; i < transposedGraph.get(v).size(); i++) {
            int u = transposedGraph.get(v).get(i);
            if (color[u] == 0) {
                dfs2(u);
            }
        }
    }

    private static void dfs(int v) {
        condensationColor[v] = 1;
        time++;
        for (int i = 0; i < condensation.get(v).size(); i++) {
            int u = condensation.get(v).get(i);
            if (condensationColor[u] < 1) {
                dfs(u);
            }
        }
        condensationColor[v] = 2;
        time++;
        timeOut[v] = time;
    }

    static void init(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        graph = new ArrayList<>();
        transposedGraph = new ArrayList<>();
        order = new ArrayList<>();
        for (int i = 0; i < 2 * n; i++) {
            graph.add(new ArrayList<>());
            transposedGraph.add(new ArrayList<>());
        }
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String name = scanner.nextLine();
            commonNames.put(name, number);
            names.put(number, name);
            names.put(number + n, name);
            number++;
        }
        for (int i = 0; i < m; i++) {
            String[] split = scanner.nextLine().split("[\\s]");
            int first, second;
            if (split[0].charAt(0) != '-') {
                first = commonNames.get(split[0].substring(1));
            } else {
                first = commonNames.get(split[0].substring(1)) + n;
            }
            if (split[2].charAt(0) != '-') {
                second = commonNames.get(split[2].substring(1));
            } else {
                second = commonNames.get(split[2].substring(1)) + n;
            }
            graph.get(first).add(second);
            graph.get((second + n) % (2 * n)).add((first + n) % (2 * n));
            transposedGraph.get(second).add(first);
            transposedGraph.get((first + n) % (2 * n)).add((second + n) % (2 * n));
        }
    }

    static void firstTraversal() {
        maxColor = 0;
        used = new int[2 * n];
        color = new int[2 * n];
        for (int i = 0; i < 2 * n; i++) {
            if (used[i] == 0) {
                dfs1(i);
            }
        }
    }

    static void secondTraversal() {
        for (int i = 0; i < 2 * n; i++) {
            int v = order.remove(2 * n - 1 - i);
            if (color[v] == 0) {
                maxColor++;
                dfs2(v);
            }
        }
    }

    static void addEdges() {
        for (int i = 0; i < 2 * n; i++) {
            for (int j = 0; j < graph.get(i).size(); j++) {
                int u = graph.get(i).get(j);
                if (color[i] == color[u]) {
                    continue;
                }
                condensationEdges.add(new Edge(color[i] - 1, color[u] - 1));
                ans.add((color[i] - 1) + " " + (color[u] - 1));
            }
        }
    }

    static void initCondensation() {
        condensation = new ArrayList<>();
        for (int i = 0; i < maxColor; i++) {
            condensation.add(new ArrayList<>());
        }
        while (!condensationEdges.isEmpty()) {
            Edge edge = condensationEdges.iterator().next();
            int parent, child;
            parent = edge.from;
            child = edge.to;
            condensation.get(parent).add(child);
            condensationEdges.remove(edge);
        }
    }

    static boolean isImpossible() {
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            if (color[i] == color[i + n]) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    static void thirdTraversal() {
        timeOut = new int[maxColor];
        condensationColor = new int[maxColor];
        for (int i = 0; i < maxColor; i++) {
            if (condensationColor[i] == 0) {
                dfs(i);
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        init(scanner);
        firstTraversal();
        secondTraversal();
        if (isImpossible()) {
            System.out.println(-1);
        } else {
            addEdges();
            initCondensation();
            thirdTraversal();
            sort = new int[2 * maxColor];
            HashMap<Integer, Integer> commonNames = new HashMap<>();
            for (int i = 0; i < maxColor; i++) {
                sort[timeOut[i]]++;
                commonNames.put(timeOut[i], i);
            }
            number = 0;
            for (int i = 2 * maxColor - 1; i >= 0; i--) {
                if (sort[i] == 1) {
                    hashMap.put(commonNames.get(i), number);
                    number++;
                }
            }
            for (int i = 0; i < n; i++) {
                if (hashMap.get(color[i] - 1) > hashMap.get(color[i + n] - 1)) {
                    answer.add(names.get(i));
                }
            }
            System.out.println(answer.size());
            for (String name : answer) {
                System.out.println(name);
            }
        }
    }
}
