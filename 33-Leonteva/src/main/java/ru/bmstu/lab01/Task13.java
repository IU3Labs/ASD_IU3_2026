/*Задача 13. Сортировка выбором
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию.
Требование: реализовать сортировку выбором вручную, без использования встроенных функций сортировки.
Сложность: базовая*/

package ru.bmstu.lab01;


//Сортирует массив по неубыванию сортировкой выбором (на месте).
public final class Task13 {

    private Task13() {
        // утилитарный класс
    }

    public static void selectionSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        for (int i = 0; i < array.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                int tmp = array[i];
                array[i] = array[minIndex];
                array[minIndex] = tmp;
            }
        }
    }
}