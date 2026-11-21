/*Задача 17. Сортировка по сумме цифр
Вход: массив из (n) неотрицательных целых чисел.
Выход: массив, отсортированный по возрастанию суммы цифр каждого числа. При равных суммах —
сохранить исходный порядок.
Сложность: средняя */

package ru.bmstu.lab1.task_17;

import java.util.Scanner;

/**
 * Решение задачи 17: устойчивая сортировка подсчётом по сумме цифр.
 */
public class DigitSumSort {
    /** Наименьшая допустимая длина массива (по условию n > 1). */
    private static final int MIN_LENGTH = 2;
    /** Наибольшая допустимая длина массива (по условию n < 10^5). */
    private static final int MAX_LENGTH = 99_999;
    /** Верхняя граница суммы цифр числа типа int: не больше 10 цифр по 9. */
    private static final int MAX_DIGIT_SUM = 90;

    /**
     * Читает n, n неотрицательных целых чисел, печатает их через пробел
     * по возрастанию суммы цифр; числа с равной суммой цифр идут в исходном порядке.
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

        // Читаем массив и сразу считаем сумму цифр каждого числа
        int[] a = new int[n];
        int[] s = new int[n];
        for (int i = 0; i < n; i++) {
            if (!scanner.hasNextInt()) {
                System.out.println("Ошибка: должно быть целое число!");
                return;
            }
            a[i] = scanner.nextInt();
            if (a[i] < 0) {
                System.out.println("Ошибка: числа должны быть неотрицательными");
                return;
            }
            s[i] = digitSum(a[i]);
        }

        // count[d] — сколько чисел имеют сумму цифр d
        int[] count = new int[MAX_DIGIT_SUM + 1];
        for (int i = 0; i < n; i++) {
            count[s[i]]++;
        }
        // start[d] — позиция в ответе, с которой начинаются числа с суммой цифр d
        int[] start = new int[MAX_DIGIT_SUM + 1];
        for (int d = 1; d <= MAX_DIGIT_SUM; d++) {
            start[d] = start[d - 1] + count[d - 1];
        }
        // Раскладываем числа слева направо — так равные суммы сохраняют исходный порядок
        int[] b = new int[n];
        for (int i = 0; i < n; i++) {
            b[start[s[i]]] = a[i];
            start[s[i]]++;
        }

        // Печатаем массив через пробел
        for (int i = 0; i < n; i++) {
            System.out.print(b[i]);
            if (i < n - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();

        scanner.close();
    }

    /**
     * Считает сумму десятичных цифр неотрицательного числа.
     *
     * @param x неотрицательное число
     * @return сумма цифр x
     */
    private static int digitSum(int x) {
        int sum = 0;
        while (x > 0) {
            sum = sum + x % 10;
            x = x / 10;
        }
        return sum;
    }
}
