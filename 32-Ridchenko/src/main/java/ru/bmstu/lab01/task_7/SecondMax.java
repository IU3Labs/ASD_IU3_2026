package ru.bmstu.lab01.task_7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 7. Второй максимум.
 * Вход: массив из n целых чисел. Все элементы различны.
 * Выход: второй по величине элемент.
 */
public class SecondMax {

    public static int findSecondMax(int[] array) {
        int max = Integer.MIN_VALUE;
        int secondMax = Integer.MIN_VALUE;
        
        for (int num : array) {
            if (num > max) {
                secondMax = max;
                max = num;
            } else if (num > secondMax) {
                secondMax = num;
            }
        }
        return secondMax;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            System.out.println(findSecondMax(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}