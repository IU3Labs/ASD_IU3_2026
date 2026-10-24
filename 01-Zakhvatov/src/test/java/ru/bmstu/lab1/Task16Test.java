package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task16Test {

    @Test
    void shouldSortMixedNumbersByAbsoluteValue() {
        int[] arr = {3, -2, 1, -5, 4};
        int[] expected = {1, -2, 3, 4, -5};
        Assertions.assertArrayEquals(expected, Task16.sortByAbsoluteValue(arr));
    }

    @Test
    void shouldPlaceNegativeFirstWhenAbsoluteValuesAreEqual() {
        int[] arr = {5, -5, 2, -2};
        int[] expected = {-2, 2, -5, 5};
        Assertions.assertArrayEquals(expected, Task16.sortByAbsoluteValue(arr));
    }

    @Test
    void shouldSortAllPositiveNumbers() {
        int[] arr = {4, 1, 3};
        int[] expected = {1, 3, 4};
        Assertions.assertArrayEquals(expected, Task16.sortByAbsoluteValue(arr));
    }

    @Test
    void shouldSortAllNegativeNumbers() {
        int[] arr = {-4, -1, -3};
        int[] expected = {-1, -3, -4};
        Assertions.assertArrayEquals(expected, Task16.sortByAbsoluteValue(arr));
    }

    @Test
    void shouldWorkWithMinimalArraySize() {
        int[] arr = {5, -5};
        int[] expected = {-5, 5};
        Assertions.assertArrayEquals(expected, Task16.sortByAbsoluteValue(arr));
    }
}
