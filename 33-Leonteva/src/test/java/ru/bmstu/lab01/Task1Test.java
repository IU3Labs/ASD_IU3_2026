package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


//Тесты для "Задача 1. Максимум и минимум"
class Task1Test {

    @Test
    void shouldReturnSameValueWhenArrayHasTwoEqualElements() {
        int[] input = {7, 7};

        int[] actual = Task1.findMinMax(input);

        int[] expected = {7, 7};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnMinAndMaxWhenArrayIsSortedAscending() {
        int[] input = {1, 2, 3, 4, 5};

        int[] actual = Task1.findMinMax(input);

        int[] expected = {1, 5};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnMinAndMaxWhenArrayIsSortedDescending() {
        int[] input = {5, 4, 3, 2, 1};

        int[] actual = Task1.findMinMax(input);

        int[] expected = {1, 5};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-10, 0, -3, 42, -100};

        int[] actual = Task1.findMinMax(input);

        int[] expected = {-100, 42};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};

        int[] actual = Task1.findMinMax(input);

        int[] expected = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task1.findMinMax(null));
    }

    @Test
    void shouldThrowWhenArrayTooSmall() {
        int[] input = {42};

        assertThrows(IllegalArgumentException.class, () -> Task1.findMinMax(input));
    }
}