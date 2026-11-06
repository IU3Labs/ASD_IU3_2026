package ru.bmstu.lab01.task_04;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class AllIndicesFinderTest {

    @Test
    void shouldFindSingleIndex() {
        int[] array = {4, 9, 2, 7, 5};
        assertArrayEquals(
                new int[]{1},
                AllIndicesFinder.findAllIndices(array, 9)
        );
    }

    @Test
    void shouldFindMultipleIndicesInAscendingOrder() {
        int[] array = {4, 9, 4, 2, 4};
        assertArrayEquals(
                new int[]{0, 2, 4},
                AllIndicesFinder.findAllIndices(array, 4)
        );
    }

    @Test
    void shouldReturnEmptyArrayWhenElementNotFound() {
        int[] array = {4, 9, 4, 2, 4};
        assertArrayEquals(
                new int[0],
                AllIndicesFinder.findAllIndices(array, 7)
        );
    }

    @Test
    void shouldReturnAllIndicesWhenAllElementsEqual() {
        int[] array = {6, 6, 6, 6};
        assertArrayEquals(
                new int[]{0, 1, 2, 3},
                AllIndicesFinder.findAllIndices(array, 6)
        );
    }

    @Test
    void shouldHandleSingleElementArrayFound() {
        assertArrayEquals(
                new int[]{0},
                AllIndicesFinder.findAllIndices(new int[]{25}, 25)
        );
    }

    @Test
    void shouldHandleSingleElementArrayNotFound() {
        assertArrayEquals(
                new int[0],
                AllIndicesFinder.findAllIndices(new int[]{25}, 7)
        );
    }

    @Test
    void shouldFindNegativeNumbers() {
        int[] array = {-8, -14, -8, -2, -8};
        assertArrayEquals(
                new int[]{0, 2, 4},
                AllIndicesFinder.findAllIndices(array, -8)
        );
    }

    @Test
    void shouldFindMixedSigns() {
        int[] array = {-3, 5, 0, 8, 5, -7, 5};
        assertArrayEquals(
                new int[]{1, 4, 6},
                AllIndicesFinder.findAllIndices(array, 5)
        );
    }

    @Test
    void shouldFindZeroValue() {
        int[] array = {0, 3, 0, 8, 0};
        assertArrayEquals(
                new int[]{0, 2, 4},
                AllIndicesFinder.findAllIndices(array, 0)
        );
    }

    @Test
    void shouldFindIndicesAtEdges() {
        int[] array = {9, 1, 2, 3, 9};
        assertArrayEquals(
                new int[]{0, 4},
                AllIndicesFinder.findAllIndices(array, 9)
        );
    }

    @Test
    void shouldNotModifyOriginalArray() {
        int[] original = {4, 9, 4, 2, 4};
        int[] copy = original.clone();

        AllIndicesFinder.findAllIndices(original, 4);

        assertArrayEquals(copy, original);
    }
}