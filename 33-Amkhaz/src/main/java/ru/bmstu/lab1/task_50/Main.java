package ru.bmstu.lab1.task_50;

import java.util.Scanner;

/** Задача 50. Вывести сумму элементов каждого столбца матрицы. */
public class Main {
    static long[] sumColumns(Scanner scanner, int n, int m) {
        long[] sums = new long[m];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < m; j++)
                sums[j] += scanner.nextInt();
        return sums;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(), m = scanner.nextInt();
        long[] sums = sumColumns(scanner, n, m);
        for (int j = 0; j < m; j++) {
            if (j > 0) System.out.print(" ");
            System.out.print(sums[j]);
        }
        System.out.println();
        scanner.close();
    }
}
