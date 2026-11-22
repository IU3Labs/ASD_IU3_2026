package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 3. Подсчёт вхождений"
class Task3Test {

    @Test
    void shouldCountAllOccurrencesWhenElementRepeated() {
        int[] input = {4, 1, 4, 2, 4};

        int actual = Task3.countOccurrences(input, 4);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroWhenElementAbsent() {
        int[] input = {1, 2, 3};

        int actual = Task3.countOccurrences(input, 99);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnOneWhenElementIsUnique() {
        int[] input = {10, 20, 30, 40};

        int actual = Task3.countOccurrences(input, 30);

        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeTarget() {
        int[] input = {0, -7, 3, -7, -7};

        int actual = Task3.countOccurrences(input, -7);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE};

        int actual = Task3.countOccurrences(input, Integer.MAX_VALUE);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroForEmptyArray() {
        int[] input = {};

        int actual = Task3.countOccurrences(input, 42);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task3.countOccurrences(null, 1));
    }
}