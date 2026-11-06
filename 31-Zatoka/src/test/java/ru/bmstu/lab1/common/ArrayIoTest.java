package ru.bmstu.lab1.common;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;
import org.junit.jupiter.api.Test;

class ArrayIoTest {

    @Test
    void shouldReadArrayAfterItsLength() {
        Scanner scanner = new Scanner("3\n10 -20 30\n");
        int[] expected = {10, -20, 30};
        assertArrayEquals(expected, ArrayIo.readArray(scanner));
    }

    @Test
    void shouldReadEmptyArray() {
        Scanner scanner = new Scanner("0\n");
        int[] expected = {};
        assertArrayEquals(expected, ArrayIo.readArray(scanner));
    }

    @Test
    void shouldJoinElementsWithSpaces() {
        int[] arr = {1, -2, 3};
        assertEquals("1 -2 3", ArrayIo.toLine(arr));
    }

    @Test
    void shouldReturnEmptyLineForEmptyArray() {
        int[] arr = {};
        assertEquals("", ArrayIo.toLine(arr));
    }

    @Test
    void shouldJoinOnlyFirstElements() {
        int[] arr = {4, 5, 6, 7};
        assertEquals("4 5", ArrayIo.toLine(arr, 2));
    }
}
