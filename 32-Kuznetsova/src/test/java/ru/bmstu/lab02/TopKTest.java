package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

class TopKTest {

    private static final long SEED = 7L;

    @Test
    void shouldReturnKLargestInDescendingOrder() {
        List<Integer> result = TopK.topK(List.of(5, 1, 9, 3, 7, 8), 3);

        assertEquals(List.of(9, 8, 7), result);
    }

    @Test
    void shouldReturnEmptyListForZeroK() {
        assertTrue(TopK.topK(List.of(1, 2, 3), 0).isEmpty());
    }

    @Test
    void shouldReturnEverythingSortedWhenKIsNotLessThanSize() {
        assertEquals(List.of(3, 2, 1), TopK.topK(List.of(2, 3, 1), 3));
        assertEquals(List.of(3, 2, 1), TopK.topK(List.of(2, 3, 1), 10));
    }

    @Test
    void shouldReturnEmptyListForEmptyInput() {
        assertTrue(TopK.topK(new ArrayList<Integer>(), 5).isEmpty());
    }

    @Test
    void shouldKeepDuplicates() {
        assertEquals(List.of(5, 5, 4), TopK.topK(List.of(5, 4, 5, 1), 3));
    }

    @Test
    void shouldRejectNegativeK() {
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(List.of(1), -1));
    }

    @Test
    void shouldNotModifyInput() {
        List<Integer> input = new ArrayList<>(List.of(3, 1, 2));

        TopK.topK(input, 2);

        assertEquals(List.of(3, 1, 2), input);
    }

    @Test
    void shouldSupportCustomComparatorForSmallestElements() {
        List<Integer> result = TopK.topK(List.of(5, 1, 9, 3, 7), 2, Comparator.reverseOrder());

        assertEquals(List.of(1, 3), result);
    }

    @Test
    void shouldMatchFullSortOnRandomData() {
        Random random = new Random(SEED);
        List<Integer> input = new ArrayList<>();
        for (int i = 0; i < 50_000; i++) {
            input.add(random.nextInt(10_000));
        }
        int k = 100;
        List<Integer> sorted = new ArrayList<>(input);
        sorted.sort(Collections.reverseOrder());

        assertEquals(sorted.subList(0, k), TopK.topK(input, k));
    }
}
