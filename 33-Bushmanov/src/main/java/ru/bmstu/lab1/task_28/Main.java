package ru.bmstu.lab1.task_28;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 28. Находит медиану целочисленного массива.
 * Перед вычислением медианы массив сортируется по неубыванию.
 */

public class Main {
    /**
     * Считывает массив, сортирует его
     * и выводит медиану.
     *
     * @param args аргументы командной строки, не используются
     */
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();//Считывание размера массива
        int[] arr = new int[n]; //Создание массива

        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();//Считывание элементов массива
        }

        Arrays.sort(arr);// Сортировка массива с помощью метода из библиотеки Arrays

        double result = median(arr);
        System.out.println(result);

        sc.close();
    }
    /**
     * Вычисляет медиану непустого отсортированного массива.
     * При нечётной длине возвращает центральный элемент,
     * при чётной — среднее двух центральных элементов.
     *
     * @param arr непустой массив, отсортированный по неубыванию
     * @return медиана массива
     */
    public static double median(int[] arr) {
        int len = arr.length;
        // При чётной длине берём среднее двух центральных чисел.
        if (len % 2 == 0) {
            return ((long) arr[len / 2 - 1] + arr[len / 2]) / 2.0;
        }
        // При нечётной длине медиана — центральный элемент.
        return arr[len / 2];
    }
}