/*Задача 15. Пузырьковая сортировка с подсчётом обменов
Вход: массив из (n) целых чисел.
Выход: отсортированный массив и количество обменов, выполненных в процессе пузырьковой сортировки.
Требование: реализовать пузырьковую сортировку с досрочным выходом, если массив уже отсортирован.
Сложность: базовая*/

package ru.bmstu.lab01;


//Сортирует массив по неубыванию пузырьком с досрочным выходом и возвращает число обменов.
public final class LabaTask15 {

    private LabaTask15() {
        // утилитарный класс
    }

    public static long bubbleSort(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("array must not be null");
        }

        long swaps = 0;
        int n = array.length;
        for (int i = 0; i < n - 1; i++) {
            boolean swapped = false;
            // После i-го прохода последние i элементов уже на своих местах.
            for (int j = 0; j < n - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int tmp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = tmp;
                    swaps++;
                    swapped = true;
                }
            }
            if (!swapped) {
                break; // массив уже отсортирован — досрочный выход
            }
        }
        return swaps;
    }
}