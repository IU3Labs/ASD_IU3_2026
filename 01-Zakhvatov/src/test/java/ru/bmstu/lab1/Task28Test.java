package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task28Test {

    @Test
    void shouldFindMedianForOddLengthArray() {
        int[] arr = {5, 2, 9, 1, 7};
        double exp = 5.0;
        Assertions.assertEquals(exp, Task28.findMedian(arr), 1e-9);
    }

    @Test
    void shouldFindMedianForEvenLengthArray() {
        int[] arr = {4, 1, 7, 2};
        double exp = 3.0;
        Assertions.assertEquals(exp, Task28.findMedian(arr), 1e-9);
    }

    @Test
    void shouldCalculateFractionalMedianForEvenLength() {
        int[] arr = {1, 2, 3, 4};
        double exp = 2.5;
        Assertions.assertEquals(exp, Task28.findMedian(arr), 1e-9);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-5, -10, 0};
        double exp = -5.0;
        Assertions.assertEquals(exp, Task28.findMedian(arr), 1e-9);
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {10, 20};
        double exp = 15.0;
        Assertions.assertEquals(exp, Task28.findMedian(arr), 1e-9);
    }
}
