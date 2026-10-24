package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 10. Поиск элемента с условием"
class Task10Test {

    @Test
    void shouldReturnLastIndexWhenMultipleOccurrences() {
        int[] input = {3, 6, 9, 12, 15};

        int actual = Task10.lastMultipleIndex(input, 3);

        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOnlyIndexWhenSingleMultiple() {
        int[] input = {4, 5, 7, 8, 10};

        int actual = Task10.lastMultipleIndex(input, 8);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneWhenNoMultiples() {
        int[] input = {1, 3, 5, 7};

        int actual = Task10.lastMultipleIndex(input, 2);

        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeK() {
        int[] input = {4, 6, 8, 10};

        int actual = Task10.lastMultipleIndex(input, -2);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeElements() {
        int[] input = {-6, 4, -9, 10};

        int actual = Task10.lastMultipleIndex(input, 3);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneForEmptyArray() {
        int[] input = {};

        int actual = Task10.lastMultipleIndex(input, 5);

        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task10.lastMultipleIndex(null, 2));
    }

    @Test
    void shouldThrowWhenKIsZero() {
        int[] input = {1, 2, 3};

        assertThrows(IllegalArgumentException.class, () -> Task10.lastMultipleIndex(input, 0));
    }
}