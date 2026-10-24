package ru.bmstu.lab01.task_28;

import ru.bmstu.lab01.task_13.SelectionSort;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 28. Медиана массива.
 * Вход: массив из n целых чисел.
 * Выход: медиана (при нечетном n - центральный элемент, при четном - среднее арифметическое двух центральных).
 */
public class Median {

    //метод для тестов
    public static double findMedian(int[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("массив не может быть пустым");
        }

        //сортируем массив нашей сортировкой выбором из задачи 13
        int[] sortedArray = SelectionSort.sort(array);
        int n = sortedArray.length;

        //если нечетное количество элементов, возвращаем центральный
        if (n % 2 != 0) {
            return sortedArray[n / 2];
        } else {
            //если четное, находим два центральных и считаем среднее
            int mid1 = sortedArray[n / 2 - 1];
            int mid2 = sortedArray[n / 2];
            //приводим к double, чтобы не потерять дробную часть при делении
            return (mid1 + mid2) / 2.0;
        }
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

            System.out.println(findMedian(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}