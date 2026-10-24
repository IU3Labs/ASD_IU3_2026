package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task36Test {

    @Test
    void shouldCalculatePrefixSums() {
        assertArrayEquals(
                new long[]{2, 5, 4, 8},
                Task36.prefixSums(new int[]{2, 3, -1, 4})
        );
    }

    @Test
    void shouldReturnEmptyArrayForEmptyInput() {
        assertArrayEquals(
                new long[]{},
                Task36.prefixSums(new int[]{})
        );
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertArrayEquals(
                new long[]{-3, -7, -2},
                Task36.prefixSums(new int[]{-3, -4, 5})
        );
    }
}

