package ru.bmstu.lab01.task_22;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class FrequencySortTest {

    @Test
    void shouldPlaceMoreFrequentElementsFirst() {
        int[] expected = {7, 7, 7, 2, 2, 5};

        assertArrayEquals(expected, FrequencySort.sort(new int[] {2, 7, 5, 7, 2, 7}));
    }

    @Test
    void shouldOrderEqualFrequenciesByAscendingValue() {
        int[] expected = {-1, 3, 8};

        assertArrayEquals(expected, FrequencySort.sort(new int[] {8, -1, 3}));
    }

    @Test
    void shouldKeepArrayOfSameElementsUnchanged() {
        int[] expected = {4, 4, 4};

        assertArrayEquals(expected, FrequencySort.sort(new int[] {4, 4, 4}));
    }

    @Test
    void shouldReadArrayAndPrintSortedResult() {
        String actual = ConsoleRunner.run(FrequencySort::run, "7\n1 2 2 3 3 3 1\n");

        assertEquals("3 3 3 1 1 2 2", actual);
    }
}
