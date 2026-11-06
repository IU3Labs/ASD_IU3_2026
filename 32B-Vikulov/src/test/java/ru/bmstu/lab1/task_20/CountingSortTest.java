package ru.bmstu.lab1.task_20;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class CountingSortTest {

    @Test
    void shouldSortNegativeAndPositiveValues() {
        int[] actual = {3, -2, 0, -2, 5, 1};

        CountingSort.sort(actual);

        assertArrayEquals(new int[] {-2, -2, 0, 1, 3, 5}, actual);
    }

    @Test
    void shouldLeaveEmptyArrayUnchanged() {
        int[] actual = new int[0];

        CountingSort.sort(actual);

        assertEquals(0, actual.length);
    }

    @Test
    void shouldMatchReferenceSortOnLargeRandomArray() {
        int[] actual = new Random(20).ints(100_000, -1_000, 1_000).toArray();
        int[] expected = actual.clone();
        Arrays.sort(expected);

        CountingSort.sort(actual);

        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldRejectTooWideRange() {
        int[] values = {Integer.MIN_VALUE, Integer.MAX_VALUE};

        assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(values));
    }

    @Test
    void shouldPrintSortedArrayFromConsole() {
        assertEquals("1 1 2", ConsoleRunner.run(CountingSort::main, "3\n2 1 1\n"));
    }
}
