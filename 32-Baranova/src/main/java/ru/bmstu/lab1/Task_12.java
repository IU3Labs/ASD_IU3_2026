package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 12. Пара с заданной суммой
 * Вход: массив из n целых чисел и число s.
 * Выход: индексы i < j, для которых a_i + a_j = s. Если пары нет — вывести -1 -1.
 */
public class Task_12 {
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
        int s = scanner.nextInt();

        int bestI = -1;
        int bestJ = -1;
        boolean found = false;

        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (array[i] + array[j] == s) {
                    bestI = i;
                    bestJ = j;
                    found = true;
                    break;
                }
            }
            if (found) {
                break;
            }
        }

        if (found) {
            System.out.println(bestI + " " + bestJ);
        } else {
            System.out.println("-1 -1");
        }

        scanner.close();
    }
}