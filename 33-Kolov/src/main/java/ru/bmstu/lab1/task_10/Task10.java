package ru.bmstu.lab1.task_10;

import java.util.Scanner;

/**
 * Задача 15. Пузырьковая сортировка с подсчётом обменов
 * Вход: массив из n целых чисел.
 * Выход: отсортированный массив и количество обменов, выполненных в процессе пузырьковой сортировки.
 * Требование: реализовать пузырьковую сортировку с досрочным выходом, если массив уже отсортирован.
 * Сложность: базовая
 */

public class Task10 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            long res = bubbleSort(arr);

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(arr[i]);
            }
            System.out.println();
            System.out.println(res);
        }
    }

    //Сортировка пузырьком.
    public static long bubbleSort(int[] arr) {
        sizeCheck(arr.length);
        long count = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            boolean swapped = false;

            // После каждого прохода очередной максимум занимает место справа.
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    count++;
                    swapped = true;
                }
            }

            if (!swapped) {
                break;
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