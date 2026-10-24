/*
 * Задача 2. Индекс первого вхождения.
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x, или -1.
 */

package ru.bmstu.lab01.task_2;

import java.util.Scanner;

/** Решение задачи 2. */
public class Task2 {

    /**
     * Считывает массив и число, затем выводит индекс первого вхождения.
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
        int value = input.nextInt();
        System.out.println(findFirstIndex(numbers, value));
    }

    /**
     * Находит индекс первого вхождения числа в массив.
     *
     * @param numbers массив целых чисел
     * @param value искомое число
     * @return индекс первого вхождения или -1
     */
    public static int findFirstIndex(int[] numbers, int value) {
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] == value) {
                return index;
            }
        }
        return -1;
    }
}
