/*Задача 19. Объединение двух отсортированных массивов
Вход: два массива, каждый отсортирован по неубыванию; размеры (n) и (m).
Выход: один отсортированный массив — результат слияния.
Требование: O(n + m).
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask19Test {

    @Test
    void shouldMergeTwoSortedArrays() {
        int[] first = {1, 3, 5};
        int[] second = {2, 4, 6};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {1, 2, 3, 4, 5, 6};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldMergeWithDuplicates() {
        int[] first = {1, 2, 2};
        int[] second = {2, 3, 3};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {1, 2, 2, 2, 3, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldMergeWhenFirstArrayIsEmpty() {
        int[] first = {};
        int[] second = {1, 2, 3};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldMergeWhenSecondArrayIsEmpty() {
        int[] first = {1, 2, 3};
        int[] second = {};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldMergeWhenBothArraysAreEmpty() {
        int[] first = {};
        int[] second = {};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldMergeWithNegativeNumbers() {
        int[] first = {-10, -5, 0};
        int[] second = {-7, -2, 3};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {-10, -7, -5, -2, 0, 3};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldMergeWhenAllElementsOfFirstAreSmaller() {
        int[] first = {1, 2, 3};
        int[] second = {10, 20, 30};

        int[] actual = LabaTask19.merge(first, second);

        int[] expected = {1, 2, 3, 10, 20, 30};
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldThrowWhenFirstArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask19.merge(null, new int[] {1}));
    }

    @Test
    void shouldThrowWhenSecondArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask19.merge(new int[] {1}, null));
    }
}