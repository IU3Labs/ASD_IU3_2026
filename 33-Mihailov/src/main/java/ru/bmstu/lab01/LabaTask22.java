/*Задача 22. Сортировка по частоте
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по убыванию частоты встречаемости элементов.
При равных частотах — по возрастанию значения.
Сложность: средняя*/

package ru.bmstu.lab01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//Сортирует массив по убыванию частоты; при равных частотах — по возрастанию значения.
public final class LabaTask22 {

    private LabaTask22() {
        // утилитарный класс
    }

    public static void sortByFrequency(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        Map<Integer, Integer> frequency = new HashMap<>();
        for (int i = 0; i < array.length; i++) {
            frequency.merge(array[i], 1, Integer::sum);
        }

        List<Integer> values = new ArrayList<>(frequency.keySet());
        // Сортировка вставками по паре (частота по убыванию, значение по возрастанию).
        for (int i = 1; i < values.size(); i++) {
            int key = values.get(i);
            int j = i - 1;
            while (j >= 0 && compareByFrequency(values.get(j), key, frequency) > 0) {
                values.set(j + 1, values.get(j));
                j--;
            }
            values.set(j + 1, key);
        }

        int index = 0;
        for (int value : values) {
            int count = frequency.get(value);
            for (int c = 0; c < count; c++) {
                array[index++] = value;
            }
        }
    }

    //Сравнение: сначала по убыванию частоты, при равенстве — по возрастанию значения.
    private static int compareByFrequency(int a, int b, Map<Integer, Integer> frequency) {
        int diff = frequency.get(b) - frequency.get(a);
        if (diff != 0) {
            return diff;
        }
        return Integer.compare(a, b);
    }
}