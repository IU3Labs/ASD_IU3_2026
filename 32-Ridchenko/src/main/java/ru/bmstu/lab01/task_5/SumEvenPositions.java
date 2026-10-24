package ru.bmstu.lab01.task_5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 5. Сумма элементов на чётных позициях.
 * Вход: массив из n целых чисел.
 * Выход: сумма элементов с чётными индексами (0, 2, 4, ...).
 */
public class SumEvenPositions {

    //метод для тестов
    public static int sumEvenPositions(int[] array) {
        int sum = 0;
        for (int i = 0; i < array.length; i += 2) {
            sum += array[i];
        }
        return sum;
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

            System.out.println(sumEvenPositions(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}