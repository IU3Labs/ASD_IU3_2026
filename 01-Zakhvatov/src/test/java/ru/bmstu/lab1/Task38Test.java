package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task38Test {

    @Test
    void shouldRotateLeftByTwoPositions() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] exp = {3, 4, 5, 1, 2};
        Assertions.assertArrayEquals(exp, Task38.rotateLeft(arr, 2));
    }

    @Test
    void shouldHandleKGreaterThanLength() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] exp = {3, 4, 5, 1, 2};
        Assertions.assertArrayEquals(exp, Task38.rotateLeft(arr, 7));
    }

    @Test
    void shouldNotChangeArrayWhenKIsZeroOrMultipleOfLength() {
        int[] arr = {1, 2, 3};
        int[] exp = {1, 2, 3};
        Assertions.assertArrayEquals(exp, Task38.rotateLeft(arr, 3));
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {10, 20};
        int[] exp = {20, 10};
        Assertions.assertArrayEquals(exp, Task38.rotateLeft(arr, 1));
    }
}
