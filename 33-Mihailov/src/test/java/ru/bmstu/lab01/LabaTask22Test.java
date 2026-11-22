/*Задача 22. Сортировка по частоте
Вход: массив из (n) целых чисел.
Выход: массив, отсортированный по убыванию частоты встречаемости элементов.
При равных частотах — по возрастанию значения.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask22Test {

    @Test
    void shouldSortByFrequencyDescending() {
        int[] input = {4, 5, 6, 5, 4, 3, 4};

        LabaTask22.sortByFrequency(input);

        // 4 встречается 3 раза, 5 — 2, 3 и 6 — по 1 (по возрастанию значения: 3, 6)
        int[] expected = {4, 4, 4, 5, 5, 3, 6};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortByValueWhenFrequenciesEqual() {
        int[] input = {3, 1, 2};

        LabaTask22.sortByFrequency(input);

        int[] expected = {1, 2, 3};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllSameElements() {
        int[] input = {7, 7, 7};

        LabaTask22.sortByFrequency(input);

        int[] expected = {7, 7, 7};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-1, 2, -1, 3, 2, -1};

        LabaTask22.sortByFrequency(input);

        // -1 встречается 3 раза, 2 — 2, 3 — 1
        int[] expected = {-1, -1, -1, 2, 2, 3};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortSingleElementArray() {
        int[] input = {42};

        LabaTask22.sortByFrequency(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask22.sortByFrequency(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleZeros() {
        int[] input = {0, 1, 0, -1, 0};

        LabaTask22.sortByFrequency(input);

        // 0 — 3 раза, -1 и 1 — по 1 (по возрастанию: -1, 1)
        int[] expected = {0, 0, 0, -1, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask22.sortByFrequency(null));
    }
}