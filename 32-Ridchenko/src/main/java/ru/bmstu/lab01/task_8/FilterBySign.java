package ru.bmstu.lab01.task_8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Задача 8. Фильтрация по знаку.
 * Вход: массив из n целых чисел.
 * Выход: два массива (положительные и отрицательные). Нули не включать.
 */
public class FilterBySign {

    //возвращает список из двух массивов: [0] - положительные, [1] - отрицательные
    public static int[][] filter(int[] array) {
        List<Integer> posList = new ArrayList<>();
        List<Integer> negList = new ArrayList<>();
        
        for (int num : array) {
            if (num > 0) posList.add(num);
            else if (num < 0) negList.add(num);
        }
        
        int[] pos = posList.stream().mapToInt(i -> i).toArray();
        int[] neg = negList.stream().mapToInt(i -> i).toArray();
        return new int[][]{pos, neg};
    }

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String[] parts = reader.readLine().trim().split("\\s+");
            int n = Integer.parseInt(parts[0]);
            int[] array = new int[n];
            for (int i = 0; i < n; i++) array[i] = Integer.parseInt(parts[i + 1]);
            
            int[][] result = filter(array);
            for (int i = 0; i < result[0].length; i++) System.out.print(result[0][i] + " ");
            System.out.println();
            for (int i = 0; i < result[1].length; i++) System.out.print(result[1][i] + " ");
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}