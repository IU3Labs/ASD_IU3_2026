package ru.bmstu.lab1.task_3;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 3. Подсчёт вхождений.
 * Вход: массив из n целых чисел и число x.
 * Выход: количество элементов, равных x.
 *
 * Ввод:
 * n x
 * a1 a2 ... an
 */

/**
 * Считает, сколько раз число встретилось в массиве.
 */
public class CountMatches {

    /**
     * Возвращает, сколько элементов равны x.
     */
    public static int countMatches(int[] numbers, int target) {
        int count = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == target) {
                count++;
            }
        }
        return count;
    }

    /**
     * Читает массив и число x, печатает количество.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int target = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        System.out.println(countMatches(numbers, target));
    }
}
