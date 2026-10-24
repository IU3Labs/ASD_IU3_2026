package ru.bmstu.lab1.task_37;

import java.util.Scanner;

/**
 * Задача 37. Разворот массива.
 * Вход: массив из n целых чисел.
 * Выход: массив, записанный в обратном порядке (разворот на месте).
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
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = scanner.nextInt();
        }

        // Метод двух указателей навстречу друг другу
        int left = 0;
        int right = n - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) {
                sb.append(' ');
            }
            sb.append(array[i]);
        }
        System.out.println(sb);
    }
}