package ru.bmstu.lab1;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Задача 12. Пара с заданной суммой.
 * Вход: массив из n целых чисел и число s.
 * Выход: индексы i < j, для которых ai + aj = s (с минимальным i, затем j). Если пары нет — -1 -1.
 * Сложность: O(n) по времени, O(n) по памяти.
 */
public final class task_12 {
    public static int[] findPair(int[] array, int s) {
        Map<Integer, Integer> seen = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            int complement = s - array[i];
            if (seen.containsKey(complement)) {
                return new int[]{seen.get(complement), i};
            }
            seen.putIfAbsent(array[i], i);
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            int n = sc.nextInt();
            int[] a = new int[n];
            for (int i = 0; i < n; i++) a[i] = sc.nextInt();
            int s = sc.nextInt();
            int[] res = findPair(a, s);
            System.out.println(res[0] + " " + res[1]);
        }
    }
}