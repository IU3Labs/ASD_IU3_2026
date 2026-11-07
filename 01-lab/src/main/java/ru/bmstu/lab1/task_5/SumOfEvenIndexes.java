package ru.bmstu.lab1.task_5;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами (0, 2, 4, ...).
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Складывает элементы, которые стоят на чётных местах.
 */
public class SumOfEvenIndexes {

    /**
     * Возвращает сумму чисел с индексами 0, 2, 4 и так далее.
     */
    public static long sumEvenIndexes(int[] numbers) {
        // Сумма может не влезть в int, поэтому считаю в long.
        long sum = 0;
        for (int i = 0; i < numbers.length; i = i + 2) {
            sum = sum + numbers[i];
        }
        return sum;
    }

    /**
     * Читает массив и печатает сумму.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(sumEvenIndexes(numbers));
    }
}
