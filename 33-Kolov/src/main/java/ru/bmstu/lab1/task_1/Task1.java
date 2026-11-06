package ru.bmstu.lab1.task_1;

import java.util.Scanner;

/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из (n) целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 * Ограничения: (1 < n < 10^5).
 * Сложность: базовая
 */

public class Task1 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            int[] res = findMinMax (arr);
            System.out.println(res[0] + " " + res[1]);
        }
    }

    //findMinMax находит наименьший и наибольший элемент массива.
    public static int[] findMinMax (int[] arr) {
        sizeCheck(arr.length);
        int min = arr[0], max = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] > max) max = arr[i];
            if (arr[i] < min) min = arr[i];
        }
        return new int[] {min, max};
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck (int size){
        if (size < 2 || size > 99999) {
            throw new IllegalArgumentException ("1 < array size < 100000");
        }
    }
}