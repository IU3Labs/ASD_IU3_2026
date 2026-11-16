/*Задача 16. Сортировка по модулю
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по неубыванию модуля элементов.
При равных модулях первым идёт отрицательный элемент.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask16Test {

    @Test
    void shouldSortByAbsAscending() {
        int[] input = {3, -1, 2, -4};

        LabaTask16.sortByAbs(input);

        int[] expected = {-1, 2, 3, -4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldPlaceNegativeFirstWhenAbsEqual() {
        int[] input = {5, -5, 3, -3};

        LabaTask16.sortByAbs(input);

        int[] expected = {-3, 3, -5, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepZeros() {
        int[] input = {0, -2, 1, 0};

        LabaTask16.sortByAbs(input);

        int[] expected = {0, 0, 1, -2};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbersOnly() {
        int[] input = {-5, -1, -3};

        LabaTask16.sortByAbs(input);

        int[] expected = {-1, -3, -5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAlreadySortedByAbs() {
        int[] input = {-1, 2, -3, 4};

        LabaTask16.sortByAbs(input);

        int[] expected = {-1, 2, -3, 4};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleBoundaryValues() {
        int[] input = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0};

        LabaTask16.sortByAbs(input);

        // |MIN_VALUE| = 2^31 > |MAX_VALUE| = 2^31-1, поэтому порядок: 0, MAX, MIN
        int[] expected = {0, Integer.MAX_VALUE, Integer.MIN_VALUE};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {-42};

        LabaTask16.sortByAbs(input);

        int[] expected = {-42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask16.sortByAbs(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask16.sortByAbs(null));
    }
}