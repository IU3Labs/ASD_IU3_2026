package ru.bmstu.lab01.task_10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class LastMultipleTest {

    @Test
    void shouldReturnLastOfSeveralMultiples() {
        assertEquals(4, LastMultiple.findLastMultipleIndex(
                new int[] {5, 2, 9, 1, 6, 7}, 3));
    }

    @Test
    void shouldReturnMinusOneWhenNoMultiples() {
        assertEquals(-1, LastMultiple.findLastMultipleIndex(
                new int[] {5, 2, 9, 1, 6, 7}, 10));
    }

    @Test
    void shouldReturnLastIndexWhenAllMultiples() {
        assertEquals(2, LastMultiple.findLastMultipleIndex(
                new int[] {10, 20, 30}, 10));
    }

    @Test
    void shouldWorkWithNegativeMultiples() {
        assertEquals(2, LastMultiple.findLastMultipleIndex(
                new int[] {-3, -6, -9}, 3));
    }

    @Test
    void shouldReturnZeroForSingleMatchingElement() {
        assertEquals(0, LastMultiple.findLastMultipleIndex(new int[] {5}, 5));
    }

    @Test
    void shouldReturnMinusOneForSingleNonMatching() {
        assertEquals(-1, LastMultiple.findLastMultipleIndex(new int[] {5}, 2));
    }

    @Test
    void shouldReturnFirstIndexForNegativeDivisor() {
        assertEquals(1, LastMultiple.findLastMultipleIndex(
                new int[] {4, 6, 7}, -3));
    }

    @Test
    void shouldReturnLastWhenBreaksInMiddle() {
        assertEquals(3, LastMultiple.findLastMultipleIndex(
                new int[] {2, 4, 9, 8, 7}, 2));
    }

    @Test
    void shouldRejectZeroDivisor() {
        assertThrows(IllegalArgumentException.class,
                () -> LastMultiple.findLastMultipleIndex(new int[] {1, 2}, 0));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> LastMultiple.findLastMultipleIndex(null, 2));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class,
                () -> LastMultiple.findLastMultipleIndex(new int[0], 3));
    }
}