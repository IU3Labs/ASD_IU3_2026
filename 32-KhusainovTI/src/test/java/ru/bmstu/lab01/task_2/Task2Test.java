package ru.bmstu.lab01.task_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task2Test {

    @Test
    void shouldFindFirstIndex() {
        int[] numbers = {4, 7, 2, 7};
        int actualIndex = Task2.findFirstIndex(numbers, 7);
        assertEquals(1, actualIndex);
    }

    @Test
    void shouldFindNumberAtFirstIndex() {
        int[] numbers = {5, 2, 3};
        int actualIndex = Task2.findFirstIndex(numbers, 5);
        assertEquals(0, actualIndex);
    }

    @Test
    void shouldReturnMinusOneWhenNumberIsMissing() {
        int[] numbers = {1, 2, 3};
        int actualIndex = Task2.findFirstIndex(numbers, 8);
        assertEquals(-1, actualIndex);
    }
}
