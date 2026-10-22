/*Задача 14. Сортировка вставками
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по невозрастанию.
Требование: реализовать сортировку вставками вручную.
Сложность: базовая */

package ru.bmstu.lab1.task_14;

import java.util.Scanner;

/**
 * Решение задачи 14: сортировка массива по невозрастанию вставками, реализованная вручную.
 */
public class InsertionSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел, сортирует их вставками по невозрастанию и печатает через пробел.
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

        // Читаем сам массив
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
        }

        // a[0..i-1] уже отсортирован по невозрастанию: сдвигаем вправо всё,
        // что меньше x, и вставляем x на освободившееся место
        for (int i = 1; i < n; i++) {
            int x = a[i];
            int j = i - 1;
            while (j >= 0 && a[j] < x) {
                a[j + 1] = a[j];
                j--;
            }
            a[j + 1] = x;
        }

        // Печатаем массив через пробел
        for (int i = 0; i < n; i++) {
            System.out.print(a[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }
}
