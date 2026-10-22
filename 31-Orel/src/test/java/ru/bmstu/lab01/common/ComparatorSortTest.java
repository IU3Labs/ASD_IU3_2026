package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

class ComparatorSortTest {

    @Test
    void shouldSortByGivenComparator() {
        int[] expected = {3, 2, 1};

        assertArrayEquals(expected, ComparatorSort.sorted(new int[] {2, 3, 1}, Comparator.reverseOrder()));
    }

    @Test
    void shouldNotModifySourceArray() {
        int[] source = {2, 1};

        ComparatorSort.sorted(source, Comparator.naturalOrder());

        assertArrayEquals(new int[] {2, 1}, source);
    }
}
