package ru.bmstu.lab01.task_13;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task13Test {

    @Test
    void shouldSortArray() {
        int[] a = {5, -2, 9, 0, 3};
        Task13.selectionSort(a);
        assertArrayEquals(new int[] {-2, 0, 3, 5, 9}, a);
    }

    @Test
    void shouldSortWithDuplicates() {
        int[] a = {7, 7, 4, 4, 1};
        Task13.selectionSort(a);
        assertArrayEquals(new int[] {1, 4, 4, 7, 7}, a);
    }

    @Test
    void shouldNotChangeSortedArray() {
        int[] a = {1, 2, 3};
        Task13.selectionSort(a);
        assertArrayEquals(new int[] {1, 2, 3}, a);
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("0 1 2 3", TestUtils.runMain(Task13::main, "4\n3 1 2 0"));
    }
}
