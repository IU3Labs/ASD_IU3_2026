package ru.bmstu.lab1;

/**
 * Задача 27. Сумма квадратов элементов массива.
 */
public class Task27 {

    public static long sumOfSquares(int[] arr) {
        long s = 0;
        for (int i = 0; i < arr.length; i++) {
            s += (long) arr[i] * arr[i];
        }
        return s;
    }
}
