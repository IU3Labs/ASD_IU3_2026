package ru.bmstu.lab01.task_18;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class SortEvensOnlyTest {

    @Test
    void shouldSortEvensAndKeepOddsInPlace() {
        assertArrayEquals(new int[] {2, 3, 4, 1, 6, 7, 8},
                SortEvensOnly.sortEvensOnly(new int[] {8, 3, 6, 1, 4, 7, 2}));
    }

    @Test
    void shouldKeepAllOddsUnchanged() {
        assertArrayEquals(new int[] {5, 3, 1, 7},
                SortEvensOnly.sortEvensOnly(new int[] {5, 3, 1, 7}));
    }

    @Test
    void shouldSortAllEvens() {
        assertArrayEquals(new int[] {2, 4, 6, 8},
                SortEvensOnly.sortEvensOnly(new int[] {8, 4, 6, 2}));
    }

    @Test
    void shouldHandleSingleEven() {
        assertArrayEquals(new int[] {1, 2, 3},
                SortEvensOnly.sortEvensOnly(new int[] {1, 2, 3}));
    }

    @Test
    void shouldHandleZerosAsEven() {
        assertArrayEquals(new int[] {0, 1, 2, 3},
                SortEvensOnly.sortEvensOnly(new int[] {2, 1, 0, 3}));
    }

    @Test
    void shouldHandleNegativeEvens() {
        assertArrayEquals(new int[] {-4, 1, -2, 3},
                SortEvensOnly.sortEvensOnly(new int[] {-2, 1, -4, 3}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {4}, SortEvensOnly.sortEvensOnly(new int[] {4}));
    }

    @Test
    void shouldNotModifySourceArray() {
        int[] source = {4, 1, 2};
        SortEvensOnly.sortEvensOnly(source);
        assertArrayEquals(new int[] {4, 1, 2}, source);
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SortEvensOnly.sortEvensOnly(null));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> SortEvensOnly.sortEvensOnly(new int[0]));
    }
}
