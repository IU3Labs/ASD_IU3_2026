package ru.bmstu.lab01.task_26;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * Задача 26. Произведение элементов.
 * Вычисление произведения всех элементов массива по модулю 10^9 + 7.
 * Временная сложность: O(n), дополнительная память: O(1).
 */
public class Task26 {

    private static final long MOD = 1_000_000_007L;

    /**
     * Вычисляет произведение элементов массива по модулю.
     * Используется тип long для предотвращения переполнения.
    */
    public static long calculateProduct(int[] arr) {
        if (arr == null || arr.length == 0) return 0;

        long product = 1;
        for (int num : arr) {
            // Взятие модуля
            long val = num % MOD;
            if (val < 0) {
                val += MOD;
            }
            product = (product * val) % MOD;
        }
        return product;
    }

    /**
     * Чтение массива, вызов функции вычисления произведения, вывод результата.
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

        out.println(calculateProduct(arr));
        scanner.close();
    }
}