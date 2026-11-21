package ru.bmstu.lab02;

import java.util.Random;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SlidingMinimumTest {

    @Test
    void shouldRemoveTheExpiredMinimum() {
        assertArrayEquals(new int[]{1, 2, 2}, SlidingMinimum.minimums(new int[]{1, 4, 2, 5}, 2));
    }

    @Test
    void shouldKeepTheOtherCopyOfAnExpiredMinimum() {
        assertArrayEquals(new int[]{2, 2, 3}, SlidingMinimum.minimums(new int[]{2, 2, 3, 4}, 2));
    }

    @Test
    void shouldReturnEveryItemForWindowOfOne() {
        int[] values = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE, -1};
        assertArrayEquals(values, SlidingMinimum.minimums(values, 1));
    }

    @Test
    void shouldReturnOneMinimumForWindowOfEntireArray() {
        assertArrayEquals(new int[]{-4}, SlidingMinimum.minimums(new int[]{5, 0, -4, -1}, 4));
    }

    @Test
    void shouldRejectInvalidInput() {
        assertThrows(NullPointerException.class, () -> SlidingMinimum.minimums(null, 1));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.minimums(new int[0], 1));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.minimums(new int[]{1}, 0));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.minimums(new int[]{1}, 2));
    }

    @Test
    void shouldMatchDirectWindowScanOnRandomInputs() {
        Random random = new Random(64);
        int[] values = new int[128];
        for (int index = 0; index < values.length; index++) {
            values[index] = random.nextInt(101) - 50;
        }
        for (int window = 1; window <= values.length; window++) {
            assertArrayEquals(scanMinimums(values, window), SlidingMinimum.minimums(values, window));
        }
    }

    @Test
    void shouldProcessTwoHundredThousandItemsWithWindowOfOneThousand() {
        int[] values = new int[200_000];
        Random random = new Random(2026);
        for (int index = 0; index < values.length; index++) {
            values[index] = random.nextInt();
        }
        int window = 1000;
        assertArrayEquals(scanMinimums(values, window), SlidingMinimum.minimums(values, window));
    }

    private static int[] scanMinimums(int[] values, int window) {
        int[] expected = new int[values.length - window + 1];
        for (int start = 0; start < expected.length; start++) {
            int minimum = values[start];
            for (int index = start + 1; index < start + window; index++) {
                minimum = Math.min(minimum, values[index]);
            }
            expected[start] = minimum;
        }
        return expected;
    }
}
