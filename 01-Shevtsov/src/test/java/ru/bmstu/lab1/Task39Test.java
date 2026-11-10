package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task39Test {

    @Test
    void shouldRotateArrayRight() {
        int[] numbers = {1, 2, 3, 4, 5};

        Task39.rotateRight(numbers, 2);

        assertArrayEquals(new int[]{4, 5, 1, 2, 3}, numbers);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] numbers = {};

        Task39.rotateRight(numbers, 3);

        assertArrayEquals(new int[]{}, numbers);
    }

    @Test
    void shouldHandleShiftLargerThanArrayLength() {
        int[] numbers = {1, 2, 3};

        Task39.rotateRight(numbers, 4);

        assertArrayEquals(new int[]{3, 1, 2}, numbers);
    }
}
