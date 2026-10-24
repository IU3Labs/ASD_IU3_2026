package ru.bmstu.lab1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task37Test {

    @Test
    void shouldReverseArray() {
        int[] numbers = {1, 2, 3, 4, 5};

        Task37.reverseInPlace(numbers);

        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, numbers);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] numbers = {};

        Task37.reverseInPlace(numbers);

        assertArrayEquals(new int[]{}, numbers);
    }

    @Test
    void shouldReverseArrayWithTwoElements() {
        int[] numbers = {7, -2};

        Task37.reverseInPlace(numbers);

        assertArrayEquals(new int[]{-2, 7}, numbers);
    }
}
