package ru.bmstu.lab1.task_19;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class MergeSortedTest {

    @Test
    void shouldMergeTwoArrays() {
        int[] actual = MergeSorted.merge(new int[] {1, 4, 7}, new int[] {2, 3, 8, 9});
        assertArrayEquals(new int[] {1, 2, 3, 4, 7, 8, 9}, actual);
    }

    @Test
    void shouldKeepDuplicates() {
        int[] actual = MergeSorted.merge(new int[] {1, 2, 2}, new int[] {2, 3});
        assertArrayEquals(new int[] {1, 2, 2, 2, 3}, actual);
    }

    @Test
    void shouldWorkWhenOneArrayIsEmpty() {
        assertArrayEquals(new int[] {1, 5}, MergeSorted.merge(new int[0], new int[] {1, 5}));
        assertArrayEquals(new int[] {1, 5}, MergeSorted.merge(new int[] {1, 5}, new int[0]));
    }

    @Test
    void shouldCopyTailWhenRangesDoNotOverlap() {
        int[] actual = MergeSorted.merge(new int[] {5, 6}, new int[] {1, 2, 3});
        assertArrayEquals(new int[] {1, 2, 3, 5, 6}, actual);
    }

    @Test
    void shouldPrintMergedArray() {
        assertEquals("1 2 3 4 7", MainRunner.run(Main::run, "3\n1 4 7\n2\n2 3"));
    }
}
