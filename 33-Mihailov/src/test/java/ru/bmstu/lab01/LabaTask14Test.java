/*Задача 14. Сортировка вставками
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по невозрастанию.
Требование: реализовать сортировку вставками вручную.
Сложность: базовая*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask14Test {

    @Test
    void shouldSortUnsortedArrayDescending() {
        int[] input = {3, 1, 4, 2, 5};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {5, 4, 3, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] input = {2, 4, 2, 1, 4};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {4, 4, 2, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAlreadySortedDescending() {
        int[] input = {5, 4, 3, 2, 1};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {5, 4, 3, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAscendingArray() {
        int[] input = {1, 2, 3, 4, 5};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {5, 4, 3, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 0, -10, 7, 2};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {7, 2, 0, -3, -10};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask14.insertionSortDescending(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask14.insertionSortDescending(null));
    }
}