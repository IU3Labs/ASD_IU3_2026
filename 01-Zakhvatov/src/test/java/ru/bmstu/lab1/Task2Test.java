package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task2Test {

    @Test
    void shouldReturnCorrectIndexWhenElementExists() {
        int[] arr = {10, 20, 30, 40};
        Assertions.assertEquals(2, Task2.findFirst(arr, 30));
    }

    @Test
    void shouldReturnFirstIndexWhenDuplicatesExist() {
        int[] arr = {7, 3, 7, 7, 1};
        Assertions.assertEquals(0, Task2.findFirst(arr, 7));
    }

    @Test
    void shouldReturnMinusOneWhenElementNotFound() {
        int[] arr = {1, 2, 3, 4};
        Assertions.assertEquals(-1, Task2.findFirst(arr, 99));
    }

    @Test
    void shouldFindElementAtLastIndex() {
        int[] arr = {4, 8, 15, 16};
        Assertions.assertEquals(3, Task2.findFirst(arr, 16));
    }
}
