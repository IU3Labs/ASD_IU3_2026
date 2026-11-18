package ru.bmstu.lab01.task_3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 3. Подсчёт вхождений.
 * Вход: массив из n целых чисел и число x.
 * Выход: количество элементов, равных x.
 */
public class CountOccurrences {

    //метод для тестов
    public static int count(int[] array, int x) {
        int count = 0;
        for (int num : array) {
            if (num == x) {
                count++;
            }
        }
        return count;
    }

    //ввод-вывод через стандартные потоки
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        String[] parts = reader.readLine().trim().split("\\s+");
        int n = Integer.parseInt(parts[0]);

        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(parts[i + 1]);
        }

        int x = Integer.parseInt(parts[n + 1]);
        System.out.println(count(array, x));
    }
}