package ru.bmstu.lab01.task_16;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.common.ConsoleRunner;

class AbsoluteValueSortTest {

    @Test
    void shouldSortByAbsoluteValue() {
        int[] expected = {0, 1, -2, 3, -5};

        assertArrayEquals(expected, AbsoluteValueSort.sort(new int[] {3, -5, 1, 0, -2}));
    }

    @Test
    void shouldPlaceNegativeBeforePositiveWithEqualAbsoluteValue() {
        int[] expected = {-4, -4, 4, 4};

        assertArrayEquals(expected, AbsoluteValueSort.sort(new int[] {4, -4, 4, -4}));
    }

    @Test
    void shouldHandleIntegerMinValueWithoutOverflow() {
        int[] expected = {-1, Integer.MAX_VALUE, Integer.MIN_VALUE};

        assertArrayEquals(expected, AbsoluteValueSort.sort(new int[] {Integer.MIN_VALUE, -1, Integer.MAX_VALUE}));
    }

    @Test
    void shouldNotModifySourceArray() {
        int[] source = {2, -1};

        AbsoluteValueSort.sort(source);

        assertArrayEquals(new int[] {2, -1}, source);
    }

    @Test
    void shouldReadArrayAndPrintSortedResult() {
        String actual = ConsoleRunner.run(AbsoluteValueSort::run, "5\n-3 2 3 -1 0\n");

        assertEquals("0 -1 2 -3 3", actual);
    }
}
