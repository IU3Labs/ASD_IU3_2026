package ru.bmstu.lab01.task_33;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Задача 33. Количество инверсий.
 * Вход: массив из n целых чисел.
 * Выход: количество пар (i, j), таких что i < j и a[i] > a[j].
 * Требование: O(n log n) через модификацию сортировки слиянием.
 */
public class CountInversions {

    //метод для тестов (возвращает количество инверсий)
    public static long countInversions(int[] array) {
        if (array == null || array.length < 2) {
            return 0;
        }
        //создаем копию, чтобы не менять исходный массив
        int[] copy = array.clone();
        return mergeSortAndCount(copy, 0, copy.length - 1);
    }

    //рекурсивная функция сортировки слиянием, которая считает инверсии
    private static long mergeSortAndCount(int[] array, int left, int right) {
        long count = 0;
        if (left < right) {
            int mid = left + (right - left) / 2;

            //считаем инверсии в левой половине
            count += mergeSortAndCount(array, left, mid);
            //считаем инверсии в правой половине
            count += mergeSortAndCount(array, mid + 1, right);
            //считаем инверсии при слиянии
            count += mergeAndCount(array, left, mid, right);
        }
        return count;
    }

    //слияние двух половин с подсчетом инверсий
    private static long mergeAndCount(int[] array, int left, int mid, int right) {
        int[] leftArray = new int[mid - left + 1];
        int[] rightArray = new int[right - mid];

        for (int i = 0; i < leftArray.length; i++) {
            leftArray[i] = array[left + i];
        }
        for (int j = 0; j < rightArray.length; j++) {
            rightArray[j] = array[mid + 1 + j];
        }

        int i = 0, j = 0, k = left;
        long inversions = 0;

        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] <= rightArray[j]) {
                array[k++] = leftArray[i++];
            } else {
                //если элемент справа меньше, то он образует инверсии
                //со всеми оставшимися элементами слева
                array[k++] = rightArray[j++];
                inversions += (leftArray.length - i);
            }
        }

        while (i < leftArray.length) {
            array[k++] = leftArray[i++];
        }
        while (j < rightArray.length) {
            array[k++] = rightArray[j++];
        }

        return inversions;
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

            System.out.println(countInversions(array));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}