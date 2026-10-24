/*Задача 21. K-й наименьший элемент
Вход: массив из (n) целых чисел и число (k) (1 <= k <= n).
Выход: (k)-й наименьший элемент массива.
Требование: отсортировать массив и взять элемент с индексом (k - 1).
Сложность: базовая*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask21Test {

    @Test
    void shouldReturnFirstSmallestWhenKEqualsOne() {
        int[] input = {5, 1, 4, 2, 3};

        int actual = LabaTask21.kthSmallest(input, 1);

        int expected = 1;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnLastSmallestWhenKEqualsN() {
        int[] input = {5, 1, 4, 2, 3};

        int actual = LabaTask21.kthSmallest(input, 5);

        int expected = 5;
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnMiddleElementWhenKInMiddle() {
        int[] input = {5, 1, 4, 2, 3};

        int actual = LabaTask21.kthSmallest(input, 3);

        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleDuplicates() {
        int[] input = {2, 2, 3, 1, 1};

        int actual = LabaTask21.kthSmallest(input, 3);

        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 0, -10, 7, 2};

        int actual = LabaTask21.kthSmallest(input, 2);

        int expected = -3;
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] input = {3, 1, 2};
        int[] snapshot = input.clone();

        LabaTask21.kthSmallest(input, 2);

        assertArrayEquals(snapshot, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        int actual = LabaTask21.kthSmallest(input, 1);

        int expected = 42;
        assertEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask21.kthSmallest(null, 1));
    }

    @Test
    void shouldThrowWhenKIsZero() {
        int[] input = {1, 2, 3};

        assertThrows(IllegalArgumentException.class, () -> LabaTask21.kthSmallest(input, 0));
    }

    @Test
    void shouldThrowWhenKExceedsN() {
        int[] input = {1, 2, 3};

        assertThrows(IllegalArgumentException.class, () -> LabaTask21.kthSmallest(input, 4));
    }
}