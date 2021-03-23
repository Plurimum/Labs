import java.util.*;

public class countOfInverse {

    public static long merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0;
        long count = 0;
        while (i < left.length || j < right.length) {
            if (i == left.length) {
                arr[i + j] = right[j];
                j++;
            } else if (j == right.length) {
                arr[i + j] = left[i];
                i++;
            } else if (left[i] <= right[j]) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                count += left.length - i;
                j++;
            }
        }
        return count;
    }

    public static long invCount(int[] arr) {
        if (arr.length < 2)
            return 0;

        int m = (arr.length + 1) / 2;
        int left[] = Arrays.copyOfRange(arr, 0, m);
        int right[] = Arrays.copyOfRange(arr, m, arr.length);

        return invCount(left) + invCount(right) + merge(arr, left, right);
    }

    public static void main(String[] args) {
        Integer N, i;
        Scanner scanner = new Scanner(System.in);
        N = scanner.nextInt();
        int[] myArr = new int[N];
        for (i = 0; i < N; i++){
            myArr[i] = scanner.nextInt();
        }
        long k = invCount(myArr);
        System.out.println(k);
    }
}
