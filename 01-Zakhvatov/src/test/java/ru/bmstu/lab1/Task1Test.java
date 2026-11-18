package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task1Test {

    @Test
    void shouldFindMinAndMaxInRegularArray() {
        int[] input = {3, 1, 9, 2, 5};
        int[] expected = {1, 9};
        Assertions.assertArrayEquals(expected, Task1.findMinMax(input));
    }

    @Test
    void shouldFindMinAndMaxWithNegativeNumbers() {
        int[] input = {-10, -5, -20, 0, 7};
        int[] expected = {-20, 7};
        Assertions.assertArrayEquals(expected, Task1.findMinMax(input));
    }

    @Test
    void shouldFindMinAndMaxWhenAllElementsAreEqual() {
        int[] input = {4, 4, 4, 4};
        int[] expected = {4, 4};
        Assertions.assertArrayEquals(expected, Task1.findMinMax(input));
    }

    @Test
    void shouldFindMinAndMaxWithTwoElements() {
        int[] input = {100, -50};
        int[] expected = {-50, 100};
        Assertions.assertArrayEquals(expected, Task1.findMinMax(input));
    }
}
