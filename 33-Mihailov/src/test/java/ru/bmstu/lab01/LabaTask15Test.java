/*Задача 15. Пузырьковая сортировка с подсчётом обменов
Вход: массив из (n) целых чисел.
Выход: отсортированный массив и количество обменов, выполненных в процессе пузырьковой сортировки.
Требование: реализовать пузырьковую сортировку с досрочным выходом, если массив уже отсортирован.
Сложность: базовая*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask15Test {

    @Test
    void shouldSortAndCountSwapsForUnsortedArray() {
        int[] input = {5, 1, 4, 2, 3};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {1, 2, 3, 4, 5};
        long expectedSwaps = 6;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldReturnZeroSwapsForAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {1, 2, 3, 4, 5};
        long expectedSwaps = 0;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldCountMaximumSwapsForReverseSortedArray() {
        int[] input = {5, 4, 3, 2, 1};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {1, 2, 3, 4, 5};
        // Число инверсий для массива из 5 элементов: 5*4/2 = 10
        long expectedSwaps = 10;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldSortArrayWithDuplicates() {
        int[] input = {3, 1, 3, 2, 1};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {1, 1, 2, 3, 3};
        long expectedSwaps = 6;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-2, 5, -3, 0, 1};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {-3, -2, 0, 1, 5};
        long expectedSwaps = 4;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {42};
        long expectedSwaps = 0;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        long actualSwaps = LabaTask15.bubbleSort(input);

        int[] expectedArray = {};
        long expectedSwaps = 0;
        assertArrayEquals(expectedArray, input);
        assertEquals(expectedSwaps, actualSwaps);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask15.bubbleSort(null));
    }
}