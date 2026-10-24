package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 4. Все индексы элемента
 * Вход: массив из n целых чисел и число x.
 * Выход: все индексы элементов, равных x, в порядке возрастания.
 */
public class Task_4 {
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

        boolean found = false;
        for (int i = 0; i < n; i++) {
            if (array[i] == x) {
                System.out.print(i + " ");
                found = true;
            }
        }

        scanner.close();
    }
}