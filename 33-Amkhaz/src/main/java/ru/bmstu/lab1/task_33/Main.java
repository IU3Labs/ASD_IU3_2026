package ru.bmstu.lab1.task_33;

import java.util.Scanner;

/** Задача 33. Количество инверсий методом сортировки слиянием. */
public class Main {
    public static long countInversions(int[] array) {
        int[] temp = new int[array.length];
        return mergeSort(array, temp, 0, array.length - 1);
    }

    private static long mergeSort(int[] a, int[] temp, int left, int right) {
        if (left >= right) return 0;
        int middle = left + (right - left) / 2;
        long count = mergeSort(a, temp, left, middle)
                   + mergeSort(a, temp, middle + 1, right);
        return count + merge(a, temp, left, middle, right);
    }

    private static long merge(int[] a, int[] temp, int left, int middle, int right) {
        int i = left, j = middle + 1, k = left;
        long count = 0;
        while (i <= middle && j <= right) {
            if (a[i] <= a[j]) temp[k++] = a[i++];
            else {
                temp[k++] = a[j++];
                count += middle - i + 1;
            }
        }
        while (i <= middle) temp[k++] = a[i++];
        while (j <= right) temp[k++] = a[j++];
        for (int p = left; p <= right; p++) a[p] = temp[p];
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) array[i] = scanner.nextInt();
        System.out.println(countInversions(array));
        scanner.close();
    }
}
