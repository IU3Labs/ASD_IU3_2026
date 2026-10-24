package ru.bmstu.lab01.task_25;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 25. Сумма элементов.
 * Вычисление суммы всех элементов массива.
 * Временная сложность: O(n), дополнительная память: O(1).
 */
public class Task25 {

    /**
     * Вычисляет сумму элементов массива.
     * Используется тип long для предотвращения целочисленного переполнения.
     *
     * @param arr исходный массив
     * @param sum сумма элементов массива
     * @return sum, сумма элементов массива
     */
    public static long calculateSum(int[] arr) {
        if (arr == null) return 0;
        
        long sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }

    /**
     * Чтение массива, вызов функции вычисления суммы, вывод результата.
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

        out.println(calculateSum(arr));
        scanner.close();
    }
}