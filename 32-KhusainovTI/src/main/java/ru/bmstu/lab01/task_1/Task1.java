/*
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 */

package ru.bmstu.lab01.task_1;

import java.util.Scanner;

/** Решение задачи 1. */
public class Task1 {

    /**
     * Считывает массив и выводит минимальный и максимальный элементы.
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
        int[] result = findMinAndMax(numbers);
        System.out.println(result[0] + " " + result[1]);
    }

    /**
     * Находит минимальный и максимальный элементы массива.
     *
     * @param numbers массив целых чисел
     * @return массив из минимального и максимального элементов
     */
    public static int[] findMinAndMax(int[] numbers) {
        int minimum = numbers[0];
        int maximum = numbers[0];
        for (int index = 1; index < numbers.length; index++) {
            if (numbers[index] < minimum) {
                minimum = numbers[index];
            }
            if (numbers[index] > maximum) {
                maximum = numbers[index];
            }
        }
        return new int[]{minimum, maximum};
    }
}
