/*
 * Задача 42. Вставка элемента в отсортированный массив.
 * Вход: отсортированный по неубыванию массив из n целых чисел и число x.
 * Выход: массив длины n + 1, в который x вставлен на правильную позицию.
 * Требование: использовать бинарный поиск для нахождения позиции.
 */
package ru.bmstu.lab01.task_42;

import java.util.Scanner;

import ru.bmstu.lab01.util.ArrayUtils;

/**
 * Вставка числа в отсортированный массив с поиском позиции бинарным поиском.
 */
public class Task42 {

    /**
     * Читает массив (n, затем n чисел), потом число x и печатает новый массив.
     *
     * @param args не используются
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] array = ArrayUtils.readArray(scanner);
        int x = scanner.nextInt();
        System.out.println(ArrayUtils.toLine(insert(array, x)));
    }

    /**
     * Возвращает новый массив длины n + 1 с вставленным числом x. Исходный массив не меняется.
     *
     * @param sorted массив, отсортированный по неубыванию
     * @param x      вставляемое число
     * @return новый отсортированный массив
     * @throws IllegalArgumentException если массив не отсортирован
     */
    public static int[] insert(int[] sorted, int x) {
        checkSorted(sorted);
        int position = findPosition(sorted, x);
        int[] result = new int[sorted.length + 1];
        for (int i = 0; i < position; i++) {
            result[i] = sorted[i];
        }
        result[position] = x;
        for (int i = position; i < sorted.length; i++) {
            result[i + 1] = sorted[i];
        }
        return result;
    }

    /**
     * Бинарный поиск позиции для вставки: индекс первого элемента, который больше x.
     * Если таких элементов нет, возвращается длина массива (вставка в конец).
     *
     * @param sorted отсортированный массив
     * @param x      число
     * @return позиция от 0 до sorted.length
     */
    public static int findPosition(int[] sorted, int x) {
        int left = 0;
        int right = sorted.length;
        // ищем на полуинтервале [left, right): все элементы левее left <= x, правее right > x
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (sorted[mid] <= x) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    private static void checkSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i] < array[i - 1]) {
                throw new IllegalArgumentException("Массив не отсортирован по неубыванию");
            }
        }
    }
}
