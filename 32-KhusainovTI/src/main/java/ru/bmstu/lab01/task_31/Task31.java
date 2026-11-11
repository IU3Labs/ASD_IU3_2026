/*
 * Задача 31. Скалярное произведение.
 * Вход: два массива одинаковой длины n.
 * Выход: скалярное произведение.
 */

package ru.bmstu.lab01.task_31;

import java.util.Scanner;

/** Решение задачи 31. */
public class Task31 {

    /**
     * Считывает два массива и выводит их скалярное произведение.
     *
     * @param args аргументы командной строки не используются
     */
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int numberOfElements = input.nextInt();
        int[] firstNumbers = new int[numberOfElements];
        int[] secondNumbers = new int[numberOfElements];
        for (int index = 0; index < numberOfElements; index++) {
            firstNumbers[index] = input.nextInt();
        }
        for (int index = 0; index < numberOfElements; index++) {
            secondNumbers[index] = input.nextInt();
        }
        System.out.println(calculateScalarProduct(firstNumbers, secondNumbers));
    }

    /**
     * Вычисляет скалярное произведение двух массивов.
     *
     * @param firstNumbers первый массив
     * @param secondNumbers второй массив
     * @return скалярное произведение
     */
    public static long calculateScalarProduct(int[] firstNumbers, int[] secondNumbers) {
        long result = 0;
        for (int index = 0; index < firstNumbers.length; index++) {
            result = result + (long) firstNumbers[index] * secondNumbers[index];
        }
        return result;
    }
}
