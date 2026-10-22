/*Задача 26. Произведение элементов
Вход: массив из (n) целых чисел.
Выход: произведение всех элементов по модулю (10^9 + 7).
Сложность: базовая */

package ru.bmstu.lab1.task_26;

import java.util.Scanner;

/**
 * Решение задачи 26: произведение элементов по модулю 10^9 + 7.
 */
public class ArrayProduct {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Модуль, по которому считается произведение. */
    private static final long MOD = 1_000_000_007L;

    /**
     * Читает n, n целых чисел и печатает их произведение по модулю 10^9 + 7.
     * Ответ всегда неотрицательный: от 0 до 10^9 + 6.
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

            // Массив не храним: после каждого умножения берём остаток.
            // p < MOD и x < MOD, поэтому p · x < 10^18 < 2^63 — помещается в long
            long p = 1;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                // Остаток отрицательного числа в Java отрицательный — сдвигаем его в [0, MOD)
                long x = scanner.nextInt() % MOD;
                if (x < 0) {
                    x = x + MOD;
                }
                p = p * x % MOD;
            }

            System.out.println(p);
        }
    }
}
