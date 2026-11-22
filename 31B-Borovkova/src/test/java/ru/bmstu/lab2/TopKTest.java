package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 3A: k наибольших элементов; эталон — сортировка по убыванию.
 */
class TopKTest {

    /**
     * Эталонное решение: сортировка копии по убыванию и первые k элементов.
     */
    private static <T extends Comparable<? super T>> List<T> bySort(List<T> input, int k) {
        List<T> copy = new ArrayList<>(input);
        copy.sort(Collections.reverseOrder());
        return copy.subList(0, Math.min(k, copy.size()));
    }

    @Test
    void shouldReturnLargestInDescendingOrder() {
        assertEquals(List.of(9, 8, 7), TopK.topK(List.of(5, 3, 8, 1, 9, 2, 7), 3));
    }

    @Test
    void shouldReturnSingleMaximumWhenKIsOne() {
        assertEquals(List.of(9), TopK.topK(List.of(5, 3, 8, 1, 9, 2, 7), 1));
    }

    @Test
    void shouldReturnEmptyWhenKIsZero() {
        assertTrue(TopK.topK(List.of(1, 2, 3), 0).isEmpty());
    }

    @Test
    void shouldReturnEmptyForEmptyInput() {
        assertTrue(TopK.topK(List.<Integer>of(), 5).isEmpty());
    }

    @Test
    void shouldReturnAllSortedWhenKEqualsSize() {
        assertEquals(List.of(4, 3, 2, 1), TopK.topK(List.of(2, 4, 1, 3), 4));
    }

    @Test
    void shouldReturnAllWhenKExceedsSize() {
        assertEquals(List.of(4, 3, 2, 1), TopK.topK(List.of(2, 4, 1, 3), 100));
    }

    @Test
    void shouldKeepDuplicates() {
        assertEquals(List.of(5, 5, 4), TopK.topK(List.of(5, 1, 5, 4, 2), 3));
    }

    @Test
    void shouldHandleNegativeNumbers() {
        assertEquals(List.of(-1, -2), TopK.topK(List.of(-5, -1, -3, -2), 2));
    }

    @Test
    void shouldRejectNegativeK() {
        assertThrows(IllegalArgumentException.class, () -> TopK.topK(List.of(1, 2), -1));
    }

    @Test
    void shouldRejectNullInputAndNullElements() {
        assertThrows(NullPointerException.class, () -> TopK.topK((List<Integer>) null, 1));
        assertThrows(NullPointerException.class, () -> TopK.topK(Arrays.asList(1, null, 3), 2));
    }

    @Test
    void shouldWorkWithStringsAndLinkedList() {
        List<String> words = new LinkedList<>(List.of("kiwi", "apple", "pear", "banana"));
        assertEquals(List.of("pear", "kiwi"), TopK.topK(words, 2));
    }

    @Test
    void shouldUseCustomComparator() {
        // k «наибольших» по обратному порядку — это k наименьших
        assertEquals(List.of(1, 2), TopK.topK(List.of(5, 3, 1, 2, 4), 2, Comparator.reverseOrder()));
    }

    @Test
    void shouldMatchSortOnRandomData() {
        Random random = new Random(5);
        for (int k : new int[] {1, 10, 500, 5_000}) {
            List<Integer> input = new ArrayList<>();
            for (int i = 0; i < 5_000; i++) {
                input.add(random.nextInt(1_000));
            }
            assertEquals(bySort(input, k), TopK.topK(input, k), "k = " + k);
        }
    }

    @Test
    void shouldMatchSortOnMillionElements() {
        Random random = new Random(6);
        List<Integer> input = new ArrayList<>(1_000_000);
        for (int i = 0; i < 1_000_000; i++) {
            input.add(random.nextInt());
        }
        long start = System.nanoTime();
        List<Integer> top = TopK.topK(input, 100);
        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        assertEquals(bySort(input, 100), top);
        System.out.println("topK(n = 1 000 000, k = 100): " + elapsedMs + " мс");
    }
}
