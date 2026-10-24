package ru.bmstu.lab1.task_5;

import java.util.Scanner;

/**
 * Задача 5. Сумма элементов с чётными индексами.
 */
public class Main {

    /**
     * Читает массив и выводит сумму элементов с чётными индексами.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int size = scanner.nextInt();
        int[] numbers = new int[size];

        for (int i = 0; i < size; i++) {
            numbers[i] = scanner.nextInt();
        }

        System.out.println(calculateEvenIndexSum(numbers));
    }

    /**
     * Складывает элементы с индексами 0, 2, 4 и далее.
     *
     * @param numbers массив целых чисел
     * @return сумма элементов с чётными индексами
     */
    public static long calculateEvenIndexSum(int[] numbers) {
        long sum = 0;

        for (int i = 0; i < numbers.length; i += 2) {
            sum += numbers[i];
        }

        return sum;
    }
}