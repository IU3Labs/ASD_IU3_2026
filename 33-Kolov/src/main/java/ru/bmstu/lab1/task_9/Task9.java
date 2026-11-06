package ru.bmstu.lab1.task_9;

import java.util.Scanner;

/**
 * Задача 14. Сортировка вставками
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по невозрастанию.
 * Требование: реализовать сортировку вставками вручную.
 * Сложность: базовая
 */

public class Task9 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            insertionSort(arr);

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(arr[i]);
            }
            System.out.println();
        }
    }

    //Сортировка вставками.
    public static void insertionSort(int[] arr) {
        sizeCheck(arr.length);

        for (int i = 1; i < arr.length; i++) {
            int temp = arr[i];
            int j = i - 1;

            // Сдвигаем меньшие элементы вправо, освобождая место для вставки.
            while (j >= 0 && arr[j] < temp) {
                arr[j + 1] = arr[j];
                j--;
            }

            arr[j + 1] = temp;
        }
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99_999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}