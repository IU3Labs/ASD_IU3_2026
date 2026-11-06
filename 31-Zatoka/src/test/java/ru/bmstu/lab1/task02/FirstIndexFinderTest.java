package ru.bmstu.lab1.task02;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class FirstIndexFinderTest {

    @Test
    void shouldFindIndexInMiddle() {
        int[] arr = {4, 8, 15, 16, 23};
        assertEquals(2, FirstIndexFinder.findFirstIndex(arr, 15));
    }

    @Test
    void shouldReturnFirstIndexWhenElementRepeats() {
        int[] arr = {7, 3, 7, 3};
        assertEquals(1, FirstIndexFinder.findFirstIndex(arr, 3));
    }

    @Test
    void shouldFindFirstElement() {
        int[] arr = {5, 1, 2};
        assertEquals(0, FirstIndexFinder.findFirstIndex(arr, 5));
    }

    @Test
    void shouldFindLastElement() {
        int[] arr = {5, 1, 2};
        assertEquals(2, FirstIndexFinder.findFirstIndex(arr, 2));
    }

    @Test
    void shouldReturnMinusOneWhenElementIsMissing() {
        int[] arr = {1, 2, 3};
        assertEquals(FirstIndexFinder.NOT_FOUND, FirstIndexFinder.findFirstIndex(arr, 10));
    }

    @Test
    void shouldReturnMinusOneForEmptyArray() {
        int[] arr = {};
        assertEquals(FirstIndexFinder.NOT_FOUND, FirstIndexFinder.findFirstIndex(arr, 1));
    }
}
