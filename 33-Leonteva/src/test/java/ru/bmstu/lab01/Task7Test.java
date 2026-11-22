package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 7. Второй максимум"
class Task7Test {

    @Test
    void shouldReturnSecondMaxWhenMaxIsFirst() {
        int[] input = {9, 1, 5, 3, 7};

        int actual = Task7.secondMax(input);

        int expected = 7;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnSecondMaxWhenMaxIsLast() {
        int[] input = {4, 8, 1, 6, 10};

        int actual = Task7.secondMax(input);

        int expected = 8;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleTwoElements() {
        int[] input = {3, 8};

        int actual = Task7.secondMax(input);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-10, -3, -7, -1};

        int actual = Task7.secondMax(input);

        int expected = -3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] input = {-5, 10, -2, 3, 0};

        int actual = Task7.secondMax(input);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, Integer.MAX_VALUE, 0};

        int actual = Task7.secondMax(input);

        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task7.secondMax(null));
    }

    @Test
    void shouldThrowWhenArrayTooSmall() {
        int[] input = {42};

        assertThrows(IllegalArgumentException.class, () -> Task7.secondMax(input));
    }
}