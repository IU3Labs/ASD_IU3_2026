package ru.bmstu.lab01.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Тесты OutputFormat: объединение чисел в строку через пробел.
 */
class OutputFormatTest {

    @Test
    void shouldJoinIntsWithSingleSpaces() {
        assertEquals("1 -2 3", OutputFormat.join(new int[] {1, -2, 3}));
    }

    @Test
    void shouldJoinLongsWithSingleSpaces() {
        long beyondInt = 2L * Integer.MAX_VALUE;

        assertEquals(beyondInt + " 0", OutputFormat.join(new long[] {beyondInt, 0L}));
    }

    @Test
    void shouldReturnEmptyStringForEmptyArray() {
        assertEquals("", OutputFormat.join(new int[0]));
        assertEquals("", OutputFormat.join(new long[0]));
    }

    @Test
    void shouldNotAddSeparatorForSingleValue() {
        assertEquals("42", OutputFormat.join(new int[] {42}));
    }
}
