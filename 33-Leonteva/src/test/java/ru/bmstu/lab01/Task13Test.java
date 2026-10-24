package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 13. Сортировка выбором"
class Task13Test {

    @Test
    void shouldSortUnsortedArray() {
        int[] input = {5, 1, 4, 2, 3};

        Task13.selectionSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};

        Task13.selectionSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortDescendingArray() {
        int[] input = {5, 4, 3, 2, 1};

        Task13.selectionSort(input);

        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleDuplicates() {
        int[] input = {3, 1, 3, 2, 1};

        Task13.selectionSort(input);

        int[] expected = {1, 1, 2, 3, 3};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 0, -10, 5, -1};

        Task13.selectionSort(input);

        int[] expected = {-10, -3, -1, 0, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElement() {
        int[] input = {42};

        Task13.selectionSort(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        Task13.selectionSort(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};

        Task13.selectionSort(input);

        int[] expected = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task13.selectionSort(null));
    }
}