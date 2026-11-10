package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Решение задачи № 41: удаление всех вхождений элемента из массива.
 */
public class Task41 {

    /**
     * Удаляет из массива все элементы, равные заданному числу.
     *
     * @param numbers исходный массив целых чисел
     * @param x число, все вхождения которого необходимо удалить
     * @return новый массив без элементов, равных x
     */
    public static int[] removeAll(int[] numbers, int x) {
        int count = 0;

        for (int number : numbers) {
            if (number != x) {
                count++;
            }
        }

        int[] result = new int[count];
        int index = 0;

        for (int number : numbers) {
            if (number != x) {
                result[index] = number;
                index++;
            }
        }

        return result;
    }

    /**
     * Считывает массив и число x, затем выводит результат удаления.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int n = scanner.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = scanner.nextInt();
        }

        int x = scanner.nextInt();
        int[] result = removeAll(numbers, x);

        for (int i = 0; i < result.length; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(result[i]);
        }

        System.out.println();
    }
}
