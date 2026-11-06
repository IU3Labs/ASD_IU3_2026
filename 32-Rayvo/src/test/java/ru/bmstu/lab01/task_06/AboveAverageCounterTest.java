package ru.bmstu.lab01.task_06;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AboveAverageCounterTest {

    @Test
    void shouldCountElementsAboveAverageInUnsortedArray() {
        int[] array = {5, 1, 9, 2, 8};
        // Сумма = 25, среднее = 5
        // Строго больше 5: 9, 8 → 2 элемента
        assertEquals(2, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldReturnZeroWhenAllElementsEqualAverage() {
        int[] array = {7, 7, 7, 7};
        // Среднее = 7, строго больше нет
        assertEquals(0, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldCountWhenOnlyOneElementAboveAverage() {
        int[] array = {1, 2, 3, 4, 10};
        // Сумма = 20, среднее = 4
        // Строго больше 4: только 10 → 1
        assertEquals(1, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldReturnZeroWhenArrayHasOneElement() {
        int[] array = {42};
        // Среднее = 42, строго больше нет
        assertEquals(0, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-10, -4, -6, -8};
        // Сумма = -28, среднее = -7
        // Строго больше -7: -4, -6 → 2
        assertEquals(2, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] array = {-4, 0, 4, 8};
        // Сумма = 8, среднее = 2
        // Строго больше 2: 4, 8 → 2
        assertEquals(2, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldHandleAllZeros() {
        int[] array = {0, 0, 0, 0};
        // Среднее = 0, строго больше нет
        assertEquals(0, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        int[] array = {Integer.MAX_VALUE, Integer.MAX_VALUE, 0, 0};
        // Сумма = 2 * MAX > int, но в long помещается
        // Среднее = MAX / 2
        // Строго больше: оба MAX → 2
        assertEquals(2, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldUseStrictInequality() {
        int[] array = {1, 2, 3};
        // Сумма = 6, среднее = 2
        // Строго больше 2: только 3 → 1
        // Если бы неравенство было нестрогим, было бы 2 (2 и 3)
        assertEquals(1, AboveAverageCounter.countGreaterThanAverage(array));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {5, 1, 9, 2, 8};
        int[] copy = original.clone();

        AboveAverageCounter.countGreaterThanAverage(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}