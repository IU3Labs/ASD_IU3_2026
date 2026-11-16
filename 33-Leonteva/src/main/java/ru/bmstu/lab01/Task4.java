/*Задача 4. Все индексы элемента
Вход: массив из (n) целых чисел и число (x).
Выход: все индексы элементов, равных (x), в порядке возрастания.
Сложность: базовая*/

package ru.bmstu.lab01;

import java.util.ArrayList;
import java.util.List;


//Возвращает все индексы элементов массива, равных заданному числу, по возрастанию.
public final class Task4 {

    private Task4() {
        // утилитарный класс
    }

    public static List<Integer> findAllIndices(int[] array, int target) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                indices.add(i);
            }
        }
        return indices;
    }
}