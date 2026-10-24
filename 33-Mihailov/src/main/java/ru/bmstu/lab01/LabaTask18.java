/*Задача 18. Сортировка только чётных
Вход: массив из (n) целых чисел.
Выход: тот же массив, в котором все чётные элементы отсортированы по неубыванию,
а нечётные остались на своих местах.
Сложность: средняя*/

package ru.bmstu.lab01;

import java.util.ArrayList;
import java.util.List;


//Сортирует по неубыванию только чётные элементы, оставляя нечётные на своих местах.
public final class LabaTask18 {

    private LabaTask18() {
        // утилитарный класс
    }

    public static void sortEvenOnly(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        List<Integer> evens = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                evens.add(array[i]);
            }
        }

        // Сортируем вставками собранные чётные элементы.
        for (int i = 1; i < evens.size(); i++) {
            int key = evens.get(i);
            int j = i - 1;
            while (j >= 0 && evens.get(j) > key) {
                evens.set(j + 1, evens.get(j));
                j--;
            }
            evens.set(j + 1, key);
        }

        // Возвращаем отсортированные чётные элементы на их исходные позиции.
        int evenIndex = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i] % 2 == 0) {
                array[i] = evens.get(evenIndex);
                evenIndex++;
            }
        }
    }
}