package ru.bmstu.lab1.task_19;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class MergeSortedTest {

    @Test
    void shouldMergeInterleavedArrays() {
        int[] actual = MergeSorted.merge(new int[] {1, 3, 5}, new int[] {2, 4, 6, 8});

        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6, 8}, actual);
    }

    @Test
    void shouldKeepDuplicatesFromBothArrays() {
        assertArrayEquals(new int[] {1, 1, 2, 2}, MergeSorted.merge(new int[] {1, 2}, new int[] {1, 2}));
    }

    @Test
    void shouldReturnOtherArrayWhenOneIsEmpty() {
        assertArrayEquals(new int[] {1, 2}, MergeSorted.merge(new int[0], new int[] {1, 2}));
        assertArrayEquals(new int[] {1, 2}, MergeSorted.merge(new int[] {1, 2}, new int[0]));
    }

    @Test
    void shouldPrintMergedArrayFromConsole() {
        assertEquals("-3 0 0 7", ConsoleRunner.run(MergeSorted::main, "2\n-3 0\n2\n0 7\n"));
    }
}
