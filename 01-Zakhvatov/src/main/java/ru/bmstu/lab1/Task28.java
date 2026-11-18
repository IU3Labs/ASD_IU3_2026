package ru.bmstu.lab1;

/**
 * Задача 28. Медиана массива.
 */
public class Task28 {

    public static double findMedian(int[] arr) {
        int n = arr.length;
        int[] a = arr.clone();

        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && a[j - 1] > a[j]; j--) {
                int tmp = a[j];
                a[j] = a[j - 1];
                a[j - 1] = tmp;
            }
        }

        if (n % 2 != 0) {
            return (double) a[n / 2];
        } else {
            return (a[n / 2 - 1] + a[n / 2]) / 2.0;
        }
    }
}
