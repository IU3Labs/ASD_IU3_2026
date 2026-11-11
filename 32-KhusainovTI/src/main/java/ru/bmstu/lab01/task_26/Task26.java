/*
 * Задача 26. Произведение элементов.
 * Вход: массив из n целых чисел.
 * Выход: произведение всех элементов по модулю 10^9 + 7.
 */

package ru.bmstu.lab01.task_26;

import java.util.Scanner;

/** Решение задачи 26. */
public class Task26 {

    private static final long MODULUS = 1_000_000_007L;

    /**
     * Считывает массив и выводит произведение его элементов по модулю.
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
        System.out.println(calculateProduct(numbers));
    }

    /**
     * Вычисляет произведение элементов по модулю 10^9 + 7.
     *
     * @param numbers массив целых чисел
     * @return произведение элементов по модулю 10^9 + 7
     */
    public static long calculateProduct(int[] numbers) {
        long product = 1;
        for (int index = 0; index < numbers.length; index++) {
            product = product * numbers[index] % MODULUS;
        }
        if (product < 0) {
            product = product + MODULUS;
        }
        return product;
    }
}
