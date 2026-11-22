package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

/** Сравнение оконных минимумов с прямым перебором. */
class SlidingMinimumTest {
    @Test
    void shouldDiscardExpiredMinimaAndHandleTies() {
        int[] input = {-4, 2, -1, -1, 3, 4};
        assertArrayEquals(new int[]{-4, -1, -1, -1, 3}, SlidingMinimum.slidingMinimum(input, 2));
    }

    @Test
    void shouldSupportBoundaryWindowSizes() {
        int[] input = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};
        assertArrayEquals(input, SlidingMinimum.slidingMinimum(input, 1));
        assertArrayEquals(new int[]{Integer.MIN_VALUE}, SlidingMinimum.slidingMinimum(input, input.length));
    }

    @Test
    void shouldRejectInvalidWindow() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMinimum(new int[2], 0));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMinimum(new int[2], 3));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMinimum(new int[0], 1));
    }

    @Test
    void shouldFindEveryMinimumOnLargeInput() {
        int[] input = new Random(31).ints(200_000).toArray();
        int window = 1000;
        int[] result = SlidingMinimum.slidingMinimum(input, window);
        assertEquals(input.length - window + 1, result.length);
        for (int start = 0; start < result.length; start++) {
            int minimum = input[start];
            for (int i = start + 1; i < start + window; i++) {
                minimum = Math.min(minimum, input[i]);
            }
            assertEquals(minimum, result[start]);
        }
    }
}
