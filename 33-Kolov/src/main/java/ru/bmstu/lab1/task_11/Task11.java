package ru.bmstu.lab1.task_11;

import java.util.Arrays;
import java.util.Scanner;

/**
 * Задача 16. Сортировка по модулю
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по возрастанию модуля элементов. При равных модулях первым идёт отрицательный элемент.
 * Сложность: средняя
 */

public class Task11 {
    //main считывает исходные данные из стандартного ввода и выводит результат решения.
    public static void main(String[] args) {
        try (Scanner scan = new Scanner(System.in)) {
            int n = scan.nextInt();
            sizeCheck(n);

            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                arr[i] = scan.nextInt();
            }

            sortByAbsoluteValue(arr);

            for (int i = 0; i < arr.length; i++) {
                if (i > 0) {
                    System.out.print(" ");
                }
                System.out.print(arr[i]);
            }
            System.out.println();
        }
    }

    //sortNyAbsoluteValue сортирует элементы по модулю.
    public static void sortByAbsoluteValue(int[] arr) {
        sizeCheck(arr.length);

        // Для сортировки с собственным правилом сравнения нужен Integer[].
        Integer[] temp = new Integer[arr.length];
        for (int i = 0; i < arr.length; i++) {
            temp[i] = arr[i];
        }

        Arrays.sort(temp, Task11::compareByAbsoluteValue);

        for (int i = 0; i < arr.length; i++) {
            arr[i] = temp[i];
        }
    }

    //compareByAbsoluteValue сравнивает элементы по модулю.
    private static int compareByAbsoluteValue(int a, int b) {
        // Приведение к long позволяет правильно обработать Integer.MIN_VALUE.
        long absA = Math.abs((long) a);
        long absB = Math.abs((long) b);

        int res = Long.compare(absA, absB);
        if (res == 0) {
            return Integer.compare(a, b);
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