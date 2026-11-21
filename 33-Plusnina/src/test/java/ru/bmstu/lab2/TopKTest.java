
package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Модульные тесты для поиска k наибольших элементов.
 */
public class TopKTest {

    @Test
    void findsThreeLargestElements() {
        int[] numbers = {4, 1, 9, 2, 7, 5};

        assertArrayEquals(
                new int[]{9, 7, 5},
                TopK.topK(numbers, 3)
        );
    }

    @Test
    void findsSingleLargestElement() {
        int[] numbers = {3, 10, 6, 1};

        assertArrayEquals(
                new int[]{10},
                TopK.topK(numbers, 1)
        );
    }

    @Test
    void returnsAllElementsInDescendingOrder() {
        int[] numbers = {3, 1, 4, 2};

        assertArrayEquals(
                new int[]{4, 3, 2, 1},
                TopK.topK(numbers, 4)
        );
    }

    @Test
    void handlesNegativeNumbers() {
        int[] numbers = {-8, -2, -10, -1};

        assertArrayEquals(
                new int[]{-1, -2},
                TopK.topK(numbers, 2)
        );
    }

    @Test
    void handlesMixedPositiveAndNegativeNumbers() {
        int[] numbers = {-5, 0, 12, -1, 7};

        assertArrayEquals(
                new int[]{12, 7, 0},
                TopK.topK(numbers, 3)
        );
    }

    @Test
    void handlesDuplicateElements() {
        int[] numbers = {5, 9, 9, 2, 5};

        assertArrayEquals(
                new int[]{9, 9, 5},
                TopK.topK(numbers, 3)
        );
    }

    @Test
    void handlesAlreadySortedArray() {
        int[] numbers = {9, 7, 5, 3, 1};

        assertArrayEquals(
                new int[]{9, 7, 5},
                TopK.topK(numbers, 3)
        );
    }

    @Test
    void handlesReverseSortedArray() {
        int[] numbers = {1, 3, 5, 7, 9};

        assertArrayEquals(
                new int[]{9, 7, 5},
                TopK.topK(numbers, 3)
        );
    }

    @Test
    void handlesArrayWithEqualElements() {
        int[] numbers = {4, 4, 4, 4};

        assertArrayEquals(
                new int[]{4, 4},
                TopK.topK(numbers, 2)
        );
    }

    @Test
    void handlesZeroK() {
        int[] numbers = {1, 2, 3};

        assertArrayEquals(
                new int[0],
                TopK.topK(numbers, 0)
        );
    }

    @Test
    void rejectsKGreaterThanArrayLength() {
        int[] numbers = {1, 2, 3};

        assertThrows(
                IllegalArgumentException.class,
                () -> TopK.topK(numbers, 4)
        );
    }

    @Test
    void rejectsNegativeK() {
        int[] numbers = {1, 2, 3};

        assertThrows(
                IllegalArgumentException.class,
                () -> TopK.topK(numbers, -1)
        );
    }

    @Test
    void rejectsNullArray() {
        assertThrows(
                NullPointerException.class,
                () -> TopK.topK(null, 1)
        );
    }

    @Test
    void handlesEmptyArrayWithZeroK() {
        assertArrayEquals(
                new int[0],
                TopK.topK(new int[0], 0)
        );
    }

    @Test
    void handlesIntegerBoundaryValues() {
        int[] numbers = {
                Integer.MIN_VALUE,
                0,
                Integer.MAX_VALUE,
                -1
        };

        assertArrayEquals(
                new int[]{Integer.MAX_VALUE, 0},
                TopK.topK(numbers, 2)
        );
    }
}
