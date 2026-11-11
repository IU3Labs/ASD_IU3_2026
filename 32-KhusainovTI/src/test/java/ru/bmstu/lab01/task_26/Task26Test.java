package ru.bmstu.lab01.task_26;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Task26Test {

    @Test
    void shouldCalculateProduct() {
        int[] numbers = {2, 3, 4};
        long actualProduct = Task26.calculateProduct(numbers);
        assertEquals(24L, actualProduct);
    }

    @Test
    void shouldReturnZeroWhenArrayContainsZero() {
        int[] numbers = {5, 0, 7};
        long actualProduct = Task26.calculateProduct(numbers);
        assertEquals(0L, actualProduct);
    }

    @Test
    void shouldCalculateProductWithNegativeNumber() {
        int[] numbers = {-2, 3};
        long actualProduct = Task26.calculateProduct(numbers);
        assertEquals(1_000_000_001L, actualProduct);
    }
}
