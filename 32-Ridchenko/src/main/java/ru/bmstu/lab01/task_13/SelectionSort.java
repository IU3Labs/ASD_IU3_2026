package ru.bmstu.lab01.task_13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Задача 13. Сортировка выбором.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию.
 * Реализация вручную, без встроенных функций сортировки.
 */
public class SelectionSort {

    //метод для тестов (возвращает отсортированный массив)
    public static int[] sort(int[] array) {
        int[] result = Arrays.copyOf(array, array.length); //копируем, чтобы не менять оригинал
        
        for (int i = 0; i < result.length - 1; i++) {
            int minIndex = i;
            //ищем минимальный элемент в оставшейся части
            for (int j = i + 1; j < result.length; j++) {
                if (result[j] < result[minIndex]) {
                    minIndex = j;
                }
            }
            //меняем местами
            int temp = result[minIndex];
            result[minIndex] = result[i];
            result[i] = temp;
        }
        return result;
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

            int[] sorted = sort(array);
            for (int num : sorted) {
                System.out.print(num + " ");
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}