/*Задача 21. K-й наименьший элемент
Вход: массив из (n) целых чисел и число (k) (1 <= k <= n).
Выход: (k)-й наименьший элемент массива.
Требование: отсортировать массив и взять элемент с индексом (k - 1).
Сложность: базовая*/

package ru.bmstu.lab01;


//Возвращает k-й наименьший элемент массива (1 <= k <= n) через полную сортировку.
public final class LabaTask21 {

    private LabaTask21() {
        // утилитарный класс
    }

    public static int kthSmallest(int[] array, int k) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (k < 1 || k > array.length) {
            throw new IllegalArgumentException("k must be in range [1, n]");
        }

        int[] sorted = array.clone();
        insertionSort(sorted);
        return sorted[k - 1];
    }

    //Сортировка вставками по неубыванию (на месте).
    private static void insertionSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int key = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > key) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}