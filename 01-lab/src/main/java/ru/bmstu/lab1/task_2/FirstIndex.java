package ru.bmstu.lab1.task_2;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 2. Индекс первого вхождения.
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x. Если такого нет — -1.
 *
 * Ввод:
 * n x
 * a1 a2 ... an
 */

/**
 * Ищет, на каком месте число встречается первый раз.
 */
public class FirstIndex {

    /**
     * Возвращает индекс первого x или -1, если числа в массиве нет.
     */
    public static int findFirstIndex(int[] numbers, int target) {
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Читает массив и число x, печатает индекс.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int target = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(findFirstIndex(numbers, target));
    }
}
