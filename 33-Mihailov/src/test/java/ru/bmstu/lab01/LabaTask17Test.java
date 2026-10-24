/*Задача 17. Сортировка по сумме цифр
Вход: массив из (n) неотрицательных целых чисел.
Выход: массив, отсортированный по возрастанию суммы цифр каждого числа.
При равных суммах — сохранить исходный порядок.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask17Test {

    @Test
    void shouldSortByDigitSumAscending() {
        int[] input = {12, 5, 21, 100};

        LabaTask17.sortByDigitSum(input);

        int[] expected = {100, 12, 21, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldPreserveOrderForEqualDigitSums() {
        int[] input = {12, 21, 3, 30};

        LabaTask17.sortByDigitSum(input);

        // Суммы цифр: 3, 3, 3, 3 — все равны, порядок сохраняется.
        int[] expected = {12, 21, 3, 30};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldSortNumbersWithZeros() {
        int[] input = {10, 0, 5, 100};

        LabaTask17.sortByDigitSum(input);

        int[] expected = {0, 10, 100, 5};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllZeros() {
        int[] input = {0, 0, 0};

        LabaTask17.sortByDigitSum(input);

        int[] expected = {0, 0, 0};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {999};

        LabaTask17.sortByDigitSum(input);

        int[] expected = {999};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask17.sortByDigitSum(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleLargeNumbers() {
        int[] input = {99999, 100000, 18};

        LabaTask17.sortByDigitSum(input);

        // суммы: 45, 1, 9
        int[] expected = {100000, 18, 99999};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask17.sortByDigitSum(null));
    }
}