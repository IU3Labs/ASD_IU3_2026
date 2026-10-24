/*Задача 20. Сортировка подсчётом
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию.
Требование: реализовать сортировку подсчётом.
Сложность: средняя*/

package ru.bmstu.lab01;


//Сортирует массив по неубыванию сортировкой подсчётом (counting sort).
public final class LabaTask20 {

    private LabaTask20() {
        // утилитарный класс
    }

    public static void countingSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (array.length < 2) {
            return;
        }

        int min = array[0];
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
            if (array[i] > max) {
                max = array[i];
            }
        }

        // Диапазон значений [min, max]. Размер считается в long, чтобы избежать переполнения
        // при потенциально большом разбросе (например, MIN_VALUE..MAX_VALUE).
        long rangeLong = (long) max - (long) min + 1;
        if (rangeLong > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("value range too large for counting sort");
        }
        int range = (int) rangeLong;

        int[] counts = new int[range];
        for (int i = 0; i < array.length; i++) {
            counts[array[i] - min]++;
        }

        int index = 0;
        for (int value = 0; value < range; value++) {
            for (int c = 0; c < counts[value]; c++) {
                array[index++] = min + value;
            }
        }
    }
}