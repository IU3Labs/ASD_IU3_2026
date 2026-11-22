/*Задача 12. Пара с заданной суммой
Вход: массив из (n) целых чисел и число (s).
Выход: индексы (i < j), для которых (a_i + a_j = s).
Если пар несколько — вывести пару с наименьшим (i), а при равенстве — с наименьшим (j).
Если пары нет — вывести -1 -1.
Сложность: средняя*/

package ru.bmstu.lab01;

import java.util.HashMap;
import java.util.Map;


//Находит пару индексов i < j с суммой элементов s (лексикографически минимальную по (i, j)).
public final class Task12 {

    private static final int NOT_FOUND = -1;

    private Task12() {
        // утилитарный класс
    }

    public static int[] findPairWithSum(int[] array, int s) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        // Для каждого значения храним наименьший индекс, где оно встретилось.
        Map<Integer, Integer> firstIndex = new HashMap<>();

        int bestI = NOT_FOUND;
        int bestJ = NOT_FOUND;

        for (int j = 0; j < array.length; j++) {
            int need = s - array[j];
            Integer i = firstIndex.get(need);
            if (i != null && (bestI == NOT_FOUND || i < bestI || (i == bestI && j < bestJ))) {
                bestI = i;
                bestJ = j;
            }
            firstIndex.putIfAbsent(array[j], j);
        }

        if (bestI == NOT_FOUND) {
            return new int[] {NOT_FOUND, NOT_FOUND};
        }
        return new int[] {bestI, bestJ};
    }
}