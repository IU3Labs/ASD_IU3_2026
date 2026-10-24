package ru.bmstu.lab1;

/**
 * Задача 14. Сортировка вставками.
 */
public class Task14 {

    public static int[] insertionSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            for (int j = i; j > 0 && arr[j - 1] < arr[j]; j--) {
                int tmp = arr[j];
                arr[j] = arr[j - 1];
                arr[j - 1] = tmp;
            }
        }
        return arr;
    }
}
