package ru.bmstu.lab1;

/**
 * Задача 3. Подсчёт вхождений.
 */
public class Task3 {

    public static int countOccurrences(int[] arr, int x) {
        int k = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) k++;
        }

        return k;
    }
}
