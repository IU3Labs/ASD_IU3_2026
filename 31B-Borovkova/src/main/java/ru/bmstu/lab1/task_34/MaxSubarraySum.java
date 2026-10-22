/*Задача 34. Максимальная сумма подмассива
Вход: массив из (n) целых чисел (могут быть отрицательные).
Выход: максимальная сумму непрерывного подмассива.
Требование: (O(n)) (алгоритм Кадане).
Сложность: высокая */

package ru.bmstu.lab1.task_34;

import java.util.Scanner;

/**
 * Решение задачи 34: максимальная сумма непустого непрерывного подмассива алгоритмом Кадане.
 */
public class MaxSubarraySum {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает максимальную сумму непустого непрерывного подмассива.
     * Если все числа отрицательные, ответ — наибольшее из них.
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

            // cur — лучшая сумма подмассива, заканчивающегося на текущем элементе;
            // best — лучшая сумма среди всех уже просмотренных. Суммы в long: до n · 2^31
            long cur = 0;
            long best = 0;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int x = scanner.nextInt();
                // Либо продолжаем предыдущий подмассив, либо начинаем новый с x —
                // продолжать выгодно, только если накопленная сумма положительна
                if (i == 0 || cur < 0) {
                    cur = x;
                } else {
                    cur = cur + x;
                }
                if (i == 0 || cur > best) {
                    best = cur;
                }
            }

            System.out.println(best);
        }
    }
}
