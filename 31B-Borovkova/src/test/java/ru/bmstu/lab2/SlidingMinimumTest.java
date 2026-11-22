package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 3D: скользящий минимум; эталон — простой перебор каждого окна.
 */
class SlidingMinimumTest {

    /**
     * Эталонное решение за O(n * k): минимум каждого окна перебором.
     */
    private static int[] bruteForce(int[] arr, int k) {
        int[] result = new int[arr.length - k + 1];
        for (int left = 0; left < result.length; left++) {
            int min = arr[left];
            for (int i = left + 1; i < left + k; i++) {
                min = Math.min(min, arr[i]);
            }
            result[left] = min;
        }
        return result;
    }

    @Test
    void shouldFindMinimumOfEachWindow() {
        assertArrayEquals(new int[] {-1, -3, -3, -3, 3, 3},
                SlidingMinimum.slidingMin(new int[] {1, 3, -1, -3, 5, 3, 6, 7}, 3));
    }

    @Test
    void shouldReturnArrayItselfWhenWindowIsOne() {
        assertArrayEquals(new int[] {4, 2, 7}, SlidingMinimum.slidingMin(new int[] {4, 2, 7}, 1));
    }

    @Test
    void shouldReturnGlobalMinimumWhenWindowIsWholeArray() {
        assertArrayEquals(new int[] {-2}, SlidingMinimum.slidingMin(new int[] {4, -2, 7, 0}, 4));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {5}, SlidingMinimum.slidingMin(new int[] {5}, 1));
    }

    @Test
    void shouldHandleIncreasingArray() {
        // Минимум всегда самый старый элемент окна — он постоянно выходит из окна
        assertArrayEquals(new int[] {1, 2, 3}, SlidingMinimum.slidingMin(new int[] {1, 2, 3, 4, 5}, 3));
    }

    @Test
    void shouldHandleDecreasingArray() {
        // Минимум всегда самый новый элемент — старые копятся в куче до ленивого удаления
        assertArrayEquals(new int[] {3, 2, 1}, SlidingMinimum.slidingMin(new int[] {5, 4, 3, 2, 1}, 3));
    }

    @Test
    void shouldHandleEqualElements() {
        assertArrayEquals(new int[] {7, 7, 7}, SlidingMinimum.slidingMin(new int[] {7, 7, 7, 7}, 2));
    }

    @Test
    void shouldHandleExtremeValues() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE, 0};
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MIN_VALUE, 0}, SlidingMinimum.slidingMin(arr, 2));
    }

    @Test
    void shouldRejectWindowLargerThanArray() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[] {1, 2}, 3));
    }

    @Test
    void shouldRejectNonPositiveWindow() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[] {1, 2}, 0));
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[] {1, 2}, -1));
    }

    @Test
    void shouldRejectEmptyAndNullArray() {
        assertThrows(IllegalArgumentException.class, () -> SlidingMinimum.slidingMin(new int[0], 1));
        assertThrows(NullPointerException.class, () -> SlidingMinimum.slidingMin(null, 1));
    }

    @Test
    void shouldMatchBruteForceOnSmallRandomData() {
        Random random = new Random(7);
        for (int k = 1; k <= 20; k++) {
            int[] arr = random.ints(100, -50, 50).toArray();
            assertArrayEquals(bruteForce(arr, k), SlidingMinimum.slidingMin(arr, k), "k = " + k);
        }
    }

    @Test
    void shouldMatchBruteForceOnLargeArray() {
        int[] arr = new Random(8).ints(200_000).toArray();
        long start = System.nanoTime();
        int[] actual = SlidingMinimum.slidingMin(arr, 1_000);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        assertArrayEquals(bruteForce(arr, 1_000), actual);
        System.out.println("slidingMin(n = 200 000, k = 1 000): " + elapsedMs + " мс");
    }
}
