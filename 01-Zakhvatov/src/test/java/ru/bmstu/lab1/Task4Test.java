package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task4Test {

    @Test
    void shouldFindAllIndicesWhenMultipleOccurrencesExist() {
        int[] arr = {10, 20, 10, 30, 10};
        int[] expected = {0, 2, 4};
        Assertions.assertArrayEquals(expected, Task4.findAll(arr, 10));
    }

    @Test
    void shouldFindSingleIndexWhenOneOccurrenceExists() {
        int[] arr = {5, 1, 9, 3};
        int[] expected = {2};
        Assertions.assertArrayEquals(expected, Task4.findAll(arr, 9));
    }

    @Test
    void shouldReturnEmptyArrayWhenElementNotFound() {
        int[] arr = {1, 2, 3, 4};
        int[] expected = {};
        Assertions.assertArrayEquals(expected, Task4.findAll(arr, 99));
    }

    @Test
    void shouldFindAllIndicesWhenAllElementsMatch() {
        int[] arr = {7, 7, 7, 7};
        int[] expected = {0, 1, 2, 3};
        Assertions.assertArrayEquals(expected, Task4.findAll(arr, 7));
    }

    @Test
    void shouldWorkCorrectlyOnMinimalArraySize() {
        int[] arr = {4, 8};
        int[] expected = {1};
        Assertions.assertArrayEquals(expected, Task4.findAll(arr, 8));
    }
}
