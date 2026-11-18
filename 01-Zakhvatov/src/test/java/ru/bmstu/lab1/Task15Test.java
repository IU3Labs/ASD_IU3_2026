package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task15Test {

    @Test
    void shouldReturnZeroSwapsWhenAlreadySorted() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] expectedArr = {1, 2, 3, 4, 5};
        Task15.Result result = Task15.bubbleSort(arr);

        Assertions.assertArrayEquals(expectedArr, result.sortedArray());
        Assertions.assertEquals(0, result.swapCount());
    }

    @Test
    void shouldSortReverseArrayWithCorrectSwapCount() {
        int[] arr = {5, 4, 3, 2, 1};
        int[] expectedArr = {1, 2, 3, 4, 5};
        Task15.Result result = Task15.bubbleSort(arr);

        Assertions.assertArrayEquals(expectedArr, result.sortedArray());
        Assertions.assertEquals(10, result.swapCount());
    }

    @Test
    void shouldSortRandomArrayAndCountSwaps() {
        int[] arr = {4, 3, 1, 2};
        int[] expectedArr = {1, 2, 3, 4};
        Task15.Result result = Task15.bubbleSort(arr);

        Assertions.assertArrayEquals(expectedArr, result.sortedArray());
        Assertions.assertEquals(5, result.swapCount());
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] arr = {3, 1, 2, 1};
        int[] expectedArr = {1, 1, 2, 3};
        Task15.Result result = Task15.bubbleSort(arr);

        Assertions.assertArrayEquals(expectedArr, result.sortedArray());
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {9, 3};
        int[] expectedArr = {3, 9};
        Task15.Result result = Task15.bubbleSort(arr);

        Assertions.assertArrayEquals(expectedArr, result.sortedArray());
        Assertions.assertEquals(1, result.swapCount());
    }
}
