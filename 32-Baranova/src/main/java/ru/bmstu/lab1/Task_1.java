package ru.bmstu.lab1;

import java.util.Scanner;

/**
 * Задача 1. Максимум и минимум
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 */
public class Task_1 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        if (!scanner.hasNextInt()) {
            System.out.println("ошибка: нет данных для ввода.");
            return;
        }

        int n = scanner.nextInt();

        if (n <= 0) {
            System.out.println("размер массива должен быть больше 0");
            return;
        }

        int firstNumber = scanner.nextInt();
        int max = firstNumber;
        int min = firstNumber;

        for (int i = 1; i < n; i++) {
            int currentNumber = scanner.nextInt();

            if (currentNumber > max) {
                max = currentNumber;
            }

            if (currentNumber < min) {
                min = currentNumber;
            }
        }

        System.out.println(min + " " + max);

        scanner.close();
    }
}