package ru.bmstu.lab01.task_25;

import java.util.Scanner;

/**
 * Задача 25. Сумма элементов
 * Вход: массив из (n) целых чисел.
 * Выход: сумма всех элементов.
 * Сложность: базовая
 */

public class Task25 {

    /**
     * Вычисляет сумму элементов массива
     *
     * @param array массив целых чисел
     * @return сумма элементов массива (тип long, чтобы избежать переполнения)
     */

    public static long calculateSum(int[] array) {
        long result = 0;
        for (int i = 0; i < array.length; i++) {
            result += array[i];
        }
        return result;
    }


    /**
     *
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }
        long result = calculateSum(array);
        System.out.println(result);
    }

}
