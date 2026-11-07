package ru.bmstu.lab1.task_6;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 6. Элементы больше среднего.
 * Вход: массив из n целых чисел.
 * Выход: количество элементов, строго больших среднего арифметического.
 * Среднее гарантированно целое.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Считает, сколько чисел строго больше среднего.
 */
public class CountAboveAverage {

    /**
     * Возвращает количество элементов больше среднего арифметического.
     */
    public static int countAboveAverage(int[] numbers) {
        long sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            sum = sum + numbers[i];
        }

        long average = sum / numbers.length;
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > average) {
                count++;
            }
        }
        return count;
    }

    /**
     * Читает массив и печатает количество.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(countAboveAverage(numbers));
    }
}
