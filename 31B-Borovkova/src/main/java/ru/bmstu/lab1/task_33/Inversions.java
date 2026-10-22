/*Задача 33. Количество инверсий
Вход: массив из (n) целых чисел.
Выход: количество пар ((i, j)) таких, что (i < j) и (a_i > a_j).
Требование: (O(n log n)) (через модификацию сортировки слиянием).
Сложность: высокая */

package ru.bmstu.lab1.task_33;

import java.util.Scanner;

/**
 * Решение задачи 33: подсчёт инверсий модифицированной сортировкой слиянием за O(n log n).
 */
public class Inversions {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает количество инверсий — пар i < j с a[i] > a[j].
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int n = scanner.nextInt();
            if (n < MIN_LENGTH || n > MAX_LENGTH) {
                System.out.println("Ошибка: некорректная длина массива");
                return;
            }

            // Читаем сам массив
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
            }

            // Инверсий может быть до n(n-1)/2 ≈ 5·10^9 — больше int, поэтому long
            long k = mergeSort(a, new int[n], 0, n - 1);
            System.out.println(k);
        }
    }

    /**
     * Сортирует a[l..r] слиянием и возвращает число инверсий внутри этого отрезка.
     *
     * @param a   сортируемый массив
     * @param t   вспомогательный массив той же длины для слияния
     * @param l   левая граница отрезка (включительно)
     * @param r   правая граница отрезка (включительно)
     * @return количество пар l <= i < j <= r с a[i] > a[j] до сортировки
     */
    private static long mergeSort(int[] a, int[] t, int l, int r) {
        if (l >= r) {
            return 0;
        }
        int m = (l + r) / 2;
        // Инверсии внутри левой половины + внутри правой + между половинами
        long k = mergeSort(a, t, l, m) + mergeSort(a, t, m + 1, r);

        int i = l;
        int j = m + 1;
        int p = l;
        while (i <= m && j <= r) {
            if (a[i] <= a[j]) {
                t[p] = a[i];
                i++;
            } else {
                // a[j] меньше a[i], а значит и всех a[i..m] (левая половина отсортирована):
                // каждый из m - i + 1 элементов образует с a[j] инверсию
                t[p] = a[j];
                j++;
                k = k + (m - i + 1);
            }
            p++;
        }
        while (i <= m) {
            t[p] = a[i];
            i++;
            p++;
        }
        while (j <= r) {
            t[p] = a[j];
            j++;
            p++;
        }
        for (int q = l; q <= r; q++) {
            a[q] = t[q];
        }
        return k;
    }
}
