package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

class PriorityQueueCustomTest {

    private static final long SEED = 12345L;

    // ---------- 1A: базовые операции ----------

    @Test
    void shouldPollElementsInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 3, 8, 1, 9, 2);

        assertEquals(List.of(1, 2, 3, 5, 8, 9), drain(queue));
    }

    @Test
    void shouldPeekMinimumWithoutRemovingIt() {
        PriorityQueueCustom<Integer> queue = queueOf(4, 2, 7);

        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldThrowWhenPollingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldThrowWhenPeekingIntoEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldTrackSizeAndEmptiness() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());

        queue.add(1);
        queue.add(2);
        assertFalse(queue.isEmpty());
        assertEquals(2, queue.size());

        queue.poll();
        queue.poll();
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        int count = 1000;
        for (int i = count; i > 0; i--) {
            queue.add(i);
        }

        assertEquals(count, queue.size());
        assertTrue(queue.isHeap());
        assertEquals(1, queue.poll());
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldKeepDuplicates() {
        PriorityQueueCustom<Integer> queue = queueOf(3, 1, 3, 1, 2);

        assertEquals(List.of(1, 1, 2, 3, 3), drain(queue));
    }

    @Test
    void shouldWorkWithSingleElement() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("only");

        assertEquals("only", queue.peek());
        assertEquals("only", queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldRejectNullElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    @Test
    void shouldRejectNonComparableElementWithoutComparator() {
        PriorityQueueCustom<Object> queue = new PriorityQueueCustom<>();

        assertThrows(ClassCastException.class, () -> queue.add(new Object()));
    }

    @Test
    void shouldMatchJavaPriorityQueueOnRandomOperations() {
        Random random = new Random(SEED);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();

        for (int step = 0; step < 20_000; step++) {
            if (expected.isEmpty() || random.nextInt(3) != 0) {
                int value = random.nextInt(1000) - 500;
                actual.add(value);
                expected.add(value);
            } else {
                assertEquals(expected.poll(), actual.poll());
            }
            assertEquals(expected.size(), actual.size());
        }
        assertTrue(actual.isHeap());
    }

    // ---------- 1B: компаратор, min-heap и max-heap ----------

    @Test
    void shouldActAsMinHeapWithNaturalComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.naturalOrder());
        Arrays.asList(4, 9, 1, 7).forEach(queue::add);

        assertEquals(List.of(1, 4, 7, 9), drain(queue));
    }

    @Test
    void shouldActAsMaxHeapWithReverseComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        Arrays.asList(4, 9, 1, 7).forEach(queue::add);

        assertEquals(List.of(9, 7, 4, 1), drain(queue));
    }

    @Test
    void shouldOrderStringsByCustomComparator() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        Arrays.asList("ccc", "a", "bb", "dddd").forEach(queue::add);

        assertEquals(List.of("a", "bb", "ccc", "dddd"), drain(queue));
    }

    @Test
    void shouldMatchSortedOrderForMaxHeapOnRandomData() {
        List<Integer> data = randomList(5000);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        data.forEach(queue::add);
        List<Integer> expected = new ArrayList<>(data);
        expected.sort(Comparator.reverseOrder());

        assertEquals(expected, drain(queue));
    }

    // ---------- 1C: удаление по значению ----------

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = queueOf(5, 1, 4, 2, 3);

        assertTrue(queue.remove(1));

        assertTrue(queue.isHeap());
        assertEquals(List.of(2, 3, 4, 5), drain(queue));
    }

    @Test
    void shouldRemoveElementFromMiddle() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3, 4, 5, 6, 7, 8, 9);

        assertTrue(queue.remove(4));

        assertTrue(queue.isHeap());
        assertEquals(List.of(1, 2, 3, 5, 6, 7, 8, 9), drain(queue));
    }

    @Test
    void shouldRemoveLastElementInArray() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3);

        assertTrue(queue.remove(3));

        assertEquals(List.of(1, 2), drain(queue));
    }

    @Test
    void shouldReturnFalseWhenRemovingMissingElement() {
        PriorityQueueCustom<Integer> queue = queueOf(1, 2, 3);

        assertFalse(queue.remove(42));
        assertEquals(3, queue.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();

        assertFalse(queue.remove(1));
        assertFalse(queue.remove(null));
    }

    @Test
    void shouldRemoveOnlyFirstOccurrence() {
        PriorityQueueCustom<Integer> queue = queueOf(2, 2, 2, 1);

        assertTrue(queue.remove(2));

        assertEquals(List.of(1, 2, 2), drain(queue));
    }

    @Test
    void shouldSiftUpWhenReplacementIsSmallerThanParent() {
        // Массив кучи: [0, 10, 1, 11, 12, 2, 3]. Удаляем 11 (левая ветвь): на его место встаёт последний элемент 3,
        // он меньше родителя 10, поэтому одного siftDown мало — нужен ещё и siftUp.
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[] {0, 10, 1, 11, 12, 2, 3}) {
            queue.add(value);
        }

        assertTrue(queue.remove(11));

        assertTrue(queue.isHeap());
        assertEquals(List.of(0, 1, 2, 3, 10, 12), drain(queue));
    }

    @Test
    void shouldKeepHeapValidAfterEveryRemoval() {
        List<Integer> data = randomList(300);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        data.forEach(queue::add);
        List<Integer> expected = new ArrayList<>(data);

        Collections.shuffle(data, new Random(SEED));
        for (Integer value : data) {
            assertTrue(queue.remove(value));
            expected.remove(value);
            assertTrue(queue.isHeap());
            assertEquals(expected.size(), queue.size());
        }
        assertTrue(queue.isEmpty());
    }

    // ---------- 1D: построение кучи из массива ----------

    @Test
    void shouldBuildHeapFromArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {9, 4, 7, 1, 8, 2});

        assertTrue(queue.isHeap());
        assertEquals(List.of(1, 2, 4, 7, 8, 9), drain(queue));
    }

    @Test
    void shouldNotModifySourceArrayWhenBuilding() {
        Integer[] source = {5, 4, 3, 2, 1};

        new PriorityQueueCustom<>(source);

        assertEquals(List.of(5, 4, 3, 2, 1), Arrays.asList(source));
    }

    @Test
    void shouldBuildFromEmptyArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);

        assertTrue(queue.isEmpty());
        queue.add(1);
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldBuildFromSingleElementArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[] {7});

        assertEquals(7, queue.peek());
        assertEquals(1, queue.size());
    }

    @Test
    void shouldBuildMaxHeapFromArrayWithComparator() {
        PriorityQueueCustom<Integer> queue =
                new PriorityQueueCustom<>(new Integer[] {3, 1, 4, 1, 5}, Comparator.reverseOrder());

        assertEquals(List.of(5, 4, 3, 1, 1), drain(queue));
    }

    @Test
    void shouldBuildLargeHeapAndKeepAcceptingElements() {
        Integer[] source = randomList(10_000).toArray(new Integer[0]);
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(source);
        queue.add(Integer.MIN_VALUE);

        assertTrue(queue.isHeap());
        assertEquals(Integer.MIN_VALUE, queue.peek());
        assertEquals(source.length + 1, queue.size());
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<Integer>((Integer[]) null));
    }

    @Test
    void shouldRejectNullElementInsideArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[] {1, null, 3}));
    }

    // ---------- вспомогательные методы ----------

    @SafeVarargs
    private static <T extends Comparable<? super T>> PriorityQueueCustom<T> queueOf(T... items) {
        PriorityQueueCustom<T> queue = new PriorityQueueCustom<>();
        for (T item : items) {
            queue.add(item);
        }
        return queue;
    }

    private static <T> List<T> drain(PriorityQueueCustom<T> queue) {
        List<T> result = new ArrayList<>();
        while (!queue.isEmpty()) {
            result.add(queue.poll());
        }
        return result;
    }

    private static List<Integer> randomList(int size) {
        Random random = new Random(SEED);
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(random.nextInt());
        }
        return result;
    }
}
