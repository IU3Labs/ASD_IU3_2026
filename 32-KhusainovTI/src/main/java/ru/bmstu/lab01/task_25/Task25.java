/*
 * Задача 25. Сумма элементов.
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 */

package ru.bmstu.lab01.task_25;

import java.util.Scanner;

/** Решение задачи 25. */
public class Task25 {

    /**
     * Считывает массив и выводит сумму его элементов.
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
        long answer = calculateSum(numbers);
        System.out.println(answer);
    }

    /**
     * Вычисляет сумму всех элементов массива.
     *
     * @param numbers массив целых чисел
     * @return сумма элементов массива
     */
    public static long calculateSum(int[] numbers) {
        long sum = 0;
        for (int index = 0; index < numbers.length; index++) {
            sum = sum + numbers[index];
        }
        return sum;
    }
}
