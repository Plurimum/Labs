import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeSet;

public class Checking {

    private static int n, m;
    private static ArrayList<ArrayList<Integer>> collection;

    private static int countBinaryOnes(int number) {
        char one = '1';
        return (int) Long.toBinaryString(number).chars().filter(item->item == one).count();
    }

    private static boolean checkSubSet(int superset) {
        for (int subset = superset; subset > 0; subset = (subset - 1) & superset) {
            boolean isSubsetFound = false;
            int setSize = countBinaryOnes(subset);
            for (int set : collection.get(setSize)) {
                if (set == subset) {
                    isSubsetFound = true;
                    break;
                }
            }
            if (!isSubsetFound) {
                return isSubsetFound;
            }
        }
        return true;
    }

    private static boolean existChangingElement(int smallSet, int bigSet) {
        TreeSet<Integer> difference = new TreeSet<>();
        int smallSetSize = countBinaryOnes(smallSet);
        int index = 0;
        for (int bits = bigSet; bits > 0; bits /= 2) {
            if ((bits & 1) != 0) {
                difference.add(index);
            }
            index++;
        }
        index = 0;
        for (int bits = smallSet; bits > 0; bits /= 2) {
            if ((bits & 1) != 0) {
                difference.remove(index);
            }
            index++;
        }
        for (int x : difference) {
            for (int set : collection.get(smallSetSize + 1)) {
                if (set == (smallSet | (1 << x))) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean firstAxiom() {
        return !collection.get(0).isEmpty();
    }

    private static boolean secondAxiom() {
        for (int i = 1; i < collection.size(); i++) {
            for (int set : collection.get(i)) {
                if (!checkSubSet(set)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean thirdAxiom() {
        for (int i = 0; i < collection.size() - 1; i++) {
            for (int smallSet : collection.get(i)) {
                for (int j = i + 1; j < collection.size(); j++) {
                    for (int bigSet : collection.get(j)) {
                        if (!existChangingElement(smallSet, bigSet)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    private static void init(Scanner scanner) {
        n = scanner.nextInt();
        m = scanner.nextInt();
        collection = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            collection.add(new ArrayList<>());
        }
        for (int i = 0; i < m; i++) {
            int setSize = scanner.nextInt();
            collection.get(setSize).add(0);
            for (int j = 0; j < setSize; j++) {
                int last = collection.get(setSize).size() - 1;
                collection.get(setSize).set(last,
                        collection.get(setSize).get(last) | (1 << (scanner.nextInt())));
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("check.in")));
        PrintWriter writer = new PrintWriter(new FileWriter("check.out"));
        init(scanner);
        if (firstAxiom() && secondAxiom() && thirdAxiom()) {
            writer.write("YES");
        } else {
            writer.write("NO");
        }
        scanner.close();
        writer.close();
    }
}
