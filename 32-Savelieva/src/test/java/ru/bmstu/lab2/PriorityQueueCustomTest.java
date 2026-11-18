package ru.bmstu.lab2;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueCustomTest {

    @Test
    void newQueueIsEmpty() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        assertTrue(q.isEmpty());
        assertEquals(0, q.size());
    }

    @Test
    void addIncreasesSize() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        q.add(5);
        q.add(3);
        assertEquals(2, q.size());
        assertFalse(q.isEmpty());
    }

    @Test
    void peekReturnsMinWithoutRemoving() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        q.add(5);
        q.add(1);
        q.add(3);
        assertEquals(1, q.peek());
        assertEquals(3, q.size());
        assertEquals(1, q.peek());
    }

    @Test
    void pollReturnsMin() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        q.add(5);
        q.add(1);
        q.add(3);
        assertEquals(1, q.poll());
        assertEquals(3, q.poll());
        assertEquals(5, q.poll());
        assertTrue(q.isEmpty());
    }

    @Test
    void pollEmptyThrows() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, q::poll);
    }

    @Test
    void peekEmptyThrows() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        assertThrows(NoSuchElementException.class, q::peek);
    }

    @Test
    void addNullThrows() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> q.add(null));
    }

    @Test
    void singleElement() {
        PriorityQueueCustom<String> q = new PriorityQueueCustom<>();
        q.add("only");
        assertEquals("only", q.peek());
        assertEquals("only", q.poll());
        assertTrue(q.isEmpty());
    }

    @Test
    void autoGrow() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>(2);
        for (int i = 100; i > 0; i--) {
            q.add(i);
        }
        assertEquals(100, q.size());
        assertEquals(1, q.peek());
        for (int i = 1; i <= 100; i++) {
            assertEquals(i, q.poll());
        }
    }

    @Test
    void elementsComeOutNonDecreasing() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        Random rnd = new Random(42);
        List<Integer> added = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            int v = rnd.nextInt(10_000);
            added.add(v);
            q.add(v);
        }
        added.sort(null);
        for (int expected : added) {
            assertEquals(expected, q.poll());
        }
    }

    @Test
    void duplicatesPreserved() {
        PriorityQueueCustom<Integer> q = new PriorityQueueCustom<>();
        q.add(2); q.add(2); q.add(1); q.add(2);
        assertEquals(1, q.poll());
        assertEquals(2, q.poll());
        assertEquals(2, q.poll());
        assertEquals(2, q.poll());
    }

    @Test
    void customInitialCapacityInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueCustom<>(0));
        assertThrows(IllegalArgumentException.class, () -> new PriorityQueueCustom<>(-5));
    }
}