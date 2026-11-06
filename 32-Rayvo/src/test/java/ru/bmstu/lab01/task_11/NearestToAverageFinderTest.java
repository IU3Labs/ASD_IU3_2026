package ru.bmstu.lab01.task_11;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NearestToAverageFinderTest {

    @Test
    void shouldFindClosestWhenAverageIsInteger() {
        int[] array = {2, 4, 6};
        // Сумма 12, среднее 4 — ровно элемент 4
        assertEquals(4, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldFindClosestWhenAverageIsFractional() {
        int[] array = {1, 2, 4};
        // Сумма 7, среднее 7/3 ≈ 2.333 — ближе всего 2
        assertEquals(2, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldReturnFirstWhenTie() {
        int[] array = {1, 2, 3, 4};
        // Сумма 10, среднее 2.5 — 2 и 3 одинаково близки, ответ 2 (первый)
        assertEquals(2, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldReturnFirstWhenTieOnSymmetricValues() {
        int[] array = {-5, 5};
        // Сумма 0, среднее 0 — -5 и 5 одинаково близки, ответ -5 (первый)
        assertEquals(-5, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldHandleSingleElement() {
        assertEquals(7, NearestToAverageFinder.findClosestToAverage(new int[]{7}));
    }

    @Test
    void shouldHandleAllEqualElements() {
        int[] array = {3, 3, 3, 3};
        assertEquals(3, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-4, -1, 0};
        // Сумма -5, среднее -5/3 ≈ -1.666 — ближе всего -1
        assertEquals(-1, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] array = {-3, 5, 0, 8, -7};
        // Сумма 3, среднее 0.6 — ближе всего 0 (расстояние 0.6)
        assertEquals(0, NearestToAverageFinder.findClosestToAverage(array));
    }

    @Test
    void shouldHandleLargeValuesWithoutOverflow() {
        int[] array = {Integer.MAX_VALUE, Integer.MIN_VALUE};
        // Сумма -1, среднее -0.5 — оба элемента одинаково близки, ответ MAX (первый)
        assertEquals(
                Integer.MAX_VALUE,
                NearestToAverageFinder.findClosestToAverage(array)
        );
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {1, 2, 3, 4};
        int[] copy = original.clone();

        NearestToAverageFinder.findClosestToAverage(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}