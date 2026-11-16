package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

//Тесты для "Задача 8. Фильтрация по знаку"
class Task8Test {

    @Test
    void shouldSplitMixedArrayPreservingOrder() {
        int[] input = {3, -1, 0, 7, -5, 2, -4};

        Task8.Result actual = Task8.splitBySign(input);

        List<Integer> expectedPositives = List.of(3, 7, 2);
        List<Integer> expectedNegatives = List.of(-1, -5, -4);
        assertEquals(expectedPositives, actual.positives());
        assertEquals(expectedNegatives, actual.negatives());
    }

    @Test
    void shouldReturnEmptyListsWhenAllZeros() {
        int[] input = {0, 0, 0};

        Task8.Result actual = Task8.splitBySign(input);

        assertTrue(actual.positives().isEmpty());
        assertTrue(actual.negatives().isEmpty());
    }

    @Test
    void shouldReturnOnlyPositivesWhenNoNegatives() {
        int[] input = {1, 2, 3};

        Task8.Result actual = Task8.splitBySign(input);

        List<Integer> expectedPositives = List.of(1, 2, 3);
        assertEquals(expectedPositives, actual.positives());
        assertTrue(actual.negatives().isEmpty());
    }

    @Test
    void shouldReturnOnlyNegativesWhenNoPositives() {
        int[] input = {-1, -2, -3};

        Task8.Result actual = Task8.splitBySign(input);

        List<Integer> expectedNegatives = List.of(-1, -2, -3);
        assertTrue(actual.positives().isEmpty());
        assertEquals(expectedNegatives, actual.negatives());
    }

    @Test
    void shouldReturnEmptyListsForEmptyArray() {
        int[] input = {};

        Task8.Result actual = Task8.splitBySign(input);

        assertTrue(actual.positives().isEmpty());
        assertTrue(actual.negatives().isEmpty());
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};

        Task8.Result actual = Task8.splitBySign(input);

        List<Integer> expectedPositives = List.of(Integer.MAX_VALUE);
        List<Integer> expectedNegatives = List.of(Integer.MIN_VALUE);
        assertEquals(expectedPositives, actual.positives());
        assertEquals(expectedNegatives, actual.negatives());
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task8.splitBySign(null));
    }
}