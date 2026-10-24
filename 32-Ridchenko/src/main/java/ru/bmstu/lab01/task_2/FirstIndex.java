package ru.bmstu.lab01.task_2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 2. Индекс первого вхождения.
 * Вход: массив из n целых чисел и число x.
 * Выход: индекс первого элемента, равного x. Если такого нет - вывести -1.
 */
public class FirstIndex {

    //метод для тестов
    public static int findFirstIndex(int[] array, int x) {
        if (array == null) {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                return i;
            }
        }

        return -1;
    }

    //ввод-вывод через стандартные потоки
    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);

            int[] array = new int[n];
            for (int i = 0; i < n; i++) {
                array[i] = Integer.parseInt(parts[i + 1]);
            }

            int x = Integer.parseInt(parts[n + 1]);
            System.out.println(findFirstIndex(array, x));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}