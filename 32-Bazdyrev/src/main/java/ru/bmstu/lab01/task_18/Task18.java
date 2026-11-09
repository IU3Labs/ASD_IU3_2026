package ru.bmstu.lab01.task_18;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Задача 18. Сортировка только чётных.
 * Алгоритм сортирует только чётные элементы массива по неубыванию,
 * оставляя нечётные на своих исходных позициях.
 * Временная сложность: O(n log n) в среднем, дополнительная память: O(n) для массива чётных чисел.
 */
public class Task18 {

    /**
     * Стандартная быстрая сортировка (QuickSort) для массива чётных чисел.
     */
    public static void quickSort(int[] arr, int left, int right) {
        if (arr == null || arr.length == 0 || left >= right) {
            return;
        }

        int i = left;
        int j = right;
        int pivot = arr[left + (right - left) / 2];

        while (i <= j) {
            while (arr[i] < pivot) {
                i++;
            }
            while (arr[j] > pivot) {
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
            quickSort(arr, left, j);
        }
        if (i < right) {
            quickSort(arr, i, right);
        }
    }

    /**
     * Функция сортировки чётных элементов с сохранением позиций нечётных.
     *
     * @param arr исходный массив
     */
    public static void sortEvenElements(int[] arr) {
        if (arr == null || arr.length == 0) {
            return;
        }

        // Подсчёт количества четных элементов
        int evenCount = 0;
        for (int num : arr) {
            if (num % 2 == 0) {
                evenCount++;
            }
        }

        // Если чётных элементов нет или всего один, сортировать нечего
        if (evenCount <= 1) {
            return;
        }

        // Собираем чётные элементы в отдельный массив
        int[] evens = new int[evenCount];
        int index = 0;
        for (int num : arr) {
            if (num % 2 == 0) {
                evens[index] = num;
                index++;
            }
        }

        //Сортируем массив чётных элементов
        quickSort(evens, 0, evens.length - 1);

        //Возвращаем отсортированные чётные элементы обратно
        index = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] % 2 == 0) {
                arr[i] = evens[index];
                index++;
            }
        }
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

        sortEvenElements(arr);

        out.println(java.util.Arrays.stream(arr)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(" ")));
        scanner.close();
    }
}