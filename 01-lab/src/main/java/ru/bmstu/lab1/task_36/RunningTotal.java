package ru.bmstu.lab1.task_36;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 36. Бегущая сумма.
 * Вход: массив из n целых чисел.
 * Выход: массив той же длины, где i-й элемент равен сумме первых i + 1 элементов.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: числа через пробел.
 */

/**
 * Строит массив бегущих сумм.
 */
public class RunningTotal {

    /**
     * Каждый следующий элемент — это предыдущая сумма плюс новое число.
     */
    public static long[] runningTotal(int[] numbers) {
        long[] totals = new long[numbers.length];
        totals[0] = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            totals[i] = totals[i - 1] + numbers[i];
        }
        return totals;
    }

    /**
     * Читает массив и печатает бегущие суммы.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        ArrayIO.printNumbers(runningTotal(numbers));
    }
}
