package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task25Test {

    @Test
    void shouldCalculateSumOfPositiveNumbers() {
        int[] arr = {1, 2, 3, 4, 5};
        long exp = 15L;
        Assertions.assertEquals(exp, Task25.sum(arr));
    }

    @Test
    void shouldCalculateSumWithNegativeNumbers() {
        int[] arr = {-10, 5, -2, 7};
        long exp = 0L;
        Assertions.assertEquals(exp, Task25.sum(arr));
    }

    @Test
    void shouldHandleLargeNumbersWithoutOverflow() {
        int[] arr = {Integer.MAX_VALUE, Integer.MAX_VALUE};
        long exp = 2L * Integer.MAX_VALUE;
        Assertions.assertEquals(exp, Task25.sum(arr));
    }

    @Test
    void shouldCalculateSumWithZeros() {
        int[] arr = {0, 0, 0};
        long exp = 0L;
        Assertions.assertEquals(exp, Task25.sum(arr));
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {10, 20};
        long exp = 30L;
        Assertions.assertEquals(exp, Task25.sum(arr));
    }
}
