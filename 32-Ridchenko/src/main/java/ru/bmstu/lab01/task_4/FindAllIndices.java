package ru.bmstu.lab01.task_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Задача 4. Все индексы элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: все индексы элементов, равных x, в порядке возрастания.
 */
public class FindAllIndices {

    //метод для тестов
    public static List<Integer> findAllIndices(int[] array, int x) {
        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == x) {
                indices.add(i);
            }
        }
        return indices;
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
        
        List<Integer> result = findAllIndices(array, x);
        //выводим индексы через пробел
        for (int i = 0; i < result.size(); i++) {
            System.out.print(result.get(i));
            if (i < result.size() - 1) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}