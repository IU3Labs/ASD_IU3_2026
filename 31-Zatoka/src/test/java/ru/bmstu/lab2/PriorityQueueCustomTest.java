package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Тесты минимальной двоичной кучи для задачи 1A. */
class PriorityQueueCustomTest {

    @Test
    void shouldBeEmptyInitially() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertTrue(queue.isEmpty());
        assertEquals(0, queue.size());
    }

    @Test
    void shouldReturnNullOnPeekWhenEmpty() {
        assertNull(new PriorityQueueCustom<Integer>().peek());
    }

    @Test
    void shouldThrowOnPollWhenEmpty() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, queue::poll);
    }

    @Test
    void shouldThrowOnNullInsertion() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> queue.add(null));
    }

    @Test
    void shouldAddAndPollSingleElement() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(7);
        assertEquals(7, queue.peek());
        assertEquals(7, queue.poll());
        assertTrue(queue.isEmpty());
    }

    @Test
    void shouldReturnSmallestElementFirst() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(8);
        queue.add(2);
        queue.add(6);
        assertEquals(2, queue.poll());
        assertEquals(6, queue.poll());
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldPreserveDuplicates() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(4);
        queue.add(4);
        queue.add(1);
        assertEquals(1, queue.poll());
        assertEquals(4, queue.poll());
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldSupportNegativeNumbersAndZero() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(0);
        queue.add(-10);
        queue.add(10);
        assertEquals(-10, queue.poll());
        assertEquals(0, queue.poll());
        assertEquals(10, queue.poll());
    }

    @Test
    void shouldNotChangeSizeWhenPeeking() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(2);
        queue.add(1);
        assertEquals(1, queue.peek());
        assertEquals(1, queue.peek());
        assertEquals(2, queue.size());
    }

    @Test
    void shouldGrowBeyondInitialCapacity() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        for (int number = 99; number >= 0; number--) {
            queue.add(number);
        }
        assertEquals(100, queue.size());
        for (int expected = 0; expected < 100; expected++) {
            assertEquals(expected, queue.poll());
        }
    }

    @Test
    void shouldPreserveOrderingAfterAlternatingOperations() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(8);
        queue.add(3);
        assertEquals(3, queue.poll());
        queue.add(1);
        queue.add(5);
        assertEquals(1, queue.poll());
        assertEquals(5, queue.poll());
        assertEquals(8, queue.poll());
    }

    @Test
    void shouldAllowReusingEmptiedQueue() {
        PriorityQueueCustom<Integer> queue = new PriorityQueueCustom<>();
        queue.add(9);
        assertEquals(9, queue.poll());
        queue.add(4);
        assertFalse(queue.isEmpty());
        assertEquals(4, queue.poll());
    }

    @Test
    void shouldSupportStringsInNaturalOrder() {
        PriorityQueueCustom<String> queue = new PriorityQueueCustom<>();
        queue.add("pear");
        queue.add("apple");
        queue.add("orange");
        assertEquals("apple", queue.poll());
        assertEquals("orange", queue.poll());
        assertEquals("pear", queue.poll());
    }

    @Test
    void shouldMatchStandardQueueOnRandomInput() {
        PriorityQueueCustom<Integer> actual = new PriorityQueueCustom<>();
        PriorityQueue<Integer> expected = new PriorityQueue<>();
        Random random = new Random(31);
        for (int index = 0; index < 10_000; index++) {
            int number = random.nextInt(2_001) - 1_000;
            actual.add(number);
            expected.add(number);
        }
        while (!expected.isEmpty()) {
            assertEquals(expected.poll(), actual.poll());
        }
        assertTrue(actual.isEmpty());
    }
}
