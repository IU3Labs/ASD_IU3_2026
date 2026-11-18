package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Comparator;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Сравнение Top-K с полной сортировкой и граничные случаи. */
class TopKTest {
    @Test
    void shouldMatchSorting() {
        List<Integer> input = new Random(21).ints(5000, -50, 50).boxed().toList();
        for (int k : new int[] {1, 3, 250, 5000, 5001}) {
            List<Integer> expected = input.stream().sorted(Comparator.reverseOrder()).limit(k).toList();
            assertEquals(expected, TopK.topK(input, k));
        }
    }

    @Test
    void shouldKeepEqualLargestItems() {
        assertEquals(List.of("b", "b"), TopK.topK(List.of("b", "a", "b"), 2));
    }

    @Test
    void shouldHandleNonPositiveK() {
        assertEquals(List.of(), TopK.topK(List.of(1), 0));
        assertEquals(List.of(), TopK.topK(List.of(1), -1));
    }

    @Test
    void shouldHandleEmptyInput() {
        assertEquals(List.of(), TopK.topK(List.<Integer>of(), 3));
    }
}
