/*Задача 40. Удаление дубликатов
Вход: отсортированный по неубыванию массив из (n) целых чисел.
Выход: массив без дубликатов, сохраняя порядок.
Требование: (O(n)), модификация исходного массива на месте.
Сложность: средняя */

package ru.bmstu.lab1.task_40;

import java.util.Scanner;

/**
 * Решение задачи 40: удаление дубликатов из отсортированного массива на месте двумя указателями.
 */
public class RemoveDuplicates {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n и n целых чисел по неубыванию, удаляет повторы на месте
     * и печатает через пробел оставшиеся различные значения.
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

            // Читаем массив и проверяем, что он неубывающий
            int[] a = new int[n];
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

            // k — длина уже готовой части без дубликатов a[0..k-1].
            // В отсортированном массиве повтор всегда равен последнему записанному a[k-1]
            int k = 1;
            for (int i = 1; i < n; i++) {
                if (a[i] != a[k - 1]) {
                    a[k] = a[i];
                    k++;
                }
            }

            // Печатаем первые k элементов через пробел
            for (int i = 0; i < k; i++) {
                System.out.print(a[i]);
                if (i < k - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
