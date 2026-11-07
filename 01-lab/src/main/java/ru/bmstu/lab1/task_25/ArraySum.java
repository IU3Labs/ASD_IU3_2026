package ru.bmstu.lab1.task_25;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 25. Сумма элементов.
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Складывает все числа массива.
 */
public class ArraySum {

    /**
     * Возвращает сумму всех элементов.
     */
    public static long sumAll(int[] numbers) {
        long sum = 0;
        for (int i = 0; i < numbers.length; i++) {
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

        System.out.println(sumAll(numbers));
    }
}
