package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


//Тесты для "Задача 2. Индекс первого вхождения"
class Task2Test {

    @Test
    void shouldReturnFirstIndexWhenElementRepeated() {
        int[] input = {4, 1, 4, 2, 4};

        int actual = Task2.indexOfFirst(input, 4);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMiddleIndexWhenElementIsUnique() {
        int[] input = {10, 20, 30, 40, 50};

        int actual = Task2.indexOfFirst(input, 30);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnLastIndexWhenElementIsAtEnd() {
        int[] input = {1, 2, 3, 4, 5};

        int actual = Task2.indexOfFirst(input, 5);

        int expected = 4;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneWhenElementAbsent() {
        int[] input = {1, 2, 3};

        int actual = Task2.indexOfFirst(input, 99);

        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeTarget() {
        int[] input = {0, -7, 3, -7};

        int actual = Task2.indexOfFirst(input, -7);

        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};

        int actual = Task2.indexOfFirst(input, Integer.MAX_VALUE);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMinusOneForEmptyArray() {
        int[] input = {};

        int actual = Task2.indexOfFirst(input, 42);

        int expected = -1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task2.indexOfFirst(null, 1));
    }
}