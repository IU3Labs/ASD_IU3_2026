package ru.bmstu.lab1.task_55;

import java.util.Scanner;

/** Задача 55. Сумма элементов побочной диагонали квадратной матрицы. */
public class Main {
    static long sumSecondaryDiagonal(Scanner scanner, int n) {
        long sum = 0;
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                int number = scanner.nextInt();
                if (i + j == n - 1) sum += number;
            }
        return sum;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        System.out.println(sumSecondaryDiagonal(scanner, n));
        scanner.close();
    }
}
