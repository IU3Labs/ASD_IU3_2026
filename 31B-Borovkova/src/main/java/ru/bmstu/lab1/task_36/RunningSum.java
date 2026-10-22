/*Задача 36. Бегущая сумма
Вход: массив из (n) целых чисел.
Выход: массив той же длины, где (i)-й элемент равен сумме первых (i + 1) элементов исходного массива.
Сложность: базовая */

package ru.bmstu.lab1.task_36;

import java.util.Scanner;

/**
 * Решение задачи 36: бегущая (накопленная) сумма массива.
 */
public class RunningSum {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает через пробел массив s, где s[i] = a[0] + … + a[i].
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

            // s[i] = s[i-1] + a[i]: каждое число сразу прибавляем к предыдущей сумме.
            // Суммы в long, так как сумма многих int может не поместиться в int
            long[] s = new long[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int x = scanner.nextInt();
                if (i == 0) {
                    s[i] = x;
                } else {
                    s[i] = s[i - 1] + x;
                }
            }

            // Печатаем суммы через пробел
            for (int i = 0; i < n; i++) {
                System.out.print(s[i]);
                if (i < n - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }
}
