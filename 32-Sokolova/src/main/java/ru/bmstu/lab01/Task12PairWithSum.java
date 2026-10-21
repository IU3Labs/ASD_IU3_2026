package ru.bmstu.lab01;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Задача 12. Пара с заданной суммой.
 * Вход: массив и число s.
 * Выход: индексы (i < j), для которых a[i] + a[j] = s.
 * При нескольких парах возвращается первая найденная (наименьшие индексы).
 */
public class Task12PairWithSum {

    /**
     * Ищет пару индексов с суммой s за O(n).
     *
     * @param arr входной массив
     * @param s   целевая сумма
     * @return массив индексов [i, j] или [-1, -1] если пары нет
     */
    public static int[] findPairWithSum(int[] arr, int s) {
        // Храним первое вхождение каждого числа: значение -> индекс
        Map<Integer, Integer> firstIndex = new HashMap<>();
        
        for (int i = 0; i < arr.length; i++) {
            int need = s - arr[i];
            
            // Если дополнение уже встречалось, возвращаем пару
            if (firstIndex.containsKey(need)) {
                int j = firstIndex.get(need);
                return new int[]{j, i}; // j всегда < i, так как карта заполняется по ходу
            }
            
            // Запоминаем индекс текущего числа (только первое вхождение)
            firstIndex.putIfAbsent(arr[i], i);
        }
        
        return new int[]{-1, -1};
    }

    public static void main(String[] args) throws IOException {
        int n = StdIn.nextInt();
        int[] arr = StdIn.readArray(n);
        int s = StdIn.nextInt();
        
        int[] res = findPairWithSum(arr, s);
        System.out.println(res[0] + " " + res[1]);
    }
}