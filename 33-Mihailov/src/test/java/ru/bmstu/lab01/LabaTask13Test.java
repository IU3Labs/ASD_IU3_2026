/*Задача 13. Сортировка выбором
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию.
Требование: реализовать сортировку выбором вручную, без использования встроенных функций сортировки.
Сложность: базовая*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask13Test {

    @Test
    void shouldSortUnsortedArray() {
        int[] input = {5, 1, 4, 2, 3};

        LabaTask13.selectionSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] input = {4, 2, 4, 1, 2};

        LabaTask13.selectionSort(input);

        int[] expected = {1, 2, 2, 4, 4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};

        LabaTask13.selectionSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleReverseSortedArray() {
        int[] input = {5, 4, 3, 2, 1};

        LabaTask13.selectionSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 0, -10, 7, 2};

        LabaTask13.selectionSort(input);

        int[] expected = {-10, -3, 0, 2, 7};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};

        LabaTask13.selectionSort(input);

        int[] expected = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        LabaTask13.selectionSort(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask13.selectionSort(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask13.selectionSort(null));
    }
}