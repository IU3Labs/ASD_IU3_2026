
package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты алгоритма поиска k наибольших элементов.
 */
class TopKTest {

    @Test
    void findsThreeLargestElements() {
        List<Integer> input = List.of(8, 3, 10, 1, 5, 12, 7);

        assertEquals(List.of(12, 10, 8), TopK.topK(input, 3));
    }

    @Test
    void returnsEmptyListWhenKIsZero() {
        assertEquals(List.of(), TopK.topK(List.of(5, 3, 8), 0));
    }

    @Test
    void handlesEmptyInput() {
        assertEquals(List.of(), TopK.topK(List.<Integer>of(), 0));
    }

    @Test
    void findsSingleLargestElement() {
        assertEquals(List.of(9), TopK.topK(List.of(2, 9, 4), 1));
    }

    @Test
    void returnsAllElementsInDescendingOrder() {
        assertEquals(
                List.of(9, 5, 2, 1),
                TopK.topK(List.of(2, 9, 1, 5), 4)
        );
    }

    @Test
    void handlesDuplicateValues() {
        assertEquals(
                List.of(9, 9, 7),
                TopK.topK(List.of(9, 3, 9, 7, 2), 3)
        );
    }

    @Test
    void handlesNegativeNumbers() {
        assertEquals(
                List.of(-1, -3),
                TopK.topK(List.of(-10, -1, -5, -3), 2)
        );
    }

    @Test
    void handlesAlreadySortedInput() {
        assertEquals(
                List.of(10, 9, 8),
                TopK.topK(List.of(10, 9, 8, 7, 6), 3)
        );
    }

    @Test
    void handlesReverseSortedInput() {
        assertEquals(
                List.of(10, 9, 8),
                TopK.topK(List.of(6, 7, 8, 9, 10), 3)
        );
    }

    @Test
    void worksWithStrings() {
        assertEquals(
                List.of("pear", "orange"),
                TopK.topK(List.of("apple", "pear", "orange"), 2)
        );
    }

    @Test
    void rejectsNegativeK() {
        assertThrows(
                IllegalArgumentException.class,
                () -> TopK.topK(List.of(1, 2, 3), -1)
        );
    }

    @Test
    void rejectsKGreaterThanInputSize() {
        assertThrows(
                IllegalArgumentException.class,
                () -> TopK.topK(List.of(1, 2, 3), 4)
        );
    }

    @Test
    void doesNotModifyOriginalList() {
        List<Integer> input = new ArrayList<>(List.of(5, 1, 9, 3));

        TopK.topK(input, 2);

        assertEquals(List.of(5, 1, 9, 3), input);
    }

    @Test
    void matchesSortingOnRandomData() {
        Random random = new Random(42);
        List<Integer> input = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            input.add(random.nextInt(10000));
        }

        List<Integer> expected = new ArrayList<>(input);
        expected.sort(Collections.reverseOrder());

        int k = 50;
        expected = new ArrayList<>(expected.subList(0, k));

        assertEquals(expected, TopK.topK(input, k));
    }

    @Test
    void handlesEqualElements() {
        assertEquals(
                List.of(5, 5, 5),
                TopK.topK(List.of(5, 5, 5, 5), 3)
        );
    }
}
