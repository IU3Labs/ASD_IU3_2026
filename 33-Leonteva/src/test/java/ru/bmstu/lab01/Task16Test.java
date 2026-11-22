package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 16. Сортировка по модулю"
class Task16Test {

    @Test
    void shouldSortByAbsoluteValue() {
        int[] input = {3, -1, 2, -5, 4};

        Task16.sortByAbs(input);

        int[] expected = {-1, 2, 3, 4, -5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldPutNegativeFirstOnEqualAbs() {
        int[] input = {5, -5, 3, -3, 0};

        Task16.sortByAbs(input);

        int[] expected = {0, -3, 3, -5, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllNegative() {
        int[] input = {-4, -1, -3, -2};

        Task16.sortByAbs(input);

        int[] expected = {-1, -2, -3, -4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllPositive() {
        int[] input = {4, 1, 3, 2};

        Task16.sortByAbs(input);

        int[] expected = {1, 2, 3, 4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepAlreadySortedArray() {
        int[] input = {-1, 2, -3, 4};

        Task16.sortByAbs(input);

        int[] expected = {-1, 2, -3, 4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElement() {
        int[] input = {-42};

        Task16.sortByAbs(input);

        int[] expected = {-42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        Task16.sortByAbs(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};

        Task16.sortByAbs(input);

        // |MIN_VALUE| = 2^31 > |MAX_VALUE| = 2^31 - 1
        int[] expected = {0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task16.sortByAbs(null));
    }
}