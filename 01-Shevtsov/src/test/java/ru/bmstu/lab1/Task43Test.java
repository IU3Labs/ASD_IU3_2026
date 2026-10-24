package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task43Test {

    @Test
    void shouldMoveZerosToEndAndKeepOrder() {
        int[] numbers = {0, 1, 0, 3, 12};

        Task43.moveZerosToEnd(numbers);

        assertArrayEquals(new int[]{1, 3, 12, 0, 0}, numbers);
    }

    @Test
    void shouldKeepArrayWithoutZerosUnchanged() {
        int[] numbers = {1, 2, 3};

        Task43.moveZerosToEnd(numbers);

        assertArrayEquals(new int[]{1, 2, 3}, numbers);
    }

    @Test
    void shouldHandleArrayContainingOnlyZeros() {
        int[] numbers = {0, 0, 0};

        Task43.moveZerosToEnd(numbers);

        assertArrayEquals(new int[]{0, 0, 0}, numbers);
    }
}
