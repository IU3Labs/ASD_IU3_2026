package ru.bmstu.lab1.task_34;

import java.util.Scanner;

/** Задача 34. Максимальная сумма непрерывного подмассива (алгоритм Кадане). */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long currentSum = scanner.nextLong();
        long maxSum = currentSum;

        for (int i = 1; i < n; i++) {
            long number = scanner.nextLong();
            currentSum = Math.max(number, currentSum + number);
            maxSum = Math.max(maxSum, currentSum);
        }
        System.out.println(maxSum);
        scanner.close();
    }
}
