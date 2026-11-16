package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 12. Пара с заданной суммой"
class Task12Test {

    @Test
    void shouldReturnFirstPairBySmallestI() {
        int[] input = {1, 2, 3, 4, 5};

        int[] actual = Task12.findPairWithSum(input, 5);

        int[] expected = {0, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnPairWithSmallestJWhenSameI() {
        int[] input = {1, 4, 3, 2, 5};

        int[] actual = Task12.findPairWithSum(input, 3);

        int[] expected = {0, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 1, 4, -1, 2};

        int[] actual = Task12.findPairWithSum(input, 3);

        int[] expected = {1, 4};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneMinusOneWhenNoPair() {
        int[] input = {1, 2, 3};

        int[] actual = Task12.findPairWithSum(input, 100);

        int[] expected = {-1, -1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleDuplicateValues() {
        int[] input = {2, 2, 2, 2};

        int[] actual = Task12.findPairWithSum(input, 4);

        int[] expected = {0, 1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};

        int[] actual = Task12.findPairWithSum(input, Integer.MAX_VALUE + Integer.MIN_VALUE);

        int[] expected = {0, 2};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneMinusOneForSingleElement() {
        int[] input = {42};

        int[] actual = Task12.findPairWithSum(input, 42);

        int[] expected = {-1, -1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneMinusOneForEmptyArray() {
        int[] input = {};

        int[] actual = Task12.findPairWithSum(input, 5);

        int[] expected = {-1, -1};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task12.findPairWithSum(null, 0));
    }
}