package ru.bmstu.lab1.task13;

/**
 * Задача 13. Сортировка выбором.
 * Вход: массив из n целых чисел.
 * Выход: массив, отсортированный по неубыванию.
 * Требование: сортировка выбором вручную, без встроенных функций сортировки.
 */
public class SelectionSort {

    private SelectionSort() {
    }

    /**
     * Сортирует массив по неубыванию сортировкой выбором (на месте).
     * На каждом шаге i ищем минимум в части arr[i..n-1]
     * и меняем его местами с arr[i]. После шага i первые i + 1 элементов
     * уже стоят на своих местах.
     *
     * @param arr массив, который нужно отсортировать
     */
    public static void sort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[minIndex];
            arr[minIndex] = temp;
        }
    }
}
