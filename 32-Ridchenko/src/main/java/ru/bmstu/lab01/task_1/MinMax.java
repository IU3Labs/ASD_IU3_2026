package ru.bmstu.lab01.task_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 1. Максимум и минимум.
 * Вход: массив из n целых чисел.
 * Выход: минимальный и максимальный элементы массива.
 */
public class MinMax {

    //метод для тестов
    public static int[] findMinMax(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("массив не может быть пустым");
        }

        int min = array[0];
        int max = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) min = array[i];
            if (array[i] > max) max = array[i];
        }

        return new int[]{min, max};
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

        int[] result = findMinMax(array);
        System.out.println(result[0] + " " + result[1]);
    }
}