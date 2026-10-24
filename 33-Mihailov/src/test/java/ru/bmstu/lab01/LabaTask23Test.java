/*Задача 23. Проверка анаграммы массивов
Вход: два массива одинаковой длины (n).
Выход: YES, если один массив является перестановкой другого; NO иначе.
Сложность: средняя*/

package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class LabaTask23Test {

    @Test
    void shouldReturnYesForPermutations() {
        int[] first = {1, 2, 3, 4};
        int[] second = {4, 3, 2, 1};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnNoForDifferentMultisets() {
        int[] first = {1, 2, 3};
        int[] second = {1, 2, 4};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("NO", actual);
    }

    @Test
    void shouldReturnYesForDuplicates() {
        int[] first = {1, 1, 2, 2};
        int[] second = {2, 1, 2, 1};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnNoWhenFrequenciesDiffer() {
        int[] first = {1, 1, 2};
        int[] second = {1, 2, 2};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("NO", actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] first = {-1, 0, 1};
        int[] second = {1, -1, 0};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnYesForEmptyArrays() {
        int[] first = {};
        int[] second = {};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnNoWhenLengthsDiffer() {
        int[] first = {1, 2, 3};
        int[] second = {1, 2};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("NO", actual);
    }

    @Test
    void shouldReturnYesForSingleElement() {
        int[] first = {42};
        int[] second = {42};

        String actual = LabaTask23.areAnagrams(first, second);

        assertEquals("YES", actual);
    }

    @Test
    void shouldThrowWhenFirstArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask23.areAnagrams(null, new int[] {1}));
    }

    @Test
    void shouldThrowWhenSecondArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> LabaTask23.areAnagrams(new int[] {1}, null));
    }
}