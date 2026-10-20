package ru.bmstu.lab1.task_54;

import java.util.Scanner;

/** Задача 54. Сумма элементов главной диагонали квадратной матрицы. */
public class Main {
    static long sumMainDiagonal(Scanner scanner, int n) {
        long sum = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int number = scanner.nextInt();
                if (i == j) sum += number;
            }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(sumMainDiagonal(scanner, n));
        scanner.close();
    }
}
