package ru.bmstu.lab01.task_11;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ClosestToMeanTest {

    @Test
    void shouldPreferFirstOfEqualDistances() {
        // среднее 5.0, ближайшие 5 и 7 — берём 5 (индекс 0)
        assertEquals(5, ClosestToMean.findClosest(new int[] {5, 2, 9, 1, 7, 6}));
    }

    @Test
    void shouldReturnMiddleWhenMeanIsMiddleElement() {
        assertEquals(2, ClosestToMean.findClosest(new int[] {1, 2, 3}));
    }

    @Test
    void shouldReturnFirstWhenTwoElementsAreEquidistant() {
        assertEquals(10, ClosestToMean.findClosest(new int[] {10, 20}));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-5, ClosestToMean.findClosest(new int[] {-5, -2, -8}));
    }

    @Test
    void shouldReturnSingleElement() {
        assertEquals(42, ClosestToMean.findClosest(new int[] {42}));
    }

    @Test
    void shouldHandleFractionalMean() {
        // среднее 2.5, ближайшие 2 и 3 — берём 2 (индекс 0)
        assertEquals(2, ClosestToMean.findClosest(new int[] {2, 3}));
    }

    @Test
    void shouldPreferFirstWhenAllEqual() {
        assertEquals(7, ClosestToMean.findClosest(new int[] {7, 7, 7}));
    }

    @Test
    void shouldHandleLargeValuesWithoutOverflow() {
        // среднее около Integer.MAX_VALUE, сумма переполнила бы int
        int[] data = {Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE - 2};
        assertEquals(Integer.MAX_VALUE, ClosestToMean.findClosest(data));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> ClosestToMean.findClosest(null));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> ClosestToMean.findClosest(new int[0]));
    }
}