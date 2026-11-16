/*Задача 14. Сортировка вставками
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по невозрастанию.
Требование: реализовать сортировку вставками вручную.
Сложность: базовая*/

package ru.bmstu.lab01;


//Сортирует массив по невозрастанию методом вставок (insertion sort), без встроенных сортировок.
public final class LabaTask14 {

    private LabaTask14() {
        // утилитарный класс
    }

    public static void insertionSortDescending(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            // Сдвигаем элементы, которые меньше key, вправо: так получаем порядок по невозрастанию.
            while (j >= 0 && array[j] < key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}