package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 11. Ближайший к среднему.
 * Вход: массив.
 * Выход: элемент с минимальным |val - avg|. При равенстве — первый по индексу.
 */
public class Task11ClosestToAvg {
    /**
     * Находит элемент, ближайший к среднему арифметическому.
     * @param arr массив
     * @return ближайший элемент
     */
    public static int closestToAvg(int[] arr) {
        long sum = 0;
        for (int val : arr) sum += val;
        double avg = (double) sum / arr.length;
        
        int best = arr[0];
        double minDiff = Math.abs(arr[0] - avg);
        
        for (int i = 1; i < arr.length; i++) {
            double diff = Math.abs(arr[i] - avg);
            if (diff < minDiff) {
                minDiff = diff;
                best = arr[i];
            }
        }
        return best;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        System.out.println(closestToAvg(arr));
    }
}