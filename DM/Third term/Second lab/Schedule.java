import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Schedule {

    private static long n, penalty, curTime;
    private static ArrayList<Task> tasks;
    private static PriorityQueue<Integer> taskPriority;

    private static class Task implements Comparable<Task>{
        int time;
        int weight;

        Task(int time, int weight) {
            this.time = time;
            this.weight = weight;
        }

        @Override
        public int compareTo(Task task) {
            if (time == task.time) {
                return Integer.compare(weight, task.weight);
            } else {
                return Integer.compare(time, task.time);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(new BufferedReader(new FileReader("schedule.in")));
        PrintWriter writer = new PrintWriter(new FileWriter("schedule.out"));
        n = scanner.nextInt();
        tasks = new ArrayList<>();
        penalty = 0;
        curTime = 0;
        for (int i = 0; i < n; i++) {
            int di = scanner.nextInt();
            int wi = scanner.nextInt();
            if (di == 0) {
                penalty += wi;
            } else {
                tasks.add(new Task(di, wi));
            }
        }
        Collections.sort(tasks);
        taskPriority = new PriorityQueue<>();
        for (Task task : tasks) {
            if (curTime < task.time) {
                curTime++;
            } else {
                penalty += taskPriority.poll();
            }
            taskPriority.add(task.weight);
        }
        writer.write(penalty + " ");
        scanner.close();
        writer.close();
    }
}
