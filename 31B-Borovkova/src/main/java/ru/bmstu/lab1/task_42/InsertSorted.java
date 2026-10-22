/*Задача 42. Вставка элемента в отсортированный массив
Вход: отсортированный по неубыванию массив из (n) целых чисел и число (x).
Выход: массив длины (n + 1), в который (x) вставлен на правильную позицию.
Требование: использовать бинарный поиск для нахождения позиции.
Сложность: средняя */

package ru.bmstu.lab1.task_42;

import java.util.Scanner;

/**
 * Решение задачи 42: вставка x в отсортированный массив, позиция ищется бинарным поиском.
 */
public class InsertSorted {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел по неубыванию и x, печатает через пробел
     * массив из n + 1 элемента с x на правильной позиции.
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

            // Сразу берём массив длины n + 1 — последняя ячейка понадобится при вставке
            int[] a = new int[n + 1];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
                if (i > 0 && a[i] < a[i - 1]) {
                    System.out.println("Ошибка: массив должен быть отсортирован по неубыванию");
                    return;
                }
            }

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int x = scanner.nextInt();

            // Бинарный поиск первой позиции, где элемент больше x (x встаёт после равных ему).
            // Инвариант: все a[0..l-1] <= x, все a[r..n-1] > x
            int l = 0;
            int r = n;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (a[m] <= x) {
                    l = m + 1;
                } else {
                    r = m;
                }
            }

            // Сдвигаем хвост a[l..n-1] на одну позицию вправо и ставим x на место l
            for (int i = n; i > l; i--) {
                a[i] = a[i - 1];
            }
            a[l] = x;

            // Печатаем n + 1 элемент через пробел
            for (int i = 0; i <= n; i++) {
                System.out.print(a[i]);
                if (i < n) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
