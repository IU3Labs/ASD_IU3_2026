/*Задача 20. Сортировка подсчётом
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию.
Требование: реализовать сортировку подсчётом.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask20Test {

    @Test
    void shouldSortUnsortedArray() {
        int[] input = {5, 1, 4, 2, 3};

        LabaTask20.countingSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] input = {4, 2, 4, 1, 2, 4};

        LabaTask20.countingSort(input);

        int[] expected = {1, 2, 2, 4, 4, 4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {0, -5, 3, -10, 7};

        LabaTask20.countingSort(input);

        int[] expected = {-10, -5, 0, 3, 7};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllEqualElements() {
        int[] input = {7, 7, 7, 7};

        LabaTask20.countingSort(input);

        int[] expected = {7, 7, 7, 7};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};

        LabaTask20.countingSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        LabaTask20.countingSort(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask20.countingSort(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask20.countingSort(null));
    }
}