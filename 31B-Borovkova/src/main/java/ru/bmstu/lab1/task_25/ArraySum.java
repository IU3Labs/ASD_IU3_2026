/*Задача 25. Сумма элементов
Вход: массив из (n) целых чисел.
Выход: сумма всех элементов.
Сложность: базовая */

package ru.bmstu.lab1.task_25;

import java.util.Scanner;

/**
 * Решение задачи 25: сумма всех элементов массива в long.
 */
public class ArraySum {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает их сумму.
     * Сумма хранится в long: n · 2^31 < 2^48, переполнения нет.
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

            // Массив не храним: каждое число сразу прибавляем к сумме
            long s = 0;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                s = s + scanner.nextInt();
            }

            System.out.println(s);
        }
    }
}
