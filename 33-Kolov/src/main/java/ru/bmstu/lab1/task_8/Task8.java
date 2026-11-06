package ru.bmstu.lab1.task_8;

import java.util.Scanner;

/**
 * Задача 13. Сортировка выбором.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию.
 * Требование: реализовать сортировку выбором вручную, без использования встроенных функций сортировки.
 * Сложность: базовая
 */

public class Task8 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            selectionSort(arr);

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(arr[i]);
            }
            System.out.println();
        }
    }

    //Сортировка выбором.
    public static void selectionSort(int[] arr) {
        sizeCheck(arr.length);

        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;

            // Ищем минимум в ещё не отсортированной части массива.
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}