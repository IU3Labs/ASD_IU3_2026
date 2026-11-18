package ru.bmstu.lab1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Task37Test {

    @Test
    void shouldReverseOddLengthArray() {
        int[] arr = {1, 2, 3, 4, 5};
        int[] exp = {5, 4, 3, 2, 1};
        Assertions.assertArrayEquals(exp, Task37.reverse(arr));
    }

    @Test
    void shouldReverseEvenLengthArray() {
        int[] arr = {1, 2, 3, 4};
        int[] exp = {4, 3, 2, 1};
        Assertions.assertArrayEquals(exp, Task37.reverse(arr));
    }

    @Test
    void shouldReverseArrayWithNegatives() {
        int[] arr = {-5, 10, -20};
        int[] exp = {-20, 10, -5};
        Assertions.assertArrayEquals(exp, Task37.reverse(arr));
    }

    @Test
    void shouldWorkOnMinimalArraySize() {
        int[] arr = {10, 20};
        int[] exp = {20, 10};
        Assertions.assertArrayEquals(exp, Task37.reverse(arr));
    }
}
