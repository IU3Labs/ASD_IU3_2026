package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

//Тесты для "Задача 4. Все индексы элемента"
class Task4Test {

    @Test
    void shouldReturnAllIndicesWhenElementRepeated() {
        int[] input = {4, 1, 4, 2, 4};

        List<Integer> actual = Task4.findAllIndices(input, 4);

        List<Integer> expected = List.of(0, 2, 4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnSingleIndexWhenElementIsUnique() {
        int[] input = {10, 20, 30, 40};

        List<Integer> actual = Task4.findAllIndices(input, 30);

        List<Integer> expected = List.of(2);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyListWhenElementAbsent() {
        int[] input = {1, 2, 3};

        List<Integer> actual = Task4.findAllIndices(input, 99);

        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldHandleNegativeTarget() {
        int[] input = {0, -7, 3, -7, -7};

        List<Integer> actual = Task4.findAllIndices(input, -7);

        List<Integer> expected = List.of(1, 3, 4);
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE, Integer.MAX_VALUE};

        List<Integer> actual = Task4.findAllIndices(input, Integer.MAX_VALUE);

        List<Integer> expected = List.of(2, 3);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmptyListForEmptyArray() {
        int[] input = {};

        List<Integer> actual = Task4.findAllIndices(input, 42);

        assertTrue(actual.isEmpty());
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task4.findAllIndices(null, 1));
    }
}