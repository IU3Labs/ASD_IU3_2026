package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.bmstu.lab02.QueueTestSupport.drain;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Задача 1C: удаление по равенству и восстановление кучи в обоих направлениях. */
class PriorityQueueRemoveTest {
    private static final int RANDOM_OPERATION_COUNT = 5_000;
    private static final int RANDOM_VALUE_RANGE = 101;
    private static final long RANDOM_SEED = 20261120L;

    @Test
    void shouldRemoveRootAndRestoreOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{4, 1, 3, 2, 5});

        assertTrue(queue.remove(1));
        assertEquals(4, queue.size());
        assertEquals(List.of(2, 3, 4, 5), drain(queue));
    }

    @Test
    void shouldSiftReplacementDownWhenRemovingMiddleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9});

        assertTrue(queue.remove(2));
        assertEquals(List.of(1, 3, 4, 5, 6, 7, 8, 9), drain(queue));
    }

    @Test
    void shouldSiftReplacementUpWhenRemovingMiddleElement() {
        // Это уже корректная куча: замена 10 последним элементом 7 нарушает порядок с родителем 8.
        Integer[] values = {1, 8, 2, 10, 11, 3, 4, 12, 13, 14, 15, 5, 6, 7};
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(values);

        assertTrue(queue.remove(10));
        assertEquals(List.of(1, 2, 3, 4, 5, 6, 7, 8, 11, 12, 13, 14, 15), drain(queue));
    }

    @Test
    void shouldRemoveLastLeaf() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2, 3, 4});

        assertTrue(queue.remove(4));
        assertEquals(3, queue.size());
        assertEquals(List.of(1, 2, 3), drain(queue));
    }

    @Test
    void shouldLeaveQueueUnchangedWhenValueIsAbsent() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{3, 1, 2});

        assertFalse(queue.remove(99));
        assertEquals(3, queue.size());
        assertEquals(1, queue.peek());
        assertEquals(List.of(1, 2, 3), drain(queue));
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(1));
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRemoveOnlyOneOccurrenceOfDuplicateValue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{4, 1, 4, 4, 2});

        assertTrue(queue.remove(4));
        assertEquals(4, queue.size());
        assertEquals(List.of(1, 2, 4, 4), drain(queue));
    }

    @Test
    void shouldIgnoreNullRemovalRequest() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{2, 1});

        assertFalse(queue.remove(null));
        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldUseEqualsRatherThanComparatorEquality() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("bb");
        queue.add("aa");
        queue.add("cccc");

        assertTrue(queue.remove(new String("aa")));
        assertEquals(List.of("bb", "cccc"), drain(queue));
    }

    @Test
    void shouldBecomeEmptyAfterRemovingOnlyElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{7});

        assertTrue(queue.remove(7));
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
        assertNull(queue.peek());
        assertFalse(queue.remove(7));
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldMatchStandardMinQueueForRandomMixedOperations() {
        assertRandomOperationsMatchReference(Comparator.naturalOrder());
    }

    @Test
    void shouldMatchStandardMaxQueueForRandomMixedOperations() {
        assertRandomOperationsMatchReference(Comparator.reverseOrder());
    }

    private void assertRandomOperationsMatchReference(Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(comparator);
        PriorityQueue<Integer> expected = new PriorityQueue<>(comparator);
        Random random = new Random(RANDOM_SEED);
        for (int operation = 0; operation < RANDOM_OPERATION_COUNT; operation++) {
            applyRandomOperation(expected, actual, random);
            assertEquals(expected.size(), actual.size(), "Размер после операции " + operation);
            assertEquals(expected.isEmpty(), actual.isEmpty(), "Пустота после операции " + operation);
            assertEquals(expected.peek(), actual.peek(), "Вершина после операции " + operation);
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private void applyRandomOperation(PriorityQueue<Integer> expected,
                                      PriorityQueueCustom<Integer> actual, Random random) {
        int value = random.nextInt(RANDOM_VALUE_RANGE) - RANDOM_VALUE_RANGE / 2;
        // Две из пяти веток добавляют элементы, чтобы очередь многократно расширяла массив.
        switch (random.nextInt(5)) {
            case 0, 1 -> {
                expected.add(value);
                actual.add(value);
            }
            case 2 -> {
                if (expected.isEmpty()) {
                    assertThrows(NoSuchElementException.class, actual::poll);
                } else {
                    assertEquals(expected.poll(), actual.poll());
                }
            }
            case 3 -> assertEquals(expected.remove(value), actual.remove(value));
            default -> assertEquals(expected.peek(), actual.peek());
        }
    }
}