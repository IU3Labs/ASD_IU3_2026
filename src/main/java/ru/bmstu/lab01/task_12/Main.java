package ru.bmstu.lab01.task_12;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Задача 12. Пара с заданной суммой.
 * Ввод: n, n целых чисел, затем s.
 * Вывод: индексы i < j с суммой s. При нескольких парах выбирается
 * минимальный i, затем минимальный j. Если пары нет, выводится "-1 -1".
 */
public class Main {
    public static int[] solve(int[] a, long s) {
        Map<Integer, List<Integer>> positions = new HashMap<>();
        for (int i = 0; i < a.length; i++) {
            positions.computeIfAbsent(a[i], key -> new ArrayList<>()).add(i);
        }

        // Ищем i по возрастанию, а для него — первое подходящее j > i.
        for (int i = 0; i < a.length; i++) {
            long neededLong = s - a[i];
            if (neededLong < Integer.MIN_VALUE || neededLong > Integer.MAX_VALUE) continue;
            List<Integer> indices = positions.get((int) neededLong);
            if (indices == null) continue;
            int left = 0, right = indices.size();
            while (left < right) {
                int mid = left + (right - left) / 2;
                if (indices.get(mid) <= i) left = mid + 1;
                else right = mid;
            }
            if (left < indices.size()) return new int[]{i, indices.get(left)};
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = sc.nextInt();
        long s = sc.nextLong();
        int[] result = solve(a, s);
        System.out.println(result[0] + " " + result[1]);
    }
}
