/*
 * Задача 36. Бегущая сумма.
 * Вход: массив из n целых чисел.
 * Выход: массив сумм первых i + 1 элементов.
 */

package ru.bmstu.lab01.task_36;

import java.util.Scanner;

/** Решение задачи 36. */
public class Task36 {

    /**
     * Считывает массив и выводит его бегущую сумму.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numberOfElements = input.nextInt();
        int[] numbers = new int[numberOfElements];
        for (int index = 0; index < numberOfElements; index++) {
            numbers[index] = input.nextInt();
        }
        long[] result = calculateRunningSum(numbers);
        for (int index = 0; index < result.length; index++) {
            if (index > 0) {
                System.out.print(" ");
            }
            System.out.print(result[index]);
        }
        System.out.println();
    }

    /**
     * Создаёт массив бегущих сумм.
     *
     * @param numbers массив целых чисел
     * @return массив бегущих сумм
     */
    public static long[] calculateRunningSum(int[] numbers) {
        long[] result = new long[numbers.length];
        long sum = 0;
        for (int index = 0; index < numbers.length; index++) {
            sum = sum + numbers[index];
            result[index] = sum;
        }
        return result;
    }
}
