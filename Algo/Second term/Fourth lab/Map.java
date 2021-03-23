import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

public class Map {

    public static class Pair{
        String first, second;
        Pair (String v1, String v2){
            first = v1;
            second = v2;
        }
        Pair(){}
    }

    public static ArrayList<ArrayList<Pair>> map = new ArrayList<>();

    public static int hashFunction(String string){
        int hashSum = 0;
        for (int i = 0; i < string.length(); i++){
            hashSum += string.charAt(i);
        }
        return hashSum % 1488228;
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("map.in")));
        PrintWriter writer = new PrintWriter("map.out");
        String str;
        for (int i = 0; i < 1488228; i++){
            ArrayList<Pair> tmp = new ArrayList<>();
            map.add(tmp);
        }
        while (scanner.hasNextLine()){
            str = scanner.nextLine();
            String[] splittedString = str.split(" ");
            String command = splittedString[0];
            String key = splittedString[1];
            int hashValue = hashFunction(key);
            int size = map.get(hashValue).size();
            if (command.equals("put")){
                String value = splittedString[2];
                boolean flag = false;
                for (int i = 0; i < size; i++){
                    if (key.equals(map.get(hashValue).get(i).first)){
                        map.get(hashValue).get(i).second = value;
                        flag = true;
                        break;
                    }
                }
                if (!flag){
                    Pair tmp = new Pair(key, value);
                    map.get(hashValue).add(tmp);
                }
            }
            if (command.equals("delete")){
                for (int i = 0; i < size; i++){
                    if (key.equals(map.get(hashValue).get(i).first)){
                        for (int j = i; j < size - 1; j++){
                            map.get(hashValue).set(j, map.get(hashValue).get(j + 1));
                        }
                        map.get(hashValue).remove(i);
                        break;
                    }
                }
            }
            if (command.equals("get")) {
                String answer = "";
                boolean flag = false;
                for (int i = 0; i < size; i++) {
                    if (key.equals(map.get(hashValue).get(i).first)) {
                        answer = map.get(hashValue).get(i).second;
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    writer.write(answer + '\n');
                    System.out.println(answer);
                } else {
                    writer.write("none" + '\n');
                    System.out.println("none");
                }
            }
        }
        writer.close();
    }
}