package ru.bmstu.lab1.task_5;

import java.util.Scanner;

/**
 * Задача 6. Элементы больше среднего.
 * Вход: массив из n целых чисел.
 * Выход: количество элементов, строго больших среднего арифметического. Гарантируется, что среднее — целое число (сумма делится на (n)).
 * Сложность: базовая
 */

public class Task5 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            int res = countAboveAverage(arr);
            System.out.println(res);
        }
    }

    //countAboveAverage находит количество элементов, строго больших среднего арифметического
    public static int countAboveAverage(int[] arr) {
        sizeCheck(arr.length);

        // Тип long предотвращает переполнение при суммировании значений int.
        long sum = 0;
        for (int i = 0; i < arr.length; i++) {
            sum += arr[i];
        }

        // По условию сумма делится на размер массива без остатка.
        long average = sum / arr.length;
        int count = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > average) {
                count++;
            }
        }

        return count;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}