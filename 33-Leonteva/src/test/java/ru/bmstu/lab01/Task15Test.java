package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 15. Пузырьковая сортировка с подсчётом обменов"
class Task15Test {

    @Test
    void shouldSortUnsortedArrayAndCountSwaps() {
        int[] input = {5, 1, 4, 2, 8};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {1, 2, 4, 5, 8};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(4, actual.swaps());
    }

    @Test
    void shouldReturnZeroSwapsForAlreadySortedArray() {
        int[] input = {1, 2, 3, 4, 5};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(0, actual.swaps());
    }

    @Test
    void shouldHandleReversedArray() {
        int[] input = {5, 4, 3, 2, 1};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {1, 2, 3, 4, 5};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(10, actual.swaps());
    }

    @Test
    void shouldHandleDuplicates() {
        int[] input = {3, 1, 3, 2, 1};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {1, 1, 2, 3, 3};
        assertArrayEquals(expectedSorted, actual.sorted());
        // пары в обратном порядке: (3,1),(3,2),(3,1),(3,2),(2,1) = 5
        assertEquals(6, actual.swaps());
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-3, 0, -10, 5, -1};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {-10, -3, -1, 0, 5};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(4, actual.swaps());
    }

    @Test
    void shouldHandleSingleElement() {
        int[] input = {42};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {42};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(0, actual.swaps());
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(0, actual.swaps());
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, 0, Integer.MIN_VALUE};

        Task15.Result actual = Task15.bubbleSort(input);

        int[] expectedSorted = {Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        assertArrayEquals(expectedSorted, actual.sorted());
        assertEquals(3, actual.swaps());
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task15.bubbleSort(null));
    }
}