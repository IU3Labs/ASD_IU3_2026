package ru.bmstu.lab01.task_28;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class MedianTest {

    private static final double EPS = 1e-9;

    @Test
    void shouldReturnCentralElementForOddLength() {
        assertEquals(5.0, Median.median(new int[] {5, 2, 9, 1, 7}), EPS);
    }

    @Test
    void shouldReturnAverageForEvenLength() {
        assertEquals(3.5, Median.median(new int[] {5, 2, 6, 1}), EPS);
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(42.0, Median.median(new int[] {42}), EPS);
    }

    @Test
    void shouldHandleTwoElements() {
        assertEquals(2.5, Median.median(new int[] {1, 4}), EPS);
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-5.0, Median.median(new int[] {-5, -1, -9}), EPS);
    }

    @Test
    void shouldHandleAllEqual() {
        assertEquals(7.0, Median.median(new int[] {7, 7, 7, 7}), EPS);
    }

    @Test
    void shouldNotOverflowOnExtremeMiddleValues() {
        // два MAX_VALUE в середине — сумма переполнила бы int
        assertEquals((double) Integer.MAX_VALUE,
                Median.median(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}), EPS);
    }

    @Test
    void shouldNotModifySourceArray() {
        int[] source = {3, 1, 2};
        Median.median(source);
        org.junit.jupiter.api.Assertions.assertArrayEquals(new int[] {3, 1, 2}, source);
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> Median.median(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Median.median(new int[0]));
    }
}