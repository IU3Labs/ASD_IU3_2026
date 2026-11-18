package ru.bmstu.lab01.task_6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 6. Элементы больше среднего.
 * Вход: массив из n целых чисел.
 * Выход: количество элементов, строго больших среднего арифметического.
 */
public class GreaterThanAverage {

    public static int countGreaterThanAverage(int[] array) {
        if (array.length == 0) return 0;
        
        int sum = 0;
        for (int num : array) sum += num;
        int avg = sum / array.length;
        
        int count = 0;
        for (int num : array) {
            if (num > avg) count++;
        }
        return count;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            System.out.println(countGreaterThanAverage(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}