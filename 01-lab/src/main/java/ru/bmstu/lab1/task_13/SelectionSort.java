package ru.bmstu.lab1.task_13;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 13. Сортировка выбором.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию.
 * Сортировку нужно написать самому, без готовой функции сортировки.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: числа через пробел.
 */

/**
 * Сортирует массив выбором по неубыванию.
 */
public class SelectionSort {

    /**
     * На каждом шаге ставит очередной минимум на своё место.
     */
    public static int[] sortAscending(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < numbers.length; j++) {
                if (numbers[j] < numbers[minIndex]) {
                    minIndex = j;
                }
            }
            int temporary = numbers[i];
            numbers[i] = numbers[minIndex];
            numbers[minIndex] = temporary;
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

        sortAscending(numbers);
        ArrayIO.printNumbers(numbers);
    }
}
