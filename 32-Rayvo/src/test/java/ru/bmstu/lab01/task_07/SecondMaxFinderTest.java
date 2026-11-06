package ru.bmstu.lab01.task_07;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SecondMaxFinderTest {

    @Test
    void shouldFindSecondMaxInUnsortedArray() {
        int[] array = {8, 3, 15, 2, 11};
        assertEquals(11, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxWhenMaxIsFirst() {
        int[] array = {15, 3, 8, 2, 11};
        assertEquals(11, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxWhenMaxIsLast() {
        int[] array = {8, 3, 11, 2, 15};
        assertEquals(11, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxInSortedAscendingArray() {
        int[] array = {1, 3, 5, 7, 9};
        assertEquals(7, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxInSortedDescendingArray() {
        int[] array = {9, 7, 5, 3, 1};
        assertEquals(7, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxInArrayOfTwoElementsAscending() {
        int[] array = {10, 20};
        assertEquals(10, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxInArrayOfTwoElementsDescending() {
        int[] array = {20, 10};
        assertEquals(10, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxWithNegativeNumbers() {
        int[] array = {-8, -14, -2, -6};
        assertEquals(-6, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxWithMixedSigns() {
        int[] array = {-3, 5, 0, 8, -7};
        assertEquals(5, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxWhenSecondMaxIsNegative() {
        int[] array = {-5, -10, -3};
        assertEquals(-5, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldFindSecondMaxWithIntegerMinValue() {
        int[] array = {Integer.MIN_VALUE, 5};
        assertEquals(Integer.MIN_VALUE, SecondMaxFinder.findSecondMax(array));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {8, 3, 15, 2, 11};
        int[] copy = original.clone();

        SecondMaxFinder.findSecondMax(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}