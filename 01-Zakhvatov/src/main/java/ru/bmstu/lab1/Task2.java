package ru.bmstu.lab1;

/**
 * Задача 2. Индекс первого вхождения.
 */
public class Task2 {

    public static int findFirst(int[] arr, int x) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) return i;
        }

        return -1;
    }
}
