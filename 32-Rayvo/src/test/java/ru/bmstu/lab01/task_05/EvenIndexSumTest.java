package ru.bmstu.lab01.task_05;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EvenIndexSumTest {

    @Test
    void shouldSumEvenIndicesInUnsortedArray() {
        int[] array = {4, 9, 2, 7, 5};
        // индексы 0, 2, 4 → 4 + 2 + 5 = 11
        assertEquals(11L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldSumEvenIndicesWhenLengthIsOdd() {
        int[] array = {1, 2, 3, 4, 5, 6, 7};
        // индексы 0, 2, 4, 6 → 1 + 3 + 5 + 7 = 16
        assertEquals(16L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldSumOnlyFirstElementWhenArrayHasOneElement() {
        int[] array = {42};
        assertEquals(42L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldHandleAllZeros() {
        int[] array = {0, 0, 0, 0};
        assertEquals(0L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] array = {-8, 3, -14, 6, -2};
        // индексы 0, 2, 4 → -8 + (-14) + (-2) = -24
        assertEquals(-24L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldHandleMixedSigns() {
        int[] array = {-5, 10, 0, -3, 7};
        // индексы 0, 2, 4 → -5 + 0 + 7 = 2
        assertEquals(2L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        int[] array = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE};
        // 2 * Integer.MAX_VALUE = 4294967294, что больше int
        assertEquals(2L * Integer.MAX_VALUE, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldUseZeroBasedIndexingNotOneBased() {
        int[] array = {10, 20, 30, 40};
        // Чётные индексы 0, 2 → 10 + 30 = 40
        // Если бы считали «с 1-й позиции», было бы 20 + 40 = 60
        assertEquals(40L, EvenIndexSum.sumEvenIndices(array));
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {4, 9, 2, 7, 5};
        int[] copy = original.clone();

        EvenIndexSum.sumEvenIndices(original);

        for (int i = 0; i < original.length; i++) {
            assertEquals(copy[i], original[i]);
        }
    }
}