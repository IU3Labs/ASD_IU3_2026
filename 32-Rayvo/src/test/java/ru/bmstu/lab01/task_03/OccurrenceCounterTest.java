package ru.bmstu.lab01.task_03;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OccurrenceCounterTest {

    @Test
    void shouldCountSingleOccurrence() {
        int[] array = {4, 9, 2, 7, 5};
        assertEquals(1, OccurrenceCounter.countOccurrences(array, 9));
    }

    @Test
    void shouldCountMultipleOccurrences() {
        int[] array = {4, 9, 4, 2, 4};
        assertEquals(3, OccurrenceCounter.countOccurrences(array, 4));
    }

    @Test
    void shouldReturnZeroWhenElementNotFound() {
        int[] array = {4, 9, 4, 2, 4};
        assertEquals(0, OccurrenceCounter.countOccurrences(array, 7));
    }

    @Test
    void shouldCountAllElementsWhenAllEqual() {
        int[] array = {6, 6, 6, 6};
        assertEquals(4, OccurrenceCounter.countOccurrences(array, 6));
    }

    @Test
    void shouldHandleSingleElementArrayFound() {
        assertEquals(1, OccurrenceCounter.countOccurrences(new int[]{25}, 25));
    }

    @Test
    void shouldHandleSingleElementArrayNotFound() {
        assertEquals(0, OccurrenceCounter.countOccurrences(new int[]{25}, 7));
    }

    @Test
    void shouldCountNegativeNumbers() {
        int[] array = {-8, -14, -8, -2, -8};
        assertEquals(3, OccurrenceCounter.countOccurrences(array, -8));
    }

    @Test
    void shouldCountMixedSigns() {
        int[] array = {-3, 5, 0, 8, 5, -7, 5};
        assertEquals(3, OccurrenceCounter.countOccurrences(array, 5));
    }

    @Test
    void shouldCountZeroValue() {
        int[] array = {0, 3, 0, 8, 0};
        assertEquals(3, OccurrenceCounter.countOccurrences(array, 0));
    }

    @Test
    void shouldCountWhenOccurrencesAtEdges() {
        int[] array = {9, 1, 2, 3, 9};
        assertEquals(2, OccurrenceCounter.countOccurrences(array, 9));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {4, 9, 4, 2, 4};
        int[] copy = original.clone();

        OccurrenceCounter.countOccurrences(original, 4);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}