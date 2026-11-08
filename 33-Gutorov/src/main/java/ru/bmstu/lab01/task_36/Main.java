package ru.bmstu.lab01.task_36;

import java.util.Scanner;

/** Задача 36. Вывести бегущую сумму элементов массива. */
public class Main {

    /** Читает числа и выводит суммы от начала до каждой позиции. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += input.nextInt();

            if (i > 0) {
                System.out.print(" ");
            }

            System.out.print(sum);
        }

        System.out.println();
        input.close();
    }
}