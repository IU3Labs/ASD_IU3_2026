/*Задача 20. Сортировка подсчётом
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию.
Требование: реализовать сортировку подсчётом.
Сложность: средняя */

package ru.bmstu.lab1.task_20;

import java.util.Scanner;

/**
 * Решение задачи 20: сортировка подсчётом по диапазону значений [min, max].
 */
public class CountingSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Наибольший допустимый размер диапазона значений max - min + 1 (размер массива счётчиков). */
    private static final int MAX_RANGE = 1_000_000;

    /**
     * Читает n, n целых чисел, сортирует их подсчётом по неубыванию и печатает через пробел.
     * Если разброс значений больше MAX_RANGE, печатает ошибку.
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

        // Читаем массив и сразу ищем минимум и максимум
        int[] a = new int[n];
        int min = 0;
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
            if (i == 0 || a[i] < min) {
                min = a[i];
            }
            if (i == 0 || a[i] > max) {
                max = a[i];
            }
        }

        // Разность max - min может не поместиться в int, поэтому считаем в long
        long range = (long) max - min + 1;
        if (range > MAX_RANGE) {
            System.out.println("Ошибка: слишком большой диапазон значений");
            return;
        }

        // count[v - min] — сколько раз встречается значение v
        int[] count = new int[(int) range];
        for (int i = 0; i < n; i++) {
            count[a[i] - min]++;
        }

        // Выписываем значения по возрастанию столько раз, сколько они встречались
        int k = 0;
        for (int v = 0; v < range; v++) {
            for (int c = 0; c < count[v]; c++) {
                a[k] = v + min;
                k++;
            }
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
