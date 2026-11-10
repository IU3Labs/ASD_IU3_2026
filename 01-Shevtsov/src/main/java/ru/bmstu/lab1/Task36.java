package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 36: вычисление префиксных сумм массива.
 */
public class Task36 {

    /**
     * Вычисляет префиксные суммы элементов массива.
     *
     * @param numbers исходный массив целых чисел
     * @return массив, в котором каждый элемент равен сумме элементов
     *         исходного массива от начала до соответствующей позиции
     */
    public static long[] prefixSums(int[] numbers) {
        long[] result = new long[numbers.length];
        long sum = 0;

        for (int i = 0; i < numbers.length; i++) {
            sum += numbers[i];
            result[i] = sum;
        }

        return result;
    }

    /**
     * Считывает массив и выводит его префиксные суммы.
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

        long[] result = prefixSums(numbers);

        for (int i = 0; i < result.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(result[i]);
        }
        System.out.println();
    }
}
