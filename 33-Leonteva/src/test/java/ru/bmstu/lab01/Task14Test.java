package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 14. Сортировка вставками по невозрастанию"
class Task14Test {

    @Test
    void shouldSortAscendingArrayToDescending() {
        int[] input = {1, 2, 3, 4, 5};

        Task14.insertionSortDescending(input);

        int[] expected = {5, 4, 3, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepAlreadyDescendingArray() {
        int[] input = {5, 4, 3, 2, 1};

        Task14.insertionSortDescending(input);

        int[] expected = {5, 4, 3, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortUnsortedArray() {
        int[] input = {3, 1, 4, 2, 5};

        Task14.insertionSortDescending(input);

        int[] expected = {5, 4, 3, 2, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleDuplicates() {
        int[] input = {3, 1, 3, 2, 1};

        Task14.insertionSortDescending(input);

        int[] expected = {3, 3, 2, 1, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 0, -10, 5, -1};

        Task14.insertionSortDescending(input);

        int[] expected = {5, 0, -1, -3, -10};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElement() {
        int[] input = {42};

        Task14.insertionSortDescending(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        Task14.insertionSortDescending(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};

        Task14.insertionSortDescending(input);

        int[] expected = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task14.insertionSortDescending(null));
    }
}