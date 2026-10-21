package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class Task26ProductOfElementsTest {

    @Test
    void should_return_correct_product_for_standard_array() {
        int[] array = {2, 3, 4};
        long result = Task26ProductOfElements.productElements(array);
        assertEquals(24, result);
    }

    @Test
    void should_handle_zero_in_array() {
        int[] array = {5, 10, 0, 20};
        long result = Task26ProductOfElements.productElements(array);
        assertEquals(0, result);
    }

    @Test
    void should_handle_negative_numbers() {
        int[] array = {-2, 3, -4};
        long result = Task26ProductOfElements.productElements(array);
        assertEquals(24, result); // (-2) * 3 * (-4) = 24
    }

    @Test
    void should_apply_modulo_correctly() {
        int[] array = {1_000_000_000, 2};
        // (1,000,000,000 * 2) % 10^9+7 = 2,000,000,000 % 10,000,000,007 = 1999999993
        long result = Task26ProductOfElements.productElements(array);
        assertEquals(1999999993L, result);
    }

    @Test
    void should_return_one_for_empty_array() {
        int[] array = {};
        long result = Task26ProductOfElements.productElements(array);
        assertEquals(1, result);
    }
}