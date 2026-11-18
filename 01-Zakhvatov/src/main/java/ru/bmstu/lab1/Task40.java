package ru.bmstu.lab1;

/**
 * Задача 40. Удаление дубликатов из отсортированного массива.
 */
public class Task40 {

    public static int[] removeDuplicates(int[] arr) {
        int k = 1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != arr[k - 1]) {
                arr[k] = arr[i];
                k++;
            }
        }

        int[] res = new int[k];
        for (int i = 0; i < k; i++) {
            res[i] = arr[i];
        }

        return res;
    }
}
