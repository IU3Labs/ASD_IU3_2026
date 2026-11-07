package ru.bmstu.lab1.task_27;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 27. Сумма квадратов.
 * Вход: массив из n целых чисел.
 * Выход: сумма квадратов всех элементов.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 */

/**
 * Складывает квадраты всех чисел.
 */
public class SumOfSquares {

    /**
     * Возвращает сумму квадратов.
     */
    public static long sumOfSquares(int[] numbers) {
        long sum = 0;
        for (int i = 0; i < numbers.length; i++) {
            // Сначала привожу к long, иначе квадрат большого числа не влезет в int.
            long value = numbers[i];
            sum = sum + value * value;
        }
        return sum;
    }

    /**
     * Читает массив и печатает сумму квадратов.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(sumOfSquares(numbers));
    }
}
