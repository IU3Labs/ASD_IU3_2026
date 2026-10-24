package ru.bmstu.lab1.task_02;

import java.util.Scanner;

/**
 * Задача 2. Индекс первого вхождения.
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x, иначе -1.
 */
public final class Main {

    private static final int NOT_FOUND = -1;

    private Main() {
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
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
        int target = scanner.nextInt();

        int foundIndex = NOT_FOUND;
        for (int i = 0; i < n; i++) {
            if (array[i] == target) {
                foundIndex = i;
                break;
            }
        }
        System.out.println(foundIndex);
    }
}