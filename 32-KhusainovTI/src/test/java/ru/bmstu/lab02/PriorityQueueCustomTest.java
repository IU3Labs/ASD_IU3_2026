package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Граничные случаи, обе стороны восстановления кучи и сравнение со стандартной очередью. */
class PriorityQueueCustomTest {
    @Test
    void shouldStartEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldRejectPollFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<>().poll());
    }

    @Test
    void shouldRejectPeekFromEmptyQueue() {
        assertThrows(NoSuchElementException.class, () -> new PriorityQueueCustom<>().peek());
    }

    @Test
    void shouldAddAndRemoveSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(42);
        assertFalse(queue.isEmpty());
        assertEquals(42, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldPeekWithoutRemovingElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{5, 1, 3});
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(3, queue.size());
    }

    @Test
    void shouldPollInNaturalOrder() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int value : new int[]{5, -2, 0, 7, 1, -10}) {
            queue.add(value);
        }
        assertSequence(queue, -10, -2, 0, 1, 5, 7);
    }

    @Test
    void shouldKeepDuplicateElements() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{3, 1, 3, 1, 3});
        assertSequence(queue, 1, 1, 3, 3, 3);
    }

    @Test
    void shouldHandleIntegerExtremes() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(Integer.MAX_VALUE);
        queue.add(0);
        queue.add(Integer.MIN_VALUE);
        assertSequence(queue, Integer.MIN_VALUE, 0, Integer.MAX_VALUE);
    }

    @Test
    void shouldGrowArrayForOneHundredThousandElements() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int i = 100_000; i > 0; i--) {
            queue.add(i);
        }
        for (int i = 1; i <= 100_000; i++) {
            assertEquals(i, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldSupportExplicitMinComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.naturalOrder());
        queue.add(3);
        queue.add(1);
        queue.add(2);
        assertSequence(queue, 1, 2, 3);
    }

    @Test
    void shouldSupportMaxComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(Comparator.reverseOrder());
        queue.add(3);
        queue.add(1);
        queue.add(2);
        assertSequence(queue, 3, 2, 1);
    }

    @Test
    void shouldUseNaturalOrderForNullComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>((Comparator<Integer>) null);
        queue.add(9);
        queue.add(2);
        assertSequence(queue, 2, 9);
    }

    @Test
    void shouldOrderNonComparableObjectsWithComparator() {
        record Job(String name, int priority) { }
        PriorityQueueCustom<Job> queue = new PriorityQueueCustom<>(Comparator.comparingInt(Job::priority));
        Job later = new Job("later", 10);
        Job first = new Job("first", 1);
        queue.add(later);
        queue.add(first);
        assertEquals(first, queue.poll());
        assertEquals(later, queue.poll());
    }

    @Test
    void shouldRejectNullWithoutChangingQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);
        assertThrows(NullPointerException.class, () -> queue.add(null));
        assertSequence(queue, 4);
    }

    @Test
    void shouldRemoveRoot() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 3, 2, 7, 4});
        assertTrue(queue.remove(1));
        assertSequence(queue, 2, 3, 4, 7);
    }

    @Test
    void shouldSiftDownAfterRemovingMiddleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        assertTrue(queue.remove(2));
        assertSequence(queue, 1, 3, 4, 5, 6, 7);
    }

    @Test
    void shouldSiftUpAfterRemovingMiddleElement() {
        // Последний элемент 4 меньше родителя 10 в позиции удаляемого 11.
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 10, 2, 11, 12, 3, 4});
        assertTrue(queue.remove(11));
        assertSequence(queue, 1, 2, 3, 4, 10, 12);
    }

    @Test
    void shouldRemoveLastArrayElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2, 3});
        assertTrue(queue.remove(3));
        assertSequence(queue, 1, 2);
    }

    @Test
    void shouldRemoveOnlyElementAndAllowReuse() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{7});
        assertTrue(queue.remove(7));
        assertTrue(queue.isEmpty());
        queue.add(2);
        assertSequence(queue, 2);
    }

    @Test
    void shouldReturnFalseForAbsentElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1, 2, 3});
        assertFalse(queue.remove(4));
        assertSequence(queue, 1, 2, 3);
    }

    @Test
    void shouldReturnFalseForRemovalFromEmptyQueue() {
        assertFalse(new PriorityQueueCustom<Integer>().remove(1));
    }

    @Test
    void shouldReturnFalseForNullRemoval() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{1});
        assertFalse(queue.remove(null));
        assertSequence(queue, 1);
    }

    @Test
    void shouldRemoveExactlyOneDuplicate() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[]{2, 2, 2});
        assertTrue(queue.remove(2));
        assertSequence(queue, 2, 2);
    }

    @Test
    void shouldRemoveByEqualityRatherThanPriority() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>(Comparator.comparingInt(String::length));
        queue.add("ab");
        queue.add("cd");
        assertFalse(queue.remove("ef"));
        assertTrue(queue.remove(new String("cd")));
        assertEquals("ab", queue.poll());
    }

    @Test
    void shouldHeapifyAndCopyInputArray() {
        Integer[] input = {9, 8, 7, 6, 5, 4, 3, 2, 1};
        Integer[] original = input.clone();
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input);
        assertTrue(Arrays.equals(original, input));
        input[0] = -100;
        assertSequence(queue, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }

    @Test
    void shouldHeapifyEmptyArrayAndAllowInsertion() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(new Integer[0]);
        assertTrue(queue.isEmpty());
        queue.add(5);
        assertSequence(queue, 5);
    }

    @Test
    void shouldRejectNullArrayAndNullArrayElements() {
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>((Integer[]) null));
        assertThrows(NullPointerException.class, () -> new PriorityQueueCustom<>(new Integer[]{1, null}));
    }

    @Test
    void shouldHeapifyWithMaxComparator() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(
                new Integer[]{1, 2, 3, 4, 5, 6}, Comparator.reverseOrder());
        assertTrue(queue.remove(4));
        assertSequence(queue, 6, 5, 3, 2, 1);
    }

    @Test
    void shouldMatchStandardQueueAcrossRandomOperations() {
        compareRandomOperations(Comparator.naturalOrder());
        compareRandomOperations(Comparator.reverseOrder());
    }

    @Test
    void shouldHeapifyInLinearNumberOfComparisons() {
        int length = 100_000;
        Integer[] input = new Integer[length];
        for (int i = 0; i < length; i++) {
            input[i] = length - i;
        }
        int[] comparisons = {0};
        Comparator<Integer> countingComparator = (left, right) -> {
            comparisons[0]++;
            return Integer.compare(left, right);
        };
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>(input, countingComparator);
        assertTrue(comparisons[0] < 2 * length, "Bottom-up heapify uses fewer than 2n comparisons");
        for (int i = 1; i <= length; i++) {
            assertEquals(i, queue.poll());
        }
    }

    private void compareRandomOperations(Comparator<Integer> comparator) {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>(comparator);
        PriorityQueue<Integer> expected = new PriorityQueue<>(comparator);
        Random random = new Random(20261119);
        for (int step = 0; step < 20_000; step++) {
            int value = random.nextInt(500);
            switch (random.nextInt(4)) {
                case 0, 1 -> {
                    actual.add(value);
                    expected.add(value);
                }
                case 2 -> assertEquals(expected.remove(value), actual.remove(value));
                default -> {
                    if (!expected.isEmpty()) {
                        assertEquals(expected.poll(), actual.poll());
                    }
                }
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.isEmpty(), actual.isEmpty());
            if (!expected.isEmpty()) {
                assertEquals(expected.peek(), actual.peek());
            }
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }

    private void assertSequence(PriorityQueueCustom<Integer> queue, int... expected) {
        assertEquals(expected.length, queue.size());
        for (int value : expected) {
            assertNotNull(queue.peek());
            assertEquals(value, queue.poll());
        }
        assertTrue(queue.isEmpty());
    }
}
