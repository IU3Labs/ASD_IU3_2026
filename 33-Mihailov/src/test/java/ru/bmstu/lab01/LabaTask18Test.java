/*Задача 18. Сортировка только чётных
Вход: массив из (n) целых чисел.
Выход: тот же массив, в котором все чётные элементы отсортированы по неубыванию,
а нечётные остались на своих местах.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask18Test {

    @Test
    void shouldSortOnlyEvenElements() {
        int[] input = {4, 3, 2, 7, 8, 1, 6};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {2, 3, 4, 7, 6, 1, 8};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldKeepOddElementsInPlace() {
        int[] input = {9, 4, 7, 2, 5};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {9, 2, 7, 4, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNoEvenNumbers() {
        int[] input = {3, 7, 1, 9};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {3, 7, 1, 9};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNoOddNumbers() {
        int[] input = {8, 4, 6, 2};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {2, 4, 6, 8};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeEvenNumbers() {
        int[] input = {-4, 1, -2, 3, -8};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {-8, 1, -4, 3, -2};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleZerosAsEven() {
        int[] input = {0, 3, 2, 1, 0};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {0, 3, 0, 1, 2};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask18.sortEvenOnly(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask18.sortEvenOnly(null));
    }
}