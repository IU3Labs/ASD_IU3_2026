package ru.bmstu.lab01.task_11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 11. Ближайший к среднему.
 * Вход: массив из n целых чисел.
 * Выход: элемент массива, ближайший к среднему арифметическому. Если несколько - первый по индексу.
 */
public class ClosestToAverage {

    public static int findClosest(int[] array) {
        if (array.length == 0) return 0; //защита, хотя по условию n>0

        double sum = 0;
        for (int num : array) sum += num;
        double avg = sum / array.length;

        int closest = array[0];
        double minDiff = Math.abs(array[0] - avg);

        for (int i = 1; i < array.length; i++) {
            double diff = Math.abs(array[i] - avg);
            //строго меньше, чтобы при равенстве остался первый по индексу
            if (diff < minDiff) {
                minDiff = diff;
                closest = array[i];
            }
        }
        return closest;
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            System.out.println(findClosest(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}