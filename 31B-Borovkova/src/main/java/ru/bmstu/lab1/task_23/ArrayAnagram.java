/*Задача 23. Проверка анаграммы массивов
Вход: два массива одинаковой длины (n).
Выход: YES, если один массив является перестановкой другого; NO иначе.
Сложность: средняя */

package ru.bmstu.lab1.task_23;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Решение задачи 23: проверка, что два массива — перестановки друг друга, через сортировку.
 */
public class ArrayAnagram {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, затем два массива по n целых чисел. Печатает YES, если второй массив
     * является перестановкой первого, и NO иначе.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            System.out.println("Ошибка: должно быть целое число!");
            return;
        }
        int n = scanner.nextInt();
        if (n < MIN_LENGTH || n > MAX_LENGTH) {
            System.out.println("Ошибка: некорректная длина массива");
            return;
        }

        // Читаем первый массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
        }

        // Читаем второй массив той же длины
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            b[i] = scanner.nextInt();
        }

        // Перестановки друг друга после сортировки совпадают поэлементно
        Arrays.sort(a);
        Arrays.sort(b);
        boolean f = true;
        for (int i = 0; i < n; i++) {
            if (a[i] != b[i]) {
                f = false;
                break;
            }
        }

        if (f) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        scanner.close();
    }
}
