package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 17. Сортировка по сумме цифр"
class Task17Test {

    @Test
    void shouldSortByDigitSum() {
        int[] input = {21, 100, 12, 3};

        Task17.sortByDigitSum(input);

        // суммы: 3, 1, 3, 3 → 100 (1), затем 21 (3), 12 (3), 3 (3)
        int[] expected = {100, 21, 12, 3};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldPreserveOrderOnEqualDigitSums() {
        int[] input = {12, 21, 30, 3};

        Task17.sortByDigitSum(input);

        // все суммы = 3, порядок не меняется
        int[] expected = {12, 21, 30, 3};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepAlreadySortedArray() {
        int[] input = {10, 2, 11, 19};

        Task17.sortByDigitSum(input);

        // суммы: 1, 2, 2, 10
        int[] expected = {10, 2, 11, 19};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleZeros() {
        int[] input = {0, 5, 0, 1};

        Task17.sortByDigitSum(input);

        // суммы: 0, 5, 0, 1 → 0, 0, 1, 5
        int[] expected = {0, 0, 1, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElement() {
        int[] input = {42};

        Task17.sortByDigitSum(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        Task17.sortByDigitSum(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleLargeNumbers() {
        int[] input = {Integer.MAX_VALUE, 1, 999};

        Task17.sortByDigitSum(input);

        // суммы: MAX_VALUE (2147483647) = 46, 1, 999 = 27 → 1, 999, MAX_VALUE
        int[] expected = {1, 999, Integer.MAX_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task17.sortByDigitSum(null));
    }
}