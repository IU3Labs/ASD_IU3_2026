package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class Task01MaxMinTest {
    @Test
    void shouldReturnMinMaxForMixedArray() {
        assertArrayEquals(new int[]{1, 9}, Task01MaxMin.findMinMax(new int[]{4, 2, 9, 1, 7}));
    }
    @Test
    void shouldHandleAllNegatives() {
        assertArrayEquals(new int[]{-25, -1}, Task01MaxMin.findMinMax(new int[]{-10, -3, -25, -1}));
    }
    @Test
    void shouldHandleTwoElements() {
        assertArrayEquals(new int[]{-100, 100}, Task01MaxMin.findMinMax(new int[]{100, -100}));
    }
}