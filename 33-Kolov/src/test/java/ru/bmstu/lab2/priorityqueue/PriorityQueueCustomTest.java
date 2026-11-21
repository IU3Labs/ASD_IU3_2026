package ru.bmstu.lab2.priorityqueue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Проверки задач 1A–1D: контракт очереди, граничные случаи и сравнение с эталоном JDK. */
class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertEquals(0, queue.size());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldAddAndPollSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        assertEquals(1, queue.size());
        assertFalse(queue.isEmpty());
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekWithoutRemovingElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{5, 2, 8});
        assertEquals(2, queue.peek());
        assertEquals(2, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldThrowWhenPollingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldThrowWhenPeekingEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::peek);
    }

    @Test
    void shouldExtractMixedValuesInAscendingOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{8, -3, 12, 0, 7}) {
            queue.add(value);
        }
        assertArrayEquals(new Integer[]{-3, 0, 7, 8, 12}, drain(queue));
    }

    @Test
    void shouldKeepDuplicateValues() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{2, 1, 2, 1});
        assertArrayEquals(new Integer[]{1, 1, 2, 2}, drain(queue));
    }

    @Test
    void shouldHandleIntegerLimits() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(
                new Integer[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE});
        assertArrayEquals(new Integer[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}, drain(queue));
    }

    @Test
    void shouldGrowArrayAcrossSeveralExpansions() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value = 1000; value >= 0; value--) {
            queue.add(value);
        }
        assertEquals(1001, queue.size());
        for (int expected = 0; expected <= 1000; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldAllowReuseAfterDraining() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(1);
        queue.poll();
        queue.add(2);
        assertEquals(2, queue.poll());
    }

    @Test
    void shouldRejectNullWithoutChangingSize() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertEquals(0, queue.size());
    }

    @Test
    void shouldUseExplicitMinComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.naturalOrder());
        queue.add(5);
        queue.add(1);
        queue.add(3);
        assertArrayEquals(new Integer[]{1, 3, 5}, drain(queue));
    }

    @Test
    void shouldUseMaxComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        queue.add(5);
        queue.add(1);
        queue.add(3);
        assertArrayEquals(new Integer[]{5, 3, 1}, drain(queue));
    }

    @Test
    void shouldUseNaturalOrderWhenComparatorIsNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>((Comparator<Integer>) null);
        queue.add(5);
        queue.add(1);
        assertEquals(1, queue.poll());
    }

    @Test
    void shouldCompareObjectsWithoutComparableUsingComparator() {
        PriorityQueueCustom<Box> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Box::priority));
        queue.add(new Box(8));
        queue.add(new Box(2));
        assertEquals(new Box(2), queue.poll());
    }

    @Test
    void shouldRejectNonComparableWithoutComparator() {
        PriorityQueueCustom<Box> queue = new PriorityQueueCustom<>();
        assertThrows(ClassCastException.class, () -> queue.add(new Box(1)));
    }

    @Test
    void shouldOrderStringsNaturally() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(new String[]{"pear", "apple", "banana"});
        assertEquals("apple", queue.poll());
        assertEquals("banana", queue.poll());
        assertEquals("pear", queue.poll());
    }

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{3, 1, 2});
        assertTrue(queue.remove(1));
        assertArrayEquals(new Integer[]{2, 3}, drain(queue));
    }

    @Test
    void shouldRestoreHeapDownwardAfterMiddleRemoval() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(
                new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9});
        assertTrue(queue.remove(1));
        assertArrayEquals(new Integer[]{0, 2, 3, 4, 5, 6, 7, 8, 9}, drain(queue));
    }

    @Test
    void shouldRestoreHeapUpwardAfterMiddleRemoval() {
        // Замена 11 последним элементом 3 требует подъёма над родителем 10.
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{0, 10, 1, 11, 12, 2, 3});
        assertTrue(queue.remove(11));
        assertArrayEquals(new Integer[]{0, 1, 2, 3, 10, 12}, drain(queue));
    }

    @Test
    void shouldRemoveLastElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2, 3});
        assertTrue(queue.remove(3));
        assertArrayEquals(new Integer[]{1, 2}, drain(queue));
    }

    @Test
    void shouldRemoveOnlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{2, 2, 2});
        assertTrue(queue.remove(2));
        assertEquals(2, queue.size());
        assertArrayEquals(new Integer[]{2, 2}, drain(queue));
    }

    @Test
    void shouldReturnFalseForMissingValue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2});
        assertFalse(queue.remove(9));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertFalse(queue.remove(1));
    }

    @Test
    void shouldReturnFalseWhenRemovingNull() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2});
        assertFalse(queue.remove(null));
        assertEquals(2, queue.size());
    }

    @Test
    void shouldRemoveByEqualsRatherThanComparatorEquality() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("aa");
        queue.add("bb");
        assertFalse(queue.remove("cc"));
        assertTrue(queue.remove(new String(new char[]{'b', 'b'})));
        assertEquals("aa", queue.poll());
    }

    @Test
    void shouldRemoveFromMaxHeap() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(
                new Integer[]{1, 4, 2, 3}, Comparator.reverseOrder());
        assertTrue(queue.remove(4));
        assertTrue(queue.remove(2));
        assertArrayEquals(new Integer[]{3, 1}, drain(queue));
    }

    @Test
    void shouldBuildHeapFromUnsortedArray() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{9, 1, 7, 2, 5});
        assertArrayEquals(new Integer[]{1, 2, 5, 7, 9}, drain(queue));
    }

    @Test
    void shouldCopySourceArray() {
        Integer[] input = {3, 1, 2};
        Integer[] original = input.clone();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
        assertArrayEquals(original, input);
        input[0] = -100;
        assertArrayEquals(new Integer[]{1, 2, 3}, drain(queue));
    }

    @Test
    void shouldBuildFromEmptyArrayAndThenGrow() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(4);
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldRejectNullArray() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
    }

    @Test
    void shouldRejectNullArrayElement() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null}));
    }

    @Test
    void shouldRejectNonComparableArrayWithoutComparator() {
        assertThrows(ClassCastException.class, () -> new PriorityQueueCustom<>(new Box[]{new Box(1)}));
    }

    @Test
    void shouldBuildHeapWithLinearComparisonCount() {
        Integer[] input = new Integer[10000];
        Arrays.setAll(input, index -> input.length - index);
        AtomicInteger comparisons = new AtomicInteger();
        Comparator<Integer> comparator = (first, second) -> {
            comparisons.incrementAndGet();
            return Integer.compare(first, second);
        };
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input, comparator);
        // Проверяем число сравнений, а не нестабильное время выполнения теста.
        assertTrue(comparisons.get() <= 3 * input.length);
        assertEquals(1, queue.peek());
    }

    @Test
    void shouldMatchJdkDuringMixedOperations() {
        Random random = new Random(20261121L);
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        for (int operation = 0; operation < 10000; operation++) {
            int value = random.nextInt(101) - 50;
            int action = random.nextInt(3);
            if (action == 0 || expected.isEmpty()) {
                expected.add(value);
                actual.add(value);
            } else if (action == 1) {
                assertEquals(expected.poll(), actual.poll());
            } else {
                assertEquals(expected.remove(value), actual.remove(value));
            }
            assertEquals(expected.size(), actual.size());
            if (!expected.isEmpty()) {
                assertEquals(expected.peek(), actual.peek());
            }
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private Integer[] drain(PriorityQueueCustom<Integer> queue) {
        Integer[] result = new Integer[queue.size()];
        for (int index = 0; index < result.length; index++) {
            result[index] = queue.poll();
        }
        return result;
    }

    private record Box(int priority) {
    }
}
