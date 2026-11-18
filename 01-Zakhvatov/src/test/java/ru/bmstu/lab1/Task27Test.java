package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task27Test {

    @Test
    void shouldCalculateSumOfSquaresForPositiveNumbers() {
        int[] arr = {1, 2, 3};
        long exp = 14L;
        Assertions.assertEquals(exp, Task27.sumOfSquares(arr));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-2, -3};
        long exp = 13L;
        Assertions.assertEquals(exp, Task27.sumOfSquares(arr));
    }

    @Test
    void shouldHandleZeros() {
        int[] arr = {0, 0, 5};
        long exp = 25L;
        Assertions.assertEquals(exp, Task27.sumOfSquares(arr));
    }

    @Test
    void shouldHandleLargeNumbersWithoutOverflow() {
        int[] arr = {100_000, 100_000};
        long exp = 20_000_000_000L;
        Assertions.assertEquals(exp, Task27.sumOfSquares(arr));
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {3, 4};
        long exp = 25L;
        Assertions.assertEquals(exp, Task27.sumOfSquares(arr));
    }
}
