package ru.bmstu.lab1.task_35;

import java.util.Scanner;
import ru.bmstu.lab1.ArrayIo;

/** Задача 35. Вычислять суммы на отрезках через префиксные суммы за O(1) на запрос. */
public final class Main {
    private Main() {
    }

    /** Читает массив и запросы, выводит сумму для каждого отрезка. */
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int[] numbers = ArrayIo.readArray(scanner);
            long[] prefix = prefixSums(numbers);
            int queryCount = scanner.nextInt();
            for (int i = 0; i < queryCount; i++) {
                int left = scanner.nextInt();
                int right = scanner.nextInt();
                System.out.println(rangeSum(prefix, left, right));
            }
        }
    }

    /** Строит prefix[i] — сумму первых i элементов за O(n) времени и памяти. */
    public static long[] prefixSums(int[] numbers) {
        long[] prefix = new long[numbers.length + 1];
        for (int i = 0; i < numbers.length; i++) {
            prefix[i + 1] = prefix[i] + numbers[i];
        }
        return prefix;
    }

    /** Возвращает сумму на включительном отрезке [left, right] за O(1). */
    public static long rangeSum(long[] prefix, int left, int right) {
        return prefix[right + 1] - prefix[left];
    }
}
