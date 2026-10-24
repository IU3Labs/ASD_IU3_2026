package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 5. Сумма элементов на чётных позициях"
class Task5Test {

    @Test
    void shouldSumEvenIndicesWhenArrayHasOddLength() {
        int[] input = {1, 2, 3, 4, 5};

        long actual = Task5.sumEvenIndices(input);

        long expected = 1 + 3 + 5;
        assertEquals(expected, actual);
    }

    @Test
    void shouldSumEvenIndicesWhenArrayHasEvenLength() {
        int[] input = {10, 20, 30, 40};

        long actual = Task5.sumEvenIndices(input);

        long expected = 10 + 30;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnSingleElementWhenArrayHasLengthOne() {
        int[] input = {42};

        long actual = Task5.sumEvenIndices(input);

        long expected = 42;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-1, 5, -3, 7, -2};

        long actual = Task5.sumEvenIndices(input);

        long expected = -1 + (-3) + (-2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnZeroForEmptyArray() {
        int[] input = {};

        long actual = Task5.sumEvenIndices(input);

        long expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotOverflowOnBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE};

        long actual = Task5.sumEvenIndices(input);

        long expected = 2L * Integer.MAX_VALUE;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task5.sumEvenIndices(null));
    }
}