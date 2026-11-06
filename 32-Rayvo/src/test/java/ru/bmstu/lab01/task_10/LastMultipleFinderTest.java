package ru.bmstu.lab01.task_10;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LastMultipleFinderTest {

    @Test
    void shouldFindLastMultipleInUnsortedArray() {
        int[] array = {3, 8, 12, 5, 9};
        // Кратны 3: 3 (0), 12 (2), 9 (4) → последний индекс 4
        assertEquals(4, LastMultipleFinder.findLastMultiple(array, 3));
    }

    @Test
    void shouldFindSingleMultiple() {
        int[] array = {4, 9, 2, 7, 5};
        assertEquals(1, LastMultipleFinder.findLastMultiple(array, 3));
    }

    @Test
    void shouldReturnLastIndexWhenAllElementsAreMultiples() {
        int[] array = {6, 12, 18, 24};
        assertEquals(3, LastMultipleFinder.findLastMultiple(array, 6));
    }

    @Test
    void shouldReturnMinusOneWhenNoMultiples() {
        int[] array = {4, 9, 2, 7, 5};
        assertEquals(-1, LastMultipleFinder.findLastMultiple(array, 11));
    }

    @Test
    void shouldReturnZeroWhenFirstElementIsOnlyMultiple() {
        int[] array = {10, 3, 7, 4};
        assertEquals(0, LastMultipleFinder.findLastMultiple(array, 5));
    }

    @Test
    void shouldHandleSingleElementArrayFound() {
        assertEquals(0, LastMultipleFinder.findLastMultiple(new int[]{25}, 5));
    }

    @Test
    void shouldHandleSingleElementArrayNotFound() {
        assertEquals(-1, LastMultipleFinder.findLastMultiple(new int[]{25}, 7));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-8, -14, -8, -2, -8};
        // Кратны -2: все. Последний индекс — 4
        assertEquals(4, LastMultipleFinder.findLastMultiple(array, -2));
    }

    @Test
    void shouldHandleNegativeDivisor() {
        int[] array = {3, 8, 12, 5, 9};
        // Кратны -3: 3 (0), 12 (2), 9 (4) → 4
        assertEquals(4, LastMultipleFinder.findLastMultiple(array, -3));
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] array = {-3, 5, 0, 8, 5, -7, 6};
        // Кратны 3: -3 (0), 0 (2), 6 (6) → 6
        assertEquals(6, LastMultipleFinder.findLastMultiple(array, 3));
    }

    @Test
    void shouldHandleZeroAsMultiple() {
        int[] array = {0, 3, 0, 8, 0};
        // Ноль кратен любому k ≠ 0. Последний индекс — 4
        assertEquals(4, LastMultipleFinder.findLastMultiple(array, 7));
    }

    @Test
    void shouldHandleIntegerMinValueWithMinusOne() {
        // MIN_VALUE % -1 == 0 в Java, без ArithmeticException.
        int[] array = {5, Integer.MIN_VALUE};
        assertEquals(1, LastMultipleFinder.findLastMultiple(array, -1));
    }

    @Test
    void shouldThrowExceptionWhenKIsZero() {
        assertThrows(
                IllegalArgumentException.class,
                () -> LastMultipleFinder.findLastMultiple(new int[]{1, 2, 3}, 0)
        );
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {3, 8, 12, 5, 9};
        int[] copy = original.clone();

        LastMultipleFinder.findLastMultiple(original, 3);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}