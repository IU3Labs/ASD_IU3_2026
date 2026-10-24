package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 2. Индекс первого вхождения
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x. Если такого нет — вывести -1.
 */
public class Task_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            return;
        }

        int n = scanner.nextInt();
        int[] array = new int[n];

        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        if (!scanner.hasNextInt()) {
            return;
        }
        int x = scanner.nextInt();

        int resultIndex = -1; //

        for (int i = 0; i < n; i++) {
            if (array[i] == x) {
                resultIndex = i; //
                break; //
            }
        }

        System.out.println(resultIndex);
        scanner.close();
    }
}