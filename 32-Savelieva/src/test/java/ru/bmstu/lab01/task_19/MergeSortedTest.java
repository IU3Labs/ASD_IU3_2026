package ru.bmstu.lab01.task_19;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MergeSortedTest {

    @Test
    void shouldMergeInterleavedArrays() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5, 6},
                MergeSorted.merge(new int[] {1, 3, 5}, new int[] {2, 4, 6}));
    }

    @Test
    void shouldMergeWhenLeftEndsFirst() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5},
                MergeSorted.merge(new int[] {1, 2}, new int[] {3, 4, 5}));
    }

    @Test
    void shouldMergeWhenRightEndsFirst() {
        assertArrayEquals(new int[] {1, 2, 3, 4, 5},
                MergeSorted.merge(new int[] {1, 2, 3}, new int[] {4, 5}));
    }

    @Test
    void shouldHandleEmptyLeft() {
        assertArrayEquals(new int[] {1, 2, 3},
                MergeSorted.merge(new int[0], new int[] {1, 2, 3}));
    }

    @Test
    void shouldHandleEmptyRight() {
        assertArrayEquals(new int[] {1, 2, 3},
                MergeSorted.merge(new int[] {1, 2, 3}, new int[0]));
    }

    @Test
    void shouldHandleBothEmpty() {
        assertArrayEquals(new int[0], MergeSorted.merge(new int[0], new int[0]));
    }

    @Test
    void shouldKeepDuplicates() {
        assertArrayEquals(new int[] {1, 2, 2, 3, 3},
                MergeSorted.merge(new int[] {1, 2, 3}, new int[] {2, 3}));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertArrayEquals(new int[] {-5, -3, -1, 0, 2},
                MergeSorted.merge(new int[] {-5, -1}, new int[] {-3, 0, 2}));
    }

    @Test
    void shouldRejectUnsortedInput() {
        assertThrows(IllegalArgumentException.class,
                () -> MergeSorted.merge(new int[] {3, 1, 2}, new int[] {4, 5}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class,
                () -> MergeSorted.merge(null, new int[] {1}));
    }
}