package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task40Test {

    @Test
    void shouldRemoveDuplicatesFromSortedArray() {
        int[] arr = {1, 1, 2, 2, 3, 4, 4};
        int[] exp = {1, 2, 3, 4};
        Assertions.assertArrayEquals(exp, Task40.removeDuplicates(arr));
    }

    @Test
    void shouldKeepArrayWhenNoDuplicatesExist() {
        int[] arr = {1, 2, 3, 4};
        int[] exp = {1, 2, 3, 4};
        Assertions.assertArrayEquals(exp, Task40.removeDuplicates(arr));
    }

    @Test
    void shouldHandleAllIdenticalElements() {
        int[] arr = {5, 5, 5, 5};
        int[] exp = {5};
        Assertions.assertArrayEquals(exp, Task40.removeDuplicates(arr));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] arr = {-3, -3, -1, 0, 0, 2};
        int[] exp = {-3, -1, 0, 2};
        Assertions.assertArrayEquals(exp, Task40.removeDuplicates(arr));
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {4, 4};
        int[] exp = {4};
        Assertions.assertArrayEquals(exp, Task40.removeDuplicates(arr));
    }
}
