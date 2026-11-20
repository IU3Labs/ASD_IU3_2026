package ru.bmstu.lab2.task_3;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayDeque;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки задачи 3D с независимыми эталонами: перебором и монотонной очередью. */
class SlidingMinimumTest {
    private static final int LARGE_SIZE = 200_000;
    private static final int LARGE_WINDOW = 1_000;
    private static final int RANDOM_CASES = 300;
    private static final int MAX_SMALL_SIZE = 40;
    private static final long SEED = 20261120L;

    @Test
    void shouldFindMinimumForEveryWindow() {
        assertArrayEquals(
                new int[] {-1, -3, -3, -3, 3, 3},
                SlidingMinimum.solve(new int[] {1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void shouldReturnEveryValueForUnitWindow() {
        assertArrayEquals(new int[] {3, -1, 4}, SlidingMinimum.solve(new int[] {3, -1, 4}, 1));
    }

    @Test
    void shouldReturnSingleMinimumForFullWindow() {
        assertArrayEquals(new int[] {-2}, SlidingMinimum.solve(new int[] {5, -2, 8}, 3));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {7}, SlidingMinimum.solve(new int[] {7}, 1));
    }

    @Test
    void shouldHandleEqualElements() {
        assertArrayEquals(new int[] {5, 5, 5}, SlidingMinimum.solve(new int[] {5, 5, 5, 5}, 2));
    }

    @Test
    void shouldRemoveExpiredMinimumFromIncreasingArray() {
        assertArrayEquals(new int[] {1, 2, 3}, SlidingMinimum.solve(new int[] {1, 2, 3, 4}, 2));
    }

    @Test
    void shouldHandleDecreasingArrayAndPreserveInput() {
        int[] values = {5, 4, 3, 2, 1};
        assertArrayEquals(new int[] {3, 2, 1}, SlidingMinimum.solve(values, 3));
        assertArrayEquals(new int[] {5, 4, 3, 2, 1}, values);
    }

    @Test
    void shouldHandleIntegerLimitsWithoutComparisonOverflow() {
        assertArrayEquals(
                new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE},
                SlidingMinimum.solve(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0}, 2));
    }

    @Test
    void shouldRejectInvalidWindowSizes() {
        assertThrows(
                IllegalArgumentException.class, () -> SlidingMinimum.solve(new int[] {1, 2}, 0));
        assertThrows(
                IllegalArgumentException.class, () -> SlidingMinimum.solve(new int[] {1, 2}, -1));
        assertThrows(
                IllegalArgumentException.class, () -> SlidingMinimum.solve(new int[] {1, 2}, 3));
    }

    @Test
    void shouldRejectEmptyArrayAndNull() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.solve(new int[0], 1));
        assertThrows(NullPointerException.class, () -> SlidingMinimum.solve(null, 1));
    }

    @Test
    void shouldMatchBruteForceForRandomSmallInputs() {
        Random random = new Random(SEED);
        for (int example = 0; example < RANDOM_CASES; example++) {
            int size = 1 + random.nextInt(MAX_SMALL_SIZE);
            int[] values = random.ints(size).toArray();
            int windowSize = 1 + random.nextInt(size);
            assertArrayEquals(
                    bruteForce(values, windowSize), SlidingMinimum.solve(values, windowSize));
        }
    }

    @Test
    void shouldHandleTwoHundredThousandValuesWithWindowOneThousand() {
        int[] values = new Random(SEED).ints(LARGE_SIZE).toArray();
        assertArrayEquals(
                dequeReference(values, LARGE_WINDOW), SlidingMinimum.solve(values, LARGE_WINDOW));
    }

    private static int[] bruteForce(int[] values, int windowSize) {
        int[] result = new int[values.length - windowSize + 1];
        for (int left = 0; left < result.length; left++) {
            int minimum = values[left];
            for (int offset = 1; offset < windowSize; offset++) {
                minimum = Math.min(minimum, values[left + offset]);
            }
            result[left] = minimum;
        }
        return result;
    }

    private static int[] dequeReference(int[] values, int windowSize) {
        ArrayDeque<Integer> indices = new ArrayDeque<>();
        int[] result = new int[values.length - windowSize + 1];
        for (int index = 0; index < values.length; index++) {
            while (!indices.isEmpty() && indices.peekFirst() <= index - windowSize) {
                indices.removeFirst();
            }
            while (!indices.isEmpty() && values[indices.peekLast()] >= values[index]) {
                indices.removeLast();
            }
            indices.addLast(index);
            if (index >= windowSize - 1) {
                result[index - windowSize + 1] = values[indices.peekFirst()];
            }
        }
        return result;
    }
}
