package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 25: вычисление суммы элементов массива.
 */
public class Task25 {

    /**
     * Вычисляет сумму элементов целочисленного массива.
     *
     * @param numbers массив целых чисел; пустой массив допустим
     * @return сумма элементов массива или 0, если массив пуст
     */
    public static long sum(int[] numbers) {
        long result = 0;

        for (int number : numbers) {
            result += number;
        }

        return result;
    }

    /**
     * Считывает массив из стандартного ввода и выводит сумму его элементов.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.println(sum(numbers));
    }
}
