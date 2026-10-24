package ru.bmstu.lab01.task_1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class MinMaxSearchTest {

    @Test
    void shouldReturnSameValueWhenAllElementsEqual() {
        assertArrayEquals(new int[] {4, 4}, MinMaxSearch.search(new int[] {4, 4}));
    }

    @Test
    void shouldFindBothExtremesInMixedArray() {
        assertArrayEquals(new int[] {-3, 9},
                MinMaxSearch.search(new int[] {5, -3, 9, 0, 2}));
    }

    @Test
    void shouldHandleAscendingArray() {
        assertArrayEquals(new int[] {1, 4},
                MinMaxSearch.search(new int[] {1, 2, 3, 4}));
    }

    @Test
    void shouldHandleDescendingArray() {
        assertArrayEquals(new int[] {1, 4},
                MinMaxSearch.search(new int[] {4, 3, 2, 1}));
    }

    @Test
    void shouldKeepExtremeIntValues() {
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE},
                MinMaxSearch.search(new int[] {0, Integer.MAX_VALUE, Integer.MIN_VALUE}));
    }

    @Test
    void shouldReturnOnlyNegativeValuesWhenNoPositives() {
        assertArrayEquals(new int[] {-15, -2},
                MinMaxSearch.search(new int[] {-7, -15, -2, -9}));
    }

    @Test
    void shouldAcceptMinimalAllowedLength() {
        assertArrayEquals(new int[] {3, 8}, MinMaxSearch.search(new int[] {8, 3}));
    }

    @Test
    void shouldAcceptMaximalAllowedLength() {
        int[] largest = new int[99_999];
        largest[0] = -1;
        largest[largest.length - 1] = 1;
        assertArrayEquals(new int[] {-1, 1}, MinMaxSearch.search(largest));
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(IllegalArgumentException.class, () -> MinMaxSearch.search(null));
    }

    @Test
    void shouldRejectArrayShorterThanTwo() {
        assertThrows(IllegalArgumentException.class,
                () -> MinMaxSearch.search(new int[] {1}));
    }

    @Test
    void shouldRejectArrayLongerThanMaximal() {
        assertThrows(IllegalArgumentException.class,
                () -> MinMaxSearch.search(new int[100_000]));
    }
}
