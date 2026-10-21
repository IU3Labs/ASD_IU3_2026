package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 3. Подсчёт вхождений.
 * Вход: массив и число x.
 * Выход: количество x.
 */
public class Task03CountOccurrences {
    /**
     * Считает вхождения x.
     * @param arr массив
     * @param x искомое значение
     * @return количество
     */
    public static int countOccurrences(int[] arr, int x) {
        int count = 0;
        for (int val : arr) if (val == x) count++;
        return count;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int x = StdIn.nextInt();
        System.out.println(countOccurrences(arr, x));
    }
}