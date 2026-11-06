package ru.bmstu.lab1.task_3;

import java.util.Scanner;

/**
 * Задача 4. Все индексы элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: все индексы элементов, равных x, по возрастанию.
 * Сложность: базовая
 */

public class Task3 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            int x = scan.nextInt();
            int[] res = findAllIndices(arr, x);

            for (int i = 0; i < res.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(res[i]);
            }
            System.out.println();
        }
    }

    //findAllIndices находит индексы элементов, равных заданному числу.
    public static int[] findAllIndices(int[] arr, int x) {
        sizeCheck(arr.length);
        int count = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                count++;
            }
        }

        int[] res = new int[count];
        int index = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == x) {
                res[index] = i;
                index++;
            }
        }
        return res;
    }

    //sizeCheck проверяет размер массива.
    private static void sizeCheck(int size) {
        if (size < 2 || size > 99_999) {
            throw new IllegalArgumentException("1 < array size < 100000");
        }
    }
}