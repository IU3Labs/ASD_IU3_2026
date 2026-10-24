package ru.bmstu.lab1;

/**
 * Задача 15. Пузырьковая сортировка с подсчётом обменов.
 */
public class Task15 {

    public record Result(int[] sortedArray, int swapCount) {}

    public static Result bubbleSort(int[] arr) {
        int k = 0;
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;

            for (int j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int tmp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = tmp;
                    k++;
                    swapped = true;
                }
            }

            if (!swapped) break;
        }

        return new Result(arr, k);
    }
}
