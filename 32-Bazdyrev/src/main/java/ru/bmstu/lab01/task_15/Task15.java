package ru.bmstu.lab01.task_15;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Задача 15. Пузырьковая сортировка с подсчётом обменов.
 * Алгоритм сортировки массива с досрочным выходом, если массив уже отсортирован.
 * Временная сложность: O(n^2) в худшем, O(n) в лучшем; дополнительная память: O(1).
 */
public class Task15 {
    /**
     * Функция пузырьковой сортировки массива (по неубыванию) с подсчетом обменов.
     *
     * @param arr исходный массив для сортировки
     * @param swaps количество выполненных обменов
     * @return swaps, количество выполненных обменов
     */
    public static int bubbleSortWithCount(int[] arr) {
        if (arr == null) return 0;
        
        int swaps = 0;
        boolean swapped;
        
        // Внешний цикл отвечает за проходы по массиву
        for (int i = 0; i < arr.length - 1; i++) {
            swapped = false;
            
            // Внутренний цикл сравнивает соседние элементы, 
            // перемещая наибольший элемент в конец
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int t = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = t;
                    
                    swaps++;
                    swapped = true;
                }
            }
            
            // Досрочный выход: если за проход не было ни одного обмена, массив отсортирован
            if (!swapped) {
                break;
            }
        }
        
        return swaps;
    }

    /**
     * Чтение массива, вызов функции сортировки, вывод результата.
     *
     * @param in поток ввода данных
     * @param out поток вывода данных
     */
    public static void solve(InputStream in, PrintStream out) {
        Scanner scanner = new Scanner(in);
        // Если на вход пустая строка
        if (!scanner.hasNextInt()) {
            scanner.close();
            return;
        }
        
        int[] arr = new int[scanner.nextInt()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = scanner.nextInt();
        }

        int swaps = bubbleSortWithCount(arr);
        
        // Вывод отсортированного массива в одну строку
        out.println(Arrays.stream(arr)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining(" ")));
        // Вывод количества обменов на новой строке
        out.println(swaps);
        
        scanner.close();
    }
}