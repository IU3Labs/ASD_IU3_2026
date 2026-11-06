package ru.bmstu.lab1.task_33;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class InversionsTest {

    private static long bruteForce(int[] values) {
        long expected = 0;
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values.length; j++) {
                if (values[i] > values[j]) {
                    expected++;
                }
            }
        }
        return expected;
    }

    @Test
    void shouldReturnZeroForSortedArray() {
        assertEquals(0, Inversions.count(new int[] {1, 2, 2, 3}));
    }

    @Test
    void shouldNotCountEqualElementsAsInversion() {
        assertEquals(0, Inversions.count(new int[] {4, 4, 4}));
    }

    @Test
    void shouldCountAllPairsForReversedArray() {
        assertEquals(10, Inversions.count(new int[] {5, 4, 3, 2, 1}));
    }

    @Test
    void shouldNotModifyInputArray() {
        int[] values = {3, 1, 2};

        Inversions.count(values);

        assertArrayEquals(new int[] {3, 1, 2}, values);
    }

    @Test
    void shouldMatchBruteForceOnRandomArray() {
        int[] values = new Random(33).ints(2_000, -50, 50).toArray();

        assertEquals(bruteForce(values), Inversions.count(values));
    }

    @Test
    void shouldNotOverflowIntOnLargeReversedArray() {
        int size = 100_000;
        int[] values = new int[size];
        for (int i = 0; i < size; i++) {
            values[i] = size - i;
        }
        long expected = (long) size * (size - 1) / 2;

        assertEquals(expected, Inversions.count(values));
    }

    @Test
    void shouldPrintInversionsFromConsole() {
        assertEquals("3", ConsoleRunner.run(Inversions::main, "4\n2 4 1 3\n"));
    }
}
