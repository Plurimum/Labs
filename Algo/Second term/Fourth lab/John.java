import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class John {

    public static class Card{
        int redNumber, blueNumber;
        Card(int redNumber, int blueNumber){
            this.redNumber = redNumber;
            this.blueNumber = blueNumber;
        }
    }

    private static long getInversions(Card[] deck) {
        return mergeSort(deck, new Card[deck.length], 0, deck.length - 1);
    }

    private static long mergeSort(Card[] deck, Card[] temp, int low, int high) {
        long inversions = 0L;
        if (high > low) {
            int mid = (high + low) / 2;
            inversions = mergeSort(deck, temp, low, mid);
            inversions += mergeSort(deck, temp, mid + 1, high);
            inversions += merge(deck, temp, low, mid + 1, high);
        }
        return inversions;
    }

    private static long merge(Card[] deck, Card[] temp, int low, int mid, int high) {
        int i, j, k;
        long inversions = 0L;
        i = low;
        j = mid;
        k = low;
        for (; (i <= mid - 1) && (j <= high); ){
            if (redComparator.compare(deck[i], deck[j]) > 0){
                temp[k++] = deck[j++];
                inversions += (mid - i);
            } else {
                temp[k++] = deck[i++];
            }
        }
        for (; i <= (mid - 1); i++, k++){
            temp[k] = deck[i];
        }
        for (; j <= high; j++, k++){
            temp[k] = deck[j];
        }
        for (i = low; i <= high;) {
            deck[i] = temp[i];
            i++;
        }
        return inversions;
    }

    public static Comparator<Card> redComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return Integer.compare(o1.redNumber, o2.redNumber);
        }
    };

    public static Comparator<Card> blueComparator = new Comparator<Card>() {
        @Override
        public int compare(Card o1, Card o2) {
            return Integer.compare(o1.blueNumber, o2.blueNumber);
        }
    };

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("john.in")));
        PrintWriter writer = new PrintWriter("john.out");
        int n = scanner.nextInt();
        Card[] deck = new Card[n];
        Card currentCard;
        for (int i = 0; i < n; i++) {
            currentCard = new Card(scanner.nextInt(), scanner.nextInt());
            deck[i] = currentCard;
        }
        Arrays.sort(deck, redComparator);
        Arrays.sort(deck, blueComparator);
        writer.write(String.valueOf(getInversions(deck)));
        writer.close();
    }
}
