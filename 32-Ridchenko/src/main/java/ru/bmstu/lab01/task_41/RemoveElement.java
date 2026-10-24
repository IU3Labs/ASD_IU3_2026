package ru.bmstu.lab01.task_41;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Задача 41. Удаление элемента.
 * Вход: массив из n целых чисел и число x.
 * Выход: массив, из которого удалены все вхождения x.
 */
public class RemoveElement {

    //метод для тестов
    public static int[] removeElement(int[] array, int x) {
        List<Integer> resultList = new ArrayList<>();
        
        //проходим по массиву и берем только те элементы, которые не равны x
        for (int num : array) {
            if (num != x) {
                resultList.add(num);
            }
        }
        
        //превращаем список обратно в массив
        int[] resultArray = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            resultArray[i] = resultList.get(i);
        }
        
        return resultArray;
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

            int x = Integer.parseInt(parts[n + 1]);
            
            int[] result = removeElement(array, x);
            
            //выводим результат через пробел
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i]);
                if (i < result.length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}