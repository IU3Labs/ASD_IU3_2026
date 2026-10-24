package ru.bmstu.lab01.task_17;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class SortByDigitSumTest {

    @Test
    void shouldSortByDigitSum() {
        // 10->1, 2->2, 30->3, 4->4
        assertArrayEquals(new int[] {10, 2, 30, 4},
                SortByDigitSum.sortByDigitSum(new int[] {30, 4, 10, 2}));
    }

    @Test
    void shouldKeepOriginalOrderOnEqualDigitSum() {
        // 12, 21, 3 — все с суммой 3; сохраняем исходный порядок
        assertArrayEquals(new int[] {12, 21, 3},
                SortByDigitSum.sortByDigitSum(new int[] {12, 21, 3}));
    }

    @Test
    void shouldHandleAllEqualDigitSums() {
        assertArrayEquals(new int[] {100, 10, 1},
                SortByDigitSum.sortByDigitSum(new int[] {100, 10, 1}));
    }

    @Test
    void shouldHandleZeros() {
        assertArrayEquals(new int[] {0, 0, 1, 2},
                SortByDigitSum.sortByDigitSum(new int[] {1, 0, 2, 0}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {42}, SortByDigitSum.sortByDigitSum(new int[] {42}));
    }

    @Test
    void shouldHandleLargeValues() {
        // 999999999 -> 81; 99999999 -> 72
        assertArrayEquals(new int[] {99_999_999, 999_999_999},
                SortByDigitSum.sortByDigitSum(new int[] {999_999_999, 99_999_999}));
    }

    @Test
    void shouldRejectNegativeValues() {
        assertThrows(IllegalArgumentException.class,
                () -> SortByDigitSum.sortByDigitSum(new int[] {1, -2, 3}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SortByDigitSum.sortByDigitSum(null));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> SortByDigitSum.sortByDigitSum(new int[0]));
    }
}
