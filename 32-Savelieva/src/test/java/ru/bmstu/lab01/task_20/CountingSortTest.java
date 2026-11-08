package ru.bmstu.lab01.task_20;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class CountingSortTest {

    @Test
    void shouldSortMixedValues() {
        assertArrayEquals(new int[] {-3, -1, 0, 2, 5, 8},
                CountingSort.sort(new int[] {5, -3, 8, 0, -1, 2}));
    }

    @Test
    void shouldSortAlreadySorted() {
        assertArrayEquals(new int[] {1, 2, 3, 4},
                CountingSort.sort(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldSortDescending() {
        assertArrayEquals(new int[] {1, 2, 3, 4},
                CountingSort.sort(new int[] {4, 3, 2, 1}));
    }

    @Test
    void shouldKeepDuplicates() {
        assertArrayEquals(new int[] {1, 1, 2, 2, 3},
                CountingSort.sort(new int[] {2, 1, 3, 1, 2}));
    }

    @Test
    void shouldHandleAllEqual() {
        assertArrayEquals(new int[] {7, 7, 7}, CountingSort.sort(new int[] {7, 7, 7}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {-42}, CountingSort.sort(new int[] {-42}));
    }

    @Test
    void shouldHandleAllNegatives() {
        assertArrayEquals(new int[] {-9, -5, -3, -1},
                CountingSort.sort(new int[] {-3, -9, -1, -5}));
    }

    @Test
    void shouldRejectHugeValueRange() {
        assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(null));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class, () -> CountingSort.sort(new int[0]));
    }
}