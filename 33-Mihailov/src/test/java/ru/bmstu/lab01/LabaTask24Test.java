/*Задача 24. Чётно-нечётная сортировка
Вход: массив из (n) целых чисел.
Выход: массив, в котором все чётные элементы отсортированы по возрастанию и идут первыми,
а нечётные отсортированы по убыванию и идут после.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask24Test {

    @Test
    void shouldSortEvenAscendingFirstOddDescendingLast() {
        int[] input = {5, 2, 8, 3, 1, 6, 4, 7};

        LabaTask24.evenOddSort(input);

        int[] expected = {2, 4, 6, 8, 7, 5, 3, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllEvenElements() {
        int[] input = {8, 2, 6, 4};

        LabaTask24.evenOddSort(input);

        int[] expected = {2, 4, 6, 8};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleAllOddElements() {
        int[] input = {1, 7, 3, 5};

        LabaTask24.evenOddSort(input);

        int[] expected = {7, 5, 3, 1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-4, 3, -2, 7, 0, -1};

        LabaTask24.evenOddSort(input);

        // чётные по возрастанию: -4, -2, 0; нечётные по убыванию: 7, 3, -1
        int[] expected = {-4, -2, 0, 7, 3, -1};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleDuplicates() {
        int[] input = {2, 3, 2, 3, 4};

        LabaTask24.evenOddSort(input);

        int[] expected = {2, 2, 4, 3, 3};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleSingleElementArray() {
        int[] input = {42};

        LabaTask24.evenOddSort(input);

        int[] expected = {42};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldHandleEmptyArray() {
        int[] input = {};

        LabaTask24.evenOddSort(input);

        int[] expected = {};
        assertArrayEquals(expected, input);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask24.evenOddSort(null));
    }
}