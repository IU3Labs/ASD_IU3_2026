package ru.bmstu.lab01.task_9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 9. Проверка отсортированности.
 * Вход: массив из n целых чисел.
 * Выход: YES, если массив неубывающий; NO иначе.
 */
public class IsSorted {

    public static String check(int[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1]) return "NO";
        }
        return "YES";
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            System.out.println(check(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}