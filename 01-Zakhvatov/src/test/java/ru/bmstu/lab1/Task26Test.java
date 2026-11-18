package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task26Test {

    @Test
    void shouldCalculateProductOfSmallNumbers() {
        int[] arr = {2, 3, 4};
        long exp = 24L;
        Assertions.assertEquals(exp, Task26.product(arr));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-2, 3};
        long exp = 1_000_000_001L;
        Assertions.assertEquals(exp, Task26.product(arr));
    }

    @Test
    void shouldReturnZeroWhenZeroPresent() {
        int[] arr = {5, 0, 10};
        long exp = 0L;
        Assertions.assertEquals(exp, Task26.product(arr));
    }

    @Test
    void shouldApplyModuloOnLargeProduct() {
        int[] arr = {1_000_000, 2_000};
        long exp = (2_000_000_000L) % 1_000_000_007L;
        Assertions.assertEquals(exp, Task26.product(arr));
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {7, 8};
        long exp = 56L;
        Assertions.assertEquals(exp, Task26.product(arr));
    }
}
