package ru.bmstu.lab1.task_32;

import java.util.Scanner;

/** Задача 32. Сумма произведений пар: сумма a[i] * a[j] для всех i < j. */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        long prefixSum = 0;
        long answer = 0;

        for (int i = 0; i < n; i++) {
            long number = scanner.nextLong();
            answer += prefixSum * number;
            prefixSum += number;
        }

        System.out.println(answer);
        scanner.close();
    }
}
