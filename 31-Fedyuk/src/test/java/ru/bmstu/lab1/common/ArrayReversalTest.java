package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

/** Проверяет границы разворачиваемого участка и изменение исходного массива. */
class ArrayReversalTest {
    @Test
    void shouldReverseOnlySelectedRange() {
        int[] values = {1, 2, 3, 4, 5};
        ArrayReversal.reverse(values, 1, 4);
        assertArrayEquals(new int[] {1, 4, 3, 2, 5}, values);
    }

    @Test
    void shouldLeaveEmptyRangeUnchanged() {
        int[] values = {1, 2};
        ArrayReversal.reverse(values, 1, 1);
        assertArrayEquals(new int[] {1, 2}, values);
    }
}
