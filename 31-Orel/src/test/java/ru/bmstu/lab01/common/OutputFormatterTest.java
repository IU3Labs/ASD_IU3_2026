package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class OutputFormatterTest {

    @Test
    void shouldJoinIntArrayWithSpaces() {
        assertEquals("1 -2 3", OutputFormatter.join(new int[] {1, -2, 3}));
    }

    @Test
    void shouldReturnEmptyStringForEmptyArray() {
        assertEquals("", OutputFormatter.join(new int[0]));
    }

    @Test
    void shouldJoinLongArrayWithoutOverflow() {
        assertEquals("10000000000 -1", OutputFormatter.join(new long[] {10_000_000_000L, -1}));
    }

    @Test
    void shouldPrintEachMatrixRowOnSeparateLine() {
        String expected = "1 2" + System.lineSeparator() + "3 4";

        assertEquals(expected, OutputFormatter.join(new long[][] {{1, 2}, {3, 4}}));
    }
}
