package ru.bmstu.lab1.task_1;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: минимум и максимум через пробел.
 */

/**
 * Ищет в массиве самое маленькое и самое большое число.
 */
public class MinAndMax {

    /**
     * Возвращает два числа: сначала минимум, потом максимум.
     */
    public static int[] findMinAndMax(int[] numbers) {
        int minimum = numbers[0];
        int maximum = numbers[0];

        for (int i = 1; i < numbers.length; i++) {
            if (numbers[i] < minimum) {
                minimum = numbers[i];
            }
            if (numbers[i] > maximum) {
                maximum = numbers[i];
            }
        }

        return new int[] {minimum, maximum};
    }

    /**
     * Читает массив и печатает минимум и максимум.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        int[] answer = findMinAndMax(numbers);
        System.out.println(answer[0] + " " + answer[1]);
    }
}
