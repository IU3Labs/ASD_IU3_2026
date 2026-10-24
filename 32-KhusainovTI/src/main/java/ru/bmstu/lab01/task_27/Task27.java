/*
 * Задача 27. Сумма квадратов.
 * Вход: массив из n целых чисел.
 * Выход: сумма квадратов всех элементов.
 */

package ru.bmstu.lab01.task_27;

import java.util.Scanner;

/** Решение задачи 27. */
public class Task27 {

    /**
     * Считывает массив и выводит сумму квадратов его элементов.
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
        System.out.println(calculateSumOfSquares(numbers));
    }

    /**
     * Вычисляет сумму квадратов элементов массива.
     *
     * @param numbers массив целых чисел
     * @return сумма квадратов
     */
    public static long calculateSumOfSquares(int[] numbers) {
        long sum = 0;
        for (int index = 0; index < numbers.length; index++) {
            long number = numbers[index];
            sum = sum + number * number;
        }
        return sum;
    }
}
