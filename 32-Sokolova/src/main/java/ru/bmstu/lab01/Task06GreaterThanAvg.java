package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 6. Элементы больше среднего.
 * Вход: массив. Среднее целое.
 * Выход: количество элементов > avg.
 */
public class Task06GreaterThanAvg {
    /**
     * Считает элементы строго больше среднего.
     * @param arr массив
     * @return количество
     */
    public static int countGreaterThanAvg(int[] arr) {
        long sum = 0;
        for (int val : arr) sum += val;
        int avg = (int) (sum / arr.length);
        int count = 0;
        for (int val : arr) if (val > avg) count++;
        return count;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        System.out.println(countGreaterThanAvg(arr));
    }
}