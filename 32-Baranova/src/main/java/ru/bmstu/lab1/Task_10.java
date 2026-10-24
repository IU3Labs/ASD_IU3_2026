package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 10. Поиск элемента с условием
 * Вход: массив из n целых чисел и число k.
 * Выход: индекс последнего элемента, кратного k. Если такого нет — вывести -1.
 */
public class Task_10 {
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
        int k = scanner.nextInt();

        int lastIndex = -1;

        for (int i = 0; i < n; i++) {
            if (array[i] % k == 0) {
                lastIndex = i;
            }
        }

        System.out.println(lastIndex);
        scanner.close();
    }
}