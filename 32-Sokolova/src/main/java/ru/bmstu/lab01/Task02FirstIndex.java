package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 2. Индекс первого вхождения.
 * Вход: массив и число x.
 * Выход: индекс первого x или -1.
 */
public class Task02FirstIndex {
    /**
     * Ищет первое вхождение x.
     * @param arr массив
     * @param x искомое значение
     * @return индекс или -1
     */
    public static int findFirstIndex(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) return i;
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int x = StdIn.nextInt();
        System.out.println(findFirstIndex(arr, x));
    }
}