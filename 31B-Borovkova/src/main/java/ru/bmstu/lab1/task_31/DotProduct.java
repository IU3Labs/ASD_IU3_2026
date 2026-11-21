/*Задача 31. Скалярное произведение
Вход: два массива одинаковой длины (n).
Выход: скалярное произведение
Сложность: базовая */

package ru.bmstu.lab1.task_31;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Решение задачи 31: скалярное произведение a · b = Σ a_i · b_i.
 */
public class DotProduct {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, затем два массива по n целых чисел и печатает их скалярное произведение.
     * Одно произведение a_i · b_i помещается в long, а их сумма — уже нет,
     * поэтому сумма накапливается в BigInteger.
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

            // Первый массив нужно сохранить: второй вводится только после него
            int[] a = new int[n];
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                a[i] = scanner.nextInt();
            }

            // Второй массив не храним: b_i сразу умножаем на a_i и прибавляем к сумме
            BigInteger s = BigInteger.ZERO;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                long b = scanner.nextInt();
                s = s.add(BigInteger.valueOf(a[i] * b));
            }

            System.out.println(s);
        }
    }
}
