/*Задача 27. Сумма квадратов
Вход: массив из (n) целых чисел.
Выход: сумма квадратов всех элементов.
Сложность: базовая */

package ru.bmstu.lab1.task_27;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Решение задачи 27: сумма квадратов элементов; итог в BigInteger, так как он может превысить long.
 */
public class SumOfSquares {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;

    /**
     * Читает n, n целых чисел и печатает сумму их квадратов.
     * Квадрат одного числа помещается в long (не больше 2^62), а сумма квадратов —
     * уже нет (до n · 2^62), поэтому она накапливается в BigInteger.
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

            // Массив не храним: квадрат каждого числа сразу прибавляем к сумме
            BigInteger s = BigInteger.ZERO;
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Ошибка: должно быть целое число!");
                    return;
                }
                long x = scanner.nextInt();
                s = s.add(BigInteger.valueOf(x * x));
            }

            System.out.println(s);
        }
    }
}
