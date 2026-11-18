package ru.bmstu.lab1;

/**
 * Задача 37. Разворот массива.
 */
public class Task37 {

    public static int[] reverse(int[] arr) {
        int l = 0;
        int r = arr.length - 1;

        while (l < r) {
            int tmp = arr[l];
            arr[l] = arr[r];
            arr[r] = tmp;
            l++;
            r--;
        }

        return arr;
    }
}
