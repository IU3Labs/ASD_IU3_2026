package ru.bmstu.lab1.task_25;

import java.util.Scanner;

/**
 * Задача 25. Сумма элементов.
 * Вход: массив из n целых чисел.
 * Выход: сумма всех элементов.
 */
public final class Main {

    private Main() {
    }

    /**
     * Точка входа в программу.
     *
     * @param args аргументы командной строки
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        if (!scanner.hasNextInt()) {
            return;
        }
        int n = scanner.nextInt();
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum += scanner.nextInt();
        }
        System.out.println(sum);
    }
}