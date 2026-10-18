package ru.bmstu.lab1.task_49;

import java.util.Scanner;

/** Задача 49. Вывести сумму элементов каждой строки матрицы. */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt(), m = scanner.nextInt();
        for (int i = 0; i < n; i++) {
            long sum = 0;
            for (int j = 0; j < m; j++) sum += scanner.nextInt();
            System.out.println(sum);
        }
        scanner.close();
    }
}
