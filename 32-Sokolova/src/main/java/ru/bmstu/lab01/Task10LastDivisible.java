package ru.bmstu.lab01;
import java.io.IOException;

/**
 * Задача 10. Последний элемент кратный k.
 * Вход: массив и k (!=0).
 * Выход: индекс последнего кратного k или -1.
 */
public class Task10LastDivisible {
    /**
     * Ищет последний индекс, где arr[i] % k == 0.
     * @param arr массив
     * @param k делитель
     * @return индекс или -1
     */
    public static int lastDivisibleIndex(int[] arr, int k) {
        int lastIdx = -1;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % k == 0) lastIdx = i;
        }
        return lastIdx;
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int k = StdIn.nextInt();
        System.out.println(lastDivisibleIndex(arr, k));
    }
}