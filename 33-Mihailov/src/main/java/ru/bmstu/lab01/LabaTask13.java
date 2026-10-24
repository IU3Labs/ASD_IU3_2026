/*Задача 13. Сортировка выбором
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию.
Требование: реализовать сортировку выбором вручную, без использования встроенных функций сортировки.
Сложность: базовая*/

package ru.bmstu.lab01;


//Сортирует массив по неубыванию методом выбора (selection sort), без встроенных сортировок.
public final class LabaTask13 {

    private LabaTask13() {
        // утилитарный класс
    }

    public static void selectionSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < n; j++) {
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