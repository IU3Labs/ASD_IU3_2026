package ru.bmstu.lab1.task_15;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 28. Медиана массива
 * Вход: массив из n целых чисел.
 * Выход: медиана массива. При нечётном n — элемент посередине после сортировки.
 * При чётном n — среднее арифметическое двух центральных элементов (как число с плавающей точкой).
 * Сложность: средняя
 */

public class Task15 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            double res = findMedian(arr);
            System.out.println(res);
        }
    }

    //findMedian находит медиану массива.
    public static double findMedian(int[] arr) {
        sizeCheck(arr.length);

        // Сортируем копию, чтобы сохранить исходный порядок элементов в arr.
        int[] temp = arr.clone();
        Arrays.sort(temp);

        int middle = temp.length / 2;

        if (temp.length % 2 != 0) {
            return temp[middle];
        }

        // Приведение к long до сложения предотвращает переполнение int.
        return ((long) temp[middle - 1] + temp[middle]) / 2.0;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99_999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}