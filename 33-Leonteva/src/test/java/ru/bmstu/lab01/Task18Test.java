package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 18. Сортировка только чётных"
class Task18Test {

    @Test
    void shouldSortEvenElementsKeepingOddInPlace() {
        int[] input = {5, 4, 3, 2, 1, 6};

        Task18.sortEvenOnly(input);

        // чётные: 4, 2, 6 → 2, 4, 6 на позициях 1, 3, 5
        int[] expected = {5, 2, 3, 4, 1, 6};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleOnlyEvenElements() {
        int[] input = {8, 6, 4, 2};

        Task18.sortEvenOnly(input);

        int[] expected = {2, 4, 6, 8};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepArrayWhenOnlyOddElements() {
        int[] input = {5, 3, 1, 7};

        Task18.sortEvenOnly(input);

        int[] expected = {5, 3, 1, 7};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleDuplicatesAndZeros() {
        int[] input = {3, 0, 4, 0, 2, 7};

        Task18.sortEvenOnly(input);

        // чётные: 0, 4, 0, 2 → 0, 0, 2, 4 на позициях 1, 2, 3, 4
        int[] expected = {3, 0, 0, 2, 4, 7};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-4, 3, -2, 5, -8};

        Task18.sortEvenOnly(input);

        // чётные: -4, -2, -8 → -8, -4, -2 на позициях 0, 2, 4
        int[] expected = {-8, 3, -4, 5, -2};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElement() {
        int[] input = {42};

        Task18.sortEvenOnly(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        Task18.sortEvenOnly(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task18.sortEvenOnly(null));
    }
}