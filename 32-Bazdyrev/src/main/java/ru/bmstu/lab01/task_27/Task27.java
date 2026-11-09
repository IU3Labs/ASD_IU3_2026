package ru.bmstu.lab01.task_27;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 27. Сумма квадратов.
 * Вычисление суммы квадратов всех элементов массива.
 * Временная сложность: O(n), дополнительная память: O(1).
 */
public class Task27 {

    /**
     * Вычисляет сумму квадратов элементов.
     * Используется приведение к long перед умножением для защиты от переполнения.
     */
    public static long calculateSumOfSquares(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        long sum = 0;
        for (int num : arr) {
            // Обязательное преобразования num в long ДО умножения
            sum += (long) num * num;
        }
        return sum;
    }

    /**
     * Чтение массива, вызов функции вычисления, вывод результата.
     */
    public static void solve(InputStream in, PrintStream out) {
        Scanner scanner = new Scanner(in);
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        
        int[] arr = new int[scanner.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        out.println(calculateSumOfSquares(arr));
        scanner.close();
    }
}