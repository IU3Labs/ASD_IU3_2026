package ru.bmstu.lab01.task_29;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class ModeTest {

    @Test
    void shouldFindUniqueMode() {
        assertEquals(3, Mode.mode(new int[] {1, 3, 3, 2, 3, 4}));
    }

    @Test
    void shouldPreferSmallestValueWhenFrequenciesEqual() {
        // 2 и 4 встречаются по два раза — берём 2
        assertEquals(2, Mode.mode(new int[] {2, 4, 2, 4, 1}));
    }

    @Test
    void shouldHandleAllEqual() {
        assertEquals(7, Mode.mode(new int[] {7, 7, 7, 7}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(42, Mode.mode(new int[] {42}));
    }

    @Test
    void shouldHandleAllUnique() {
        // все по 1 разу — берём наименьшее
        assertEquals(1, Mode.mode(new int[] {5, 3, 1, 4, 2}));
    }

    @Test
    void shouldHandleNegativeValues() {
        assertEquals(-2, Mode.mode(new int[] {-2, -2, 1, 1, -2}));
    }

    @Test
    void shouldHandleZeros() {
        assertEquals(0, Mode.mode(new int[] {0, 0, 1, 1, 2}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> Mode.mode(null));
    }

    @Test
    void shouldRejectEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Mode.mode(new int[0]));
    }

    @Test
    void shouldRejectTooLargeArray() {
        assertThrows(IllegalArgumentException.class, () -> Mode.mode(new int[10_001]));
    }
}