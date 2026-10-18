package ru.bmstu.lab01.task_14;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Задача 14. Сортировка вставками (Insertion Sort).
 * Алгоритм сортировки массива по невозрастанию вставками вручную.
 * Временная сложность: O(n^2), дополнительная память: O(1).
 */
public class Task14 {
    /**
     * Функция сортировки массива методом вставок (по невозрастанию).
     *
     * @param arr исходный массив для сортировки
     */
    public static void insertionSort(int[] arr) {
        if (arr == null) return;
        
        // Проходим по массиву, начиная со второго элемента
        for (int i = 1; i < arr.length; i++) {
            int key = arr[i];
            int j = i - 1;
            
            // Сдвигаем элементы, которые меньше key, вправо,
            // чтобы бОльшие числа перемещались к началу массива
            while (j >= 0 && arr[j] < key) {
                arr[j + 1] = arr[j];
                j--;
            }
            // Вставляем текущий элемент на найденное для него место
            arr[j + 1] = key;
        }
    }

    /**
     * Чтение массива, вызов функции сортировки, вывод результата.
     *
     * @param in поток ввода данных
     * @param out поток вывода данных
     */
    public static void solve(InputStream in, PrintStream out) {
        Scanner scanner = new Scanner(in);
        //Если на вход пустая строка
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        
        int[] arr = new int[scanner.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        insertionSort(arr);
        
        out.println(Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")));
        scanner.close();
    }
}