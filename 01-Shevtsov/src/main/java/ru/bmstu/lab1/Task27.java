
package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 27: вычисление суммы квадратов элементов массива.
 */
public class Task27 {

    /**
     * Вычисляет сумму квадратов элементов массива.
     *
     * @param numbers массив целых чисел
     * @return сумма квадратов элементов массива; для пустого массива возвращает 0
     */
    public static long sumOfSquares(int[] numbers) {
        long result = 0;

        for (int number : numbers) {
            result += (long) number * number;
        }

        return result;
    }

    /**
     * Считывает массив и выводит сумму квадратов его элементов.
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

        System.out.println(sumOfSquares(numbers));
    }
}
