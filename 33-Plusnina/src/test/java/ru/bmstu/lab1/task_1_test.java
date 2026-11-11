package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class task_1_test {
    @Test
    void shouldFindMinAndMaxInRegularArray() {
        assertArrayEquals(new int[]{1, 9}, task_1.minMax(new int[]{3, 1, 4, 1, 5, 9, 2, 6}));
    }
    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[]{7, 7}, task_1.minMax(new int[]{7}));
    }
    @Test
    void shouldThrowOnEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> task_1.minMax(new int[]{}));
    }
}