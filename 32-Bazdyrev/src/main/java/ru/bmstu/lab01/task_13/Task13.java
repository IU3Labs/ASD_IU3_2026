package ru.bmstu.lab01.task_13;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Задача 13. Сортировка выбором (Selection Sort).
 * Алгоритм сортировки массива по неубыванию выбором вручную, без использования встроенных функций сортировки.    
 * Временная сложность: O(n^2), дополнительная память: O(1).
 */
public class Task13 {
    /**
     * Функция сортировки массива.
     *
     * @param arr исходный массив для сортировки
     */
    public static void selectionSort(int[] arr) {
        if (arr == null) return;
        
        // На каждом шаге цикла формируется отсортированная часть слева
        for (int i = 0; i < arr.length - 1; i++) {
            int min = i;
            
            // Поиск индекса минимума в правой неотсортированной части
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j; 
                }
            }
            
            // Если минимальный элемент не на своем месте, элементы меняются местами
            if (min != i) {
                int t = arr[i]; 
                arr[i] = arr[min]; 
                arr[min] = t;
            }
        }
    }

    /**
     * Чтение массива, вызов функции сортировки, вывод результата.
     *
     * @param in поток ввода данных
     * @param out поток вывода данных
     */
    public static void solve(InputStream in, PrintStream out) {
        Scanner scanner = new Scanner(in);
        //Если на вход пустая строка
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        
        int[] arr = new int[scanner.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        selectionSort(arr);
        
        //Формирование строки через Stream API для избежания лишнего пробела в конце
        out.println(Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")));
        scanner.close();
    }
}