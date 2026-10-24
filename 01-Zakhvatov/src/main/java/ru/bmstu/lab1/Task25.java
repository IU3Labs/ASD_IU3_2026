package ru.bmstu.lab1;

/**
 * Задача 25. Сумма элементов массива.
 */
public class Task25 {

    public static long sum(int[] arr) {
        long s = 0;
        for (int i = 0; i < arr.length; i++) {
            s += arr[i];
        }
        return s;
    }
}
