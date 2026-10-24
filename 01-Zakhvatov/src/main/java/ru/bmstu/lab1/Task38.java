package ru.bmstu.lab1;

/**
 * Задача 38. Циклический сдвиг влево.
 */
public class Task38 {

    public static int[] rotateLeft(int[] arr, int k) {
        int n = arr.length;
        k = k % n;
        if (k < 0) {
            k += n;
        }

        rev(arr, 0, k - 1);
        rev(arr, k, n - 1);
        rev(arr, 0, n - 1);

        return arr;
    }

    private static void rev(int[] arr, int l, int r) {
        while (l < r) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }
    }
}
