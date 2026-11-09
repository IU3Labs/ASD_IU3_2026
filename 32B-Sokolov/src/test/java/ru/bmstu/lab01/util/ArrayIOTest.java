package ru.bmstu.lab01.util;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class ArrayIOTest {

    @Test
    void shouldReadArray() {
        int[] actual = ArrayIO.readArray(new Scanner("3\n5 -2 7"));
        assertArrayEquals(new int[] {5, -2, 7}, actual);
    }

    @Test
    void shouldJoinWithSpaces() {
        assertEquals("1 -2 3", ArrayIO.join(new int[] {1, -2, 3}));
    }

    @Test
    void shouldReturnEmptyStringForEmptyArray() {
        assertEquals("", ArrayIO.join(new int[0]));
    }
}
