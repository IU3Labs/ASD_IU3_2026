package ru.bmstu.lab01.task_30;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class VarianceTest {

    private static final double EPS = 1e-9;

    @Test
    void shouldReturnZeroForAllEqual() {
        assertEquals(0.0, Variance.variance(new int[] {5, 5, 5, 5}), EPS);
    }

    @Test
    void shouldComputeVarianceOfSimpleArray() {
        // {1, 2, 3, 4, 5}, mean = 3, отклонения: -2, -1, 0, 1, 2, квадраты: 4, 1, 0, 1, 4 = 10, /5 = 2
        assertEquals(2.0, Variance.variance(new int[] {1, 2, 3, 4, 5}), EPS);
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(0.0, Variance.variance(new int[] {42}), EPS);
    }

    @Test
    void shouldHandleTwoElements() {
        // {0, 2}, mean = 1, отклонения -1, 1, квадраты 1, 1 = 2, /2 = 1
        assertEquals(1.0, Variance.variance(new int[] {0, 2}), EPS);
    }

    @Test
    void shouldHandleNegativeValues() {
        // {-1, 0, 1}, mean = 0, квадраты 1, 0, 1 = 2, /3 ≈ 0.6666
        assertEquals(2.0 / 3.0, Variance.variance(new int[] {-1, 0, 1}), EPS);
    }

    @Test
    void shouldHandleLargeValues() {
        // {1000000, 1000002}, mean = 1000001, отклонения -1, 1, квадраты 1,1 = 2, /2 = 1
        assertEquals(1.0, Variance.variance(new int[] {1_000_000, 1_000_002}), EPS);
    }

    @Test
    void shouldBeNonNegative() {
        assertEquals(0.25, Variance.variance(new int[] {1, 1, 2, 2}), EPS);
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> Variance.variance(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Variance.variance(new int[0]));
    }
}