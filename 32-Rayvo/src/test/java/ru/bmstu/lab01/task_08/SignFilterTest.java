package ru.bmstu.lab01.task_08;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SignFilterTest {

    @Test
    void shouldSplitMixedArrayPreservingOrder() {
        int[] array = {3, -1, 0, 7, -4, 2, -9};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        assertArrayEquals(new int[]{3, 7, 2}, result.positive());
        assertArrayEquals(new int[]{-1, -4, -9}, result.negative());
    }

    @Test
    void shouldReturnAllPositiveWhenNoNegative() {
        int[] array = {4, 8, 1, 6};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        assertArrayEquals(new int[]{4, 8, 1, 6}, result.positive());
        assertArrayEquals(new int[0], result.negative());
    }

    @Test
    void shouldReturnAllNegativeWhenNoPositive() {
        int[] array = {-4, -8, -1, -6};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        assertArrayEquals(new int[0], result.positive());
        assertArrayEquals(new int[]{-4, -8, -1, -6}, result.negative());
    }

    @Test
    void shouldDropZerosWhenAllElementsAreZero() {
        int[] array = {0, 0, 0, 0};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        assertArrayEquals(new int[0], result.positive());
        assertArrayEquals(new int[0], result.negative());
    }

    @Test
    void shouldDropZerosInMixedArray() {
        int[] array = {0, 5, 0, -3, 0};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        assertArrayEquals(new int[]{5}, result.positive());
        assertArrayEquals(new int[]{-3}, result.negative());
    }

    @Test
    void shouldHandleSinglePositiveElement() {
        SignFilter.FilterResult result = SignFilter.filterBySign(new int[]{25});

        assertArrayEquals(new int[]{25}, result.positive());
        assertArrayEquals(new int[0], result.negative());
    }

    @Test
    void shouldHandleSingleNegativeElement() {
        SignFilter.FilterResult result = SignFilter.filterBySign(new int[]{-25});

        assertArrayEquals(new int[0], result.positive());
        assertArrayEquals(new int[]{-25}, result.negative());
    }

    @Test
    void shouldHandleSingleZeroElement() {
        SignFilter.FilterResult result = SignFilter.filterBySign(new int[]{0});

        assertArrayEquals(new int[0], result.positive());
        assertArrayEquals(new int[0], result.negative());
    }

    @Test
    void shouldPreserveOrderOfPositives() {
        int[] array = {9, -1, 3, -2, 7, -3, 5};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        // Порядок 9, 3, 7, 5 — как в исходном массиве, без сортировки
        assertArrayEquals(new int[]{9, 3, 7, 5}, result.positive());
        assertArrayEquals(new int[]{-1, -2, -3}, result.negative());
    }

    @Test
    void shouldPreserveOrderOfNegatives() {
        int[] array = {-9, 1, -3, 2, -7, 3, -5};
        SignFilter.FilterResult result = SignFilter.filterBySign(array);

        assertArrayEquals(new int[]{1, 2, 3}, result.positive());
        assertArrayEquals(new int[]{-9, -3, -7, -5}, result.negative());
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {3, -1, 0, 7, -4, 2, -9};
        int[] copy = original.clone();

        SignFilter.filterBySign(original);

        assertArrayEquals(copy, original);
    }
}