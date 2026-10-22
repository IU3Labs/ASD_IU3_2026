/*Задача 32. Сумма произведений пар
Вход: массив из (n) целых чисел.
Выход: сумма произведений всех пар элементов (a_i * a_j) для (i < j).
Требование: O(n)
Сложность: высокая */

package ru.bmstu.lab1.task_32;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Решение задачи 32: сумма a_i · a_j по всем i < j за O(n) через сумму предыдущих элементов.
 */
public class PairProductsSum {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает сумму произведений a_i · a_j по всем парам i < j.
     * Пары не перебираются: каждое a_j умножается сразу на сумму всех элементов до него.
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

            // Σ_{i<j} a_i·a_j = Σ_j a_j · (a_0 + … + a_{j-1}).
            // p — сумма элементов до текущего (не больше n · 2^31 < 2^48, помещается в long),
            // а a_j · p — до 2^79, поэтому произведения и итог считаем в BigInteger
            long p = 0;
            BigInteger s = BigInteger.ZERO;
            for (int j = 0; j < n; j++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                int x = scanner.nextInt();
                s = s.add(BigInteger.valueOf(p).multiply(BigInteger.valueOf(x)));
                p = p + x;
            }

            System.out.println(s);
        }
    }
}
