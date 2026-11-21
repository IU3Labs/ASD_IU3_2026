/*Задача 39. Циклический сдвиг вправо
Вход: массив из (n) целых чисел и число (k).
Выход: массив, циклически сдвинутый вправо на (k) позиций.
Требование: учесть, что (k) может быть больше (n).
Сложность: средняя */

package ru.bmstu.lab1.task_39;

import java.util.Scanner;

/**
 * Решение задачи 39: циклический сдвиг вправо на k позиций (k mod n) тройным разворотом.
 */
public class ShiftRight {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и k >= 0 (k может быть больше n), сдвигает массив
     * циклически вправо на k позиций и печатает его через пробел.
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

            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            int k = scanner.nextInt();
            if (k < 0) {
                System.out.println("Ошибка: k не должно быть отрицательным");
                return;
            }

            // k может быть больше n: каждые n сдвигов возвращают массив в исходное состояние,
            // поэтому достаточно сдвинуть на k mod n
            k = k % n;

            // Тройной разворот: весь массив, затем первые k, затем остальные.
            // [A B], где B — последние k элементов -> [B' A'] -> [B A]
            reverse(a, 0, n - 1);
            reverse(a, 0, k - 1);
            reverse(a, k, n - 1);

            // Печатаем массив через пробел
            for (int i = 0; i < n; i++) {
                System.out.print(a[i]);
                if (i < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Разворачивает на месте отрезок a[l..r]. Если l >= r, ничего не делает.
     *
     * @param a массив
     * @param l левая граница (включительно)
     * @param r правая граница (включительно)
     */
    private static void reverse(int[] a, int l, int r) {
        while (l < r) {
            int t = a[l];
            a[l] = a[r];
            a[r] = t;
            l++;
            r--;
        }
    }
}
