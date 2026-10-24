package ru.bmstu.lab01.task_10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 10. Поиск элемента с условием.
 * Вход: массив из n целых чисел и число k.
 * Выход: индекс последнего элемента, кратного k. Если нет - -1.
 */
public class LastMultiple {

    public static int findLastMultiple(int[] array, int k) {
        for (int i = array.length - 1; i >= 0; i--) {
            if (array[i] % k == 0) return i;
        }
        return -1;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            int k = Integer.parseInt(parts[n + 1]);
            System.out.println(findLastMultiple(array, k));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}