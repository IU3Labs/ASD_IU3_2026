package ru.university.acsd.lab01;

import java.util.Scanner;

/**
 * Задача 35. Префиксные суммы.
 * <p>
 * Вход: массив из n целых чисел и q запросов вида [l, r].
 * Выход: для каждого запроса сумма от l до r.
 * Требование: O(1) на запрос. Сложность: средняя.
 */
public class task_35 {

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
        long[] prefixSums = new long[n + 1];
        
        for (int i = 0; i < n; i++) {
            prefixSums[i + 1] = prefixSums[i] + scanner.nextLong();
        }
        
        int queriesCount = scanner.nextInt();
        for (int i = 0; i < queriesCount; i++) {
            int left = scanner.nextInt();
            int right = scanner.nextInt();
            System.out.println(prefixSums[right + 1] - prefixSums[left]);
        }
        scanner.close();
    }
}