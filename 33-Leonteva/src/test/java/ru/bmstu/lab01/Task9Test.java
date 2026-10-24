package ru.bmstu.lab01;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

//Тесты для "Задача 9. Проверка отсортированности"
class Task9Test {

    @Test
    void shouldReturnYesWhenArrayStrictlyIncreasing() {
        int[] input = {1, 2, 3, 4, 5};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnYesWhenArrayHasEqualNeighbors() {
        int[] input = {1, 1, 2, 2, 3};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnNoWhenArrayDecreasing() {
        int[] input = {5, 4, 3, 2, 1};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("NO", actual);
    }

    @Test
    void shouldReturnNoWhenSingleBreakInMiddle() {
        int[] input = {1, 3, 2, 4, 5};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("NO", actual);
    }

    @Test
    void shouldReturnYesForSingleElement() {
        int[] input = {42};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("YES", actual);
    }

    @Test
    void shouldReturnYesForEmptyArray() {
        int[] input = {};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("YES", actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] input = {-10, -5, -5, 0, 3};

        String actual = Task9.isNonDecreasing(input);

        assertEquals("YES", actual);
    }

    @Test
    void shouldThrowWhenArrayIsNull() {
        assertThrows(IllegalArgumentException.class, () -> Task9.isNonDecreasing(null));
    }
}