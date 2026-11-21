/*Задача 38. Циклический сдвиг влево
Вход: массив из (n) целых чисел и число (k).
Выход: массив, циклически сдвинутый влево на (k) позиций.
Требование: (O(n)), (O(1)) доп. памяти (через тройной разворот).
Сложность: средняя */

package ru.bmstu.lab1.task_38;

import java.util.Scanner;

/**
 * Решение задачи 38: циклический сдвиг влево на k позиций тройным разворотом.
 */
public class ShiftLeft {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и k >= 0, сдвигает массив циклически влево на k позиций
     * и печатает его через пробел.
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

            // Сдвиг на n возвращает массив в исходное состояние, поэтому важен только k mod n
            k = k % n;

            // Тройной разворот: развернуть первые k, затем остальные, затем весь массив.
            // [A B] -> [A' B'] -> ([A' B'])' = [B A] — это и есть сдвиг влево на k
            reverse(a, 0, k - 1);
            reverse(a, k, n - 1);
            reverse(a, 0, n - 1);

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
