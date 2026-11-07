package ru.bmstu.lab1.task_15;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 15. Пузырьковая сортировка с подсчётом обменов.
 * Вход: массив из n целых чисел.
 * Выход: отсортированный массив и количество обменов.
 * Если массив уже отсортирован, сортировка останавливается раньше.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: сначала числа через пробел, на следующей строке — число обменов.
 */

/**
 * Сортирует массив пузырьком и считает обмены.
 */
public class BubbleSort {

    /**
     * Сортирует массив по неубыванию и возвращает, сколько было обменов.
     */
    public static int sortAndCountSwaps(int[] numbers) {
        int swaps = 0;
        for (int pass = 0; pass < numbers.length - 1; pass++) {
            boolean swapped = false;
            for (int i = 0; i < numbers.length - 1 - pass; i++) {
                if (numbers[i] > numbers[i + 1]) {
                    int temporary = numbers[i];
                    numbers[i] = numbers[i + 1];
                    numbers[i + 1] = temporary;
                    swaps++;
                    swapped = true;
                }
            }
            // Если за проход не было обменов, массив уже стоит по порядку.
            if (!swapped) {
                break;
            }
        }
        return swaps;
    }

    /**
     * Читает массив, печатает его после сортировки и число обменов.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        int swaps = sortAndCountSwaps(numbers);
        ArrayIO.printNumbers(numbers);
        System.out.println(swaps);
    }
}
