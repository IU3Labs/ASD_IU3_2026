package ru.bmstu.lab01.task_12;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 12. Пара с заданной суммой.
 * Вход: массив из n целых чисел и число s.
 * Выход: индексы i < j, для которых a[i] + a[j] = s. Если пары нет - "-1 -1".
 */
public class PairWithSum {

    public static int[] findPair(int[] array, int s) {
        //двойной цикл гарантирует поиск пары с наименьшим i, а затем с наименьшим j
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] + array[j] == s) {
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            int s = Integer.parseInt(parts[n + 1]);
            int[] result = findPair(array, s);
            System.out.println(result[0] + " " + result[1]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}