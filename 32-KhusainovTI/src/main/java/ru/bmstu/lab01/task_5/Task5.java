/*
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами.
 */

package ru.bmstu.lab01.task_5;

import java.util.Scanner;

/** Решение задачи 5. */
public class Task5 {

    /**
     * Считывает массив и выводит сумму элементов с чётными индексами.
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
        System.out.println(calculateSumAtEvenIndexes(numbers));
    }

    /**
     * Вычисляет сумму элементов с чётными индексами.
     *
     * @param numbers массив целых чисел
     * @return сумма элементов с индексами 0, 2, 4 и так далее
     */
    public static long calculateSumAtEvenIndexes(int[] numbers) {
        long sum = 0;
        for (int index = 0; index < numbers.length; index += 2) {
            sum = sum + numbers[index];
        }
        return sum;
    }
}
