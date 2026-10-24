package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 11. Элемент, ближайший к среднему"
class Task11Test {

    @Test
    void shouldReturnElementClosestToIntegerAverage() {
        int[] input = {1, 2, 3, 4, 5};

        int actual = Task11.closestToAverage(input);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFirstWhenTwoEquidistant() {
        int[] input = {1, 3};

        int actual = Task11.closestToAverage(input);

        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnFirstWhenSeveralEquidistant() {
        int[] input = {1, 2, 4, 5};

        int actual = Task11.closestToAverage(input);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleFractionalAverage() {
        int[] input = {1, 2};

        int actual = Task11.closestToAverage(input);

        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-10, -5, 0, 5, 10};

        int actual = Task11.closestToAverage(input);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnSingleElementForArrayOfOne() {
        int[] input = {42};

        int actual = Task11.closestToAverage(input);

        int expected = 42;
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotOverflowOnBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, Integer.MIN_VALUE};

        int actual = Task11.closestToAverage(input);

        int expected = Integer.MAX_VALUE;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task11.closestToAverage(null));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        int[] input = {};

        assertThrows(IllegalArgumentException.class, () -> Task11.closestToAverage(input));
    }
}