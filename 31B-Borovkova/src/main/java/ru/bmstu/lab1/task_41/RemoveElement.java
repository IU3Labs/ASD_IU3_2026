/*Задача 41. Удаление элемента
Вход: массив из (n) целых чисел и число (x).
Выход: массив, из которого удалены все вхождения (x).
Сложность: базовая */

package ru.bmstu.lab1.task_41;

import java.util.Scanner;

/**
 * Решение задачи 41: удаление всех вхождений x сдвигом остальных элементов на место.
 */
public class RemoveElement {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и x, печатает через пробел элементы, не равные x,
     * в исходном порядке. Если удалены все элементы, печатает пустую строку.
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
            int x = scanner.nextInt();

            // k — сколько элементов уже оставлено; каждый элемент, не равный x,
            // переписывается на позицию k. Порядок оставшихся сохраняется
            int k = 0;
            for (int i = 0; i < n; i++) {
                if (a[i] != x) {
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
