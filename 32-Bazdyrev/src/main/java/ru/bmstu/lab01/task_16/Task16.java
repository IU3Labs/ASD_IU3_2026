package ru.bmstu.lab01.task_16;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Задача 16. Сортировка по модулю.
 * Алгоритм сортировки массива по неубыванию модуля элементов.
 * При равных модулях первым идёт отрицательный элемент.
 * Временная сложность: O(n log n) в среднем, дополнительная память: O(log n) на стек вызовов.
 */
public class Task16 {

    /**
     * Быстрая сортировка (QuickSort).
     */
    public static void quickSortByAbs(int[] arr, int left, int right) {
        if (arr == null || arr.length == 0 || left >= right) {
            return;
        }

        int i = left;
        int j = right;
        // Опорный элемент из середины
        int pivot = arr[left + (right - left) / 2];

        while (i <= j) {
            while (lessThan(arr[i], pivot)) {
                i++;
            }
            while (lessThan(pivot, arr[j])) {
                j--;
            }

            if (i <= j) {
                int t = arr[i];
                arr[i] = arr[j];
                arr[j] = t;
                i++;
                j--;
            }
        }

        if (left < j) {
            quickSortByAbs(arr, left, j);
        }
        if (i < right) {
            quickSortByAbs(arr, i, right);
        }
    }

    /**
     * Вспомогательный метод сравнения двух чисел по условию задачи.
     */
    private static boolean lessThan(int a, int b) {
        long absA = Math.abs((long) a);
        long absB = Math.abs((long) b);
        
        if (absA != absB) {
            return absA < absB;
        }
        // Если модули равны, отрицательное число должно идти перед положительным
        return a < b;
    }

    /**
     * Чтение массива, вызов функции сортировки, вывод результата.
     */
    public static void solve(InputStream in, PrintStream out) {
        Scanner scanner = new Scanner(in);
        // Защита от пустого ввода
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        
        int[] arr = new int[scanner.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        quickSortByAbs(arr, 0, arr.length - 1);
        
        out.println(Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")));
        scanner.close();
    }
}