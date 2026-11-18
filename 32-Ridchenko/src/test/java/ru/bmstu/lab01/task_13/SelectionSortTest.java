package ru.bmstu.lab01.task_13;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortTest {

    @Test
    public void testSortNormal() {
        int[] input = {5, 2, 9, 1, 7};
        int[] expected = {1, 2, 5, 7, 9};
        assertArrayEquals(expected, SelectionSort.sort(input));
    }

    @Test
    public void testSortAlreadySorted() {
        int[] input = {1, 2, 3, 4, 5};
        int[] expected = {1, 2, 3, 4, 5};
        assertArrayEquals(expected, SelectionSort.sort(input));
    }

    @Test
    public void testSortWithDuplicates() {
        int[] input = {3, 1, 3, 2, 1};
        int[] expected = {1, 1, 2, 3, 3};
        assertArrayEquals(expected, SelectionSort.sort(input)); 
    }

    @Test
    public void testSortEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, SelectionSort.sort(input));
    }
}