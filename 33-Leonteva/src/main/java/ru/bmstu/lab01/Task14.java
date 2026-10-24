/*Задача 14. Сортировка вставками по невозрастанию
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по невозрастанию.
Требование: реализовать сортировку вставками вручную.
Сложность: базовая*/

package ru.bmstu.lab01;


//Сортирует массив по невозрастанию сортировкой вставками (на месте).
public final class Task14 {

    private Task14() {
        // утилитарный класс
    }

    public static void insertionSortDescending(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            // Сдвигаем вправо все элементы, меньшие key (для невозрастания).
            while (j >= 0 && array[j] < key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}