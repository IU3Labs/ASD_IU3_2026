package ru.bmstu.lab01.task_16;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class SortByModulusTest {

    @Test
    void shouldSortByAbsoluteValue() {
        assertArrayEquals(new int[] {-1, -2, 3, 4, 5},
                SortByModulus.sortByModulus(new int[] {3, -1, 5, -2, 4}));
    }

    @Test
    void shouldPutNegativeBeforePositiveOnEqualModulus() {
        assertArrayEquals(new int[] {-3, 3, -5, 5},
                SortByModulus.sortByModulus(new int[] {5, -3, 3, -5}));
    }

    @Test
    void shouldHandleZeros() {
        assertArrayEquals(new int[] {0, -1, 1, -2, 2},
                SortByModulus.sortByModulus(new int[] {2, -1, 0, 1, -2}));
    }

    @Test
    void shouldKeepOrderWhenAlreadySorted() {
        assertArrayEquals(new int[] {-1, 1, -2, 2},
                SortByModulus.sortByModulus(new int[] {-1, 1, -2, 2}));
    }

    @Test
    void shouldHandleSingleElement() {
        assertArrayEquals(new int[] {-5}, SortByModulus.sortByModulus(new int[] {-5}));
    }

    @Test
    void shouldHandleIntegerMinValueCorrectly() {
        // |Integer.MIN_VALUE| через int-abs остаётся отрицательным — проверяем через long
        assertArrayEquals(new int[] {-1, Integer.MAX_VALUE, Integer.MIN_VALUE},
                SortByModulus.sortByModulus(
                        new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, -1}));
    }

    @Test
    void shouldHandleAllNegatives() {
        assertArrayEquals(new int[] {-1, -2, -3},
                SortByModulus.sortByModulus(new int[] {-3, -1, -2}));
    }

    @Test
    void shouldRejectNull() {
        assertThrows(IllegalArgumentException.class, () -> SortByModulus.sortByModulus(null));
    }

    @Test
    void shouldRejectEmpty() {
        assertThrows(IllegalArgumentException.class,
                () -> SortByModulus.sortByModulus(new int[0]));
    }
}