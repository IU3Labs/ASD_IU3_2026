package ru.bmstu.lab1.task_8;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIO;

/*
 * Задача 8. Фильтрация по знаку.
 * Вход: массив из n целых чисел.
 * Выход: два массива — положительные и отрицательные, в исходном порядке.
 * Нули не входят ни в один.
 *
 * Ввод:
 * n
 * a1 a2 ... an
 * Вывод: первая строка — положительные, вторая — отрицательные.
 */

/**
 * Разделяет числа на положительные и отрицательные.
 */
public class SplitBySign {

    /**
     * Возвращает два массива: сначала положительные, потом отрицательные.
     */
    public static int[][] splitBySign(int[] numbers) {
        int positiveCount = 0;
        int negativeCount = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 0) {
                positiveCount++;
            } else if (numbers[i] < 0) {
                negativeCount++;
            }
        }

        int[] positives = new int[positiveCount];
        int[] negatives = new int[negativeCount];
        int positiveIndex = 0;
        int negativeIndex = 0;
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] > 0) {
                positives[positiveIndex] = numbers[i];
                positiveIndex++;
            } else if (numbers[i] < 0) {
                negatives[negativeIndex] = numbers[i];
                negativeIndex++;
            }
        }
        return new int[][] {positives, negatives};
    }

    /**
     * Читает массив и печатает положительные и отрицательные числа.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int length = scanner.nextInt();
        int[] numbers = ArrayIO.readArray(scanner, length);

        int[][] parts = splitBySign(numbers);
        ArrayIO.printNumbers(parts[0]);
        ArrayIO.printNumbers(parts[1]);
    }
}
