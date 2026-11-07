package ru.bmstu.lab1.task_14;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 14. Сортировка вставками.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по невозрастанию.
 * Сортировку нужно написать самому.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: числа через пробел.
 */

/**
 * Сортирует массив вставками по невозрастанию.
 */
public class InsertionSort {

    /**
     * Вставляет каждое число так, чтобы слева оставались большие.
     */
    public static int[] sortDescending(int[] numbers) {
        for (int i = 1; i < numbers.length; i++) {
            int current = numbers[i];
            int j = i - 1;
            while (j >= 0 && numbers[j] < current) {
                numbers[j + 1] = numbers[j];
                j--;
            }
            numbers[j + 1] = current;
        }
        return numbers;
    }

    /**
     * Читает массив, сортирует его и печатает.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        sortDescending(numbers);
        ArrayIO.printNumbers(numbers);
    }
}
