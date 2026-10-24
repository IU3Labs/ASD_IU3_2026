/*
 * Задача 3. Подсчёт вхождений.
 * Вход: массив из n целых чисел и число x.
 * Выход: количество элементов, равных x.
 */

package ru.bmstu.lab01.task_3;

import java.util.Scanner;

/** Решение задачи 3. */
public class Task3 {

    /**
     * Считывает массив и число, затем выводит количество вхождений.
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
        System.out.println(countOccurrences(numbers, value));
    }

    /**
     * Считает количество вхождений числа в массив.
     *
     * @param numbers массив целых чисел
     * @param value искомое число
     * @return количество вхождений
     */
    public static int countOccurrences(int[] numbers, int value) {
        int count = 0;
        for (int index = 0; index < numbers.length; index++) {
            if (numbers[index] == value) {
                count++;
            }
        }
        return count;
    }
}
