package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 6. Элементы больше среднего"
class Task6Test {

    @Test
    void shouldCountElementsGreaterThanAverage() {
        int[] input = {1, 2, 3, 4, 5};

        int actual = Task6.countGreaterThanAverage(input);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroWhenAllElementsEqual() {
        int[] input = {7, 7, 7, 7};

        int actual = Task6.countGreaterThanAverage(input);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-5, -2, 0, 1, 6};

        int actual = Task6.countGreaterThanAverage(input);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldCountAllWhenOnlyMaxAboveAverage() {
        int[] input = {0, 0, 0, 0, 4};

        int actual = Task6.countGreaterThanAverage(input);

        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        int actual = Task6.countGreaterThanAverage(input);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotOverflowOnBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE};

        int actual = Task6.countGreaterThanAverage(input);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task6.countGreaterThanAverage(null));
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        int[] input = {};

        assertThrows(IllegalArgumentException.class, () -> Task6.countGreaterThanAverage(input));
    }
}