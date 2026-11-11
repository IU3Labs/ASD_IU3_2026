package ru.bmstu.lab01.task_31;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task31Test {

    @Test
    void shouldCalculateScalarProduct() {
        int[] firstNumbers = {1, 2, 3};
        int[] secondNumbers = {4, 5, 6};
        long actualResult = Task31.calculateScalarProduct(firstNumbers, secondNumbers);
        assertEquals(32L, actualResult);
    }

    @Test
    void shouldWorkWithNegativeNumbers() {
        int[] firstNumbers = {-1, 2};
        int[] secondNumbers = {3, -4};
        long actualResult = Task31.calculateScalarProduct(firstNumbers, secondNumbers);
        assertEquals(-11L, actualResult);
    }

    @Test
    void shouldReturnZeroForZeroArray() {
        int[] firstNumbers = {0, 0, 0};
        int[] secondNumbers = {4, 5, 6};
        long actualResult = Task31.calculateScalarProduct(firstNumbers, secondNumbers);
        assertEquals(0L, actualResult);
    }
}
