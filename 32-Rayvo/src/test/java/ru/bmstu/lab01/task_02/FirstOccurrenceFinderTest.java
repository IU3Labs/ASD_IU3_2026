package ru.bmstu.lab01.task_02;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstOccurrenceFinderTest {

    @Test
    void shouldReturnFirstIndexWhenElementOccursOnce() {
        int[] array = {8, 3, 15, 2, 11};
        assertEquals(2, FirstOccurrenceFinder.findFirstOccurrence(array, 15));
    }

    @Test
    void shouldReturnFirstIndexOfDuplicates() {
        int[] array = {8, 3, 15, 3, 11};
        // Число 3 встречается на позициях 1 и 3 — ожидаем первое
        assertEquals(1, FirstOccurrenceFinder.findFirstOccurrence(array, 3));
    }

    @Test
    void shouldReturnZeroWhenElementIsFirst() {
        int[] array = {8, 3, 15, 2, 11};
        assertEquals(0, FirstOccurrenceFinder.findFirstOccurrence(array, 8));
    }

    @Test
    void shouldReturnLastIndexWhenElementIsLast() {
        int[] array = {8, 3, 15, 2, 11};
        assertEquals(4, FirstOccurrenceFinder.findFirstOccurrence(array, 11));
    }

    @Test
    void shouldReturnMinusOneWhenElementNotFound() {
        int[] array = {8, 3, 15, 2, 11};
        assertEquals(-1, FirstOccurrenceFinder.findFirstOccurrence(array, 7));
    }

    @Test
    void shouldHandleSingleElementArrayFound() {
        assertEquals(0, FirstOccurrenceFinder.findFirstOccurrence(new int[]{25}, 25));
    }

    @Test
    void shouldHandleSingleElementArrayNotFound() {
        assertEquals(-1, FirstOccurrenceFinder.findFirstOccurrence(new int[]{25}, 7));
    }

    @Test
    void shouldFindAllEqualElementsFirstIndex() {
        int[] array = {6, 6, 6, 6};
        assertEquals(0, FirstOccurrenceFinder.findFirstOccurrence(array, 6));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-8, -14, -2, -6};
        assertEquals(2, FirstOccurrenceFinder.findFirstOccurrence(array, -2));
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] array = {-3, 5, 0, 8, -7};
        assertEquals(1, FirstOccurrenceFinder.findFirstOccurrence(array, 5));
    }

    @Test
    void shouldFindZeroValue() {
        int[] array = {-3, 5, 0, 8, -7};
        assertEquals(2, FirstOccurrenceFinder.findFirstOccurrence(array, 0));
    }
}