package ru.bmstu.lab01.task_33;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class InversionCounterTest {

    private static final int MAX_SIZE = 100_000;

    @Test
    void shouldReturnZeroForSortedArray() {
        assertEquals(0, InversionCounter.count(new int[] {1, 2, 2, 5, 9}));
    }

    @Test
    void shouldCountAllPairsInReversedArray() {
        // n * (n - 1) / 2 для n = 5
        assertEquals(10, InversionCounter.count(new int[] {5, 4, 3, 2, 1}));
    }

    @Test
    void shouldNotCountEqualElementsAsInversion() {
        assertEquals(0, InversionCounter.count(new int[] {3, 3, 3}));
    }

    @Test
    void shouldCountInversionsInMixedArray() {
        // пары: (2,1), (4,1), (4,3)
        assertEquals(3, InversionCounter.count(new int[] {2, 4, 1, 3, 5}));
    }

    @Test
    void shouldMatchBruteForceOnRandomArray() {
        int[] array = new Random(33).ints(500, -50, 50).toArray();

        assertEquals(countByBruteForce(array), InversionCounter.count(array));
    }

    @Test
    void shouldCountWithoutOverflowOnMaxSizeReversedArray() {
        int[] array = new int[MAX_SIZE];
        for (int i = 0; i < MAX_SIZE; i++) {
            array[i] = MAX_SIZE - i;
        }
        long expected = (long) MAX_SIZE * (MAX_SIZE - 1) / 2;

        assertEquals(expected, InversionCounter.count(array));
    }

    @Test
    void shouldNotModifySourceArray() {
        int[] source = {3, 1, 2};

        InversionCounter.count(source);

        assertArrayEquals(new int[] {3, 1, 2}, source);
    }

    @Test
    void shouldReadArrayAndPrintInversionCount() {
        assertEquals("2", ConsoleRunner.run(InversionCounter::run, "3\n3 1 2\n"));
    }

    private static long countByBruteForce(int[] array) {
        long inversions = 0;
        for (int i = 0; i < array.length; i++) {
            for (int j = i + 1; j < array.length; j++) {
                if (array[i] > array[j]) {
                    inversions++;
                }
            }
        }
        return inversions;
    }
}
