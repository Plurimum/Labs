import java.util.*;

public class CountSort {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s;
        Integer num;
        Integer[] cnt = new Integer[101];
        Arrays.fill(cnt,0);
        while (scanner.hasNext()){
            num = scanner.nextInt();
            cnt[num]++;
        }
        for (int i = 0; i < 101; i++){
            for (int j =0; j < cnt[i]; j++){
                System.out.print(i + " ");
            }
        }
    }
}
