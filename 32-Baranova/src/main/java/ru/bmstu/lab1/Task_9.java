package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 9. Проверка отсортированности
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий; NO иначе.
 */
public class Task_9 {
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

        boolean isSorted = true;
        for (int i = 0; i < n - 1; i++) {
            if (array[i] > array[i + 1]) {
                isSorted = false;
                break;
            }
        }

        if (isSorted) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }

        scanner.close();
    }
}