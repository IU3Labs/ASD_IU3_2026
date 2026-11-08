package ru.bmstu.lab01.task_37;

import java.util.Scanner;

/** Задача 37. Развернуть массив на месте. */
public class Main {

    /** Читает массив, разворачивает его и выводит результат. */
    static void main() {
        Scanner input = new Scanner(System.in);

        int n = input.nextInt();
        int[] numbers = new int[n];

        for (int i = 0; i < n; i++) {
            numbers[i] = input.nextInt();
        }

        reverse(numbers);

        for (int i = 0; i < n; i++) {
            if (i > 0) {
                System.out.print(" ");
            }
            System.out.print(numbers[i]);
        }

        System.out.println();
        input.close();
    }

    /** Разворачивает исходный массив без создания второго массива. */
    static void reverse(int[] numbers) {
        for (int i = 0; i < numbers.length / 2; i++) {
            int last = numbers.length - 1 - i;
            int temporary = numbers[i];
            numbers[i] = numbers[last];
            numbers[last] = temporary;
        }
    }
}