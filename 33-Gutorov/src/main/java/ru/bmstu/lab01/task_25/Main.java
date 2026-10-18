package ru.bmstu.lab01.task_25;

import java.util.Scanner;

/** Задача 25. Найти сумму элементов массива. */
public class Main {

    /** Читает числа и выводит их сумму. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += input.nextInt();
        }

        System.out.println(sum);
        input.close();
    }
}