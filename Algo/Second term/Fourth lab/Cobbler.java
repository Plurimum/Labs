import java.io.*;
import java.util.*;

public class Cobbler {

    public static void main(String[] args){
        int count = 0;
        try {
            Scanner scanner = new Scanner( new BufferedReader( new FileReader("cobbler.in")));
            int k, n;
            k = scanner.nextInt();
            n = scanner.nextInt();
            ArrayList<Integer> boots = new ArrayList<>();
            for (int i = 0; i < n; i++){
                int tmp = scanner.nextInt();
                if (tmp <= k) {
                    boots.add(tmp);
                }
            }
            Collections.sort(boots);
            for (Integer boot : boots){
                if (k - boot < 0){
                    break;
                }
                k -= boot;
                count++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            PrintWriter writer = new PrintWriter("cobbler.out");
            writer.write(String.valueOf(count));
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
