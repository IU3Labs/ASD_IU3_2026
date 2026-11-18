package ru.bmstu.lab01.task_41;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class RemoveElementTest {

    @Test
    public void testRemoveExisting() {
        int[] input = {5, 2, 9, 5, 7, 5};
        int[] expected = {2, 9, 7};
        assertArrayEquals(expected, RemoveElement.removeElement(input, 5));
    }

    @Test
    public void testRemoveNonExisting() {
        int[] input = {5, 2, 9, 1, 7};
        int[] expected = {5, 2, 9, 1, 7};
        assertArrayEquals(expected, RemoveElement.removeElement(input, 10));
    }

    @Test
    public void testRemoveAll() {
        int[] input = {5, 5, 5, 5};
        int[] expected = {};
        assertArrayEquals(expected, RemoveElement.removeElement(input, 5));
    }

    @Test
    public void testEmptyArray() {
        int[] input = {};
        int[] expected = {};
        assertArrayEquals(expected, RemoveElement.removeElement(input, 5));
    }
}