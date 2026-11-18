package ru.bmstu.lab02.task_1c;

import org.junit.jupiter.api.Test;
import java.util.Comparator;
import static org.junit.jupiter.api.Assertions.*;

class PriorityQueueCustomTest {

    @Test
    void testRemoveRoot() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        assertTrue(pq.remove(1));
        assertEquals(2, pq.peek());
        assertEquals(2, pq.size());
    }

    @Test
    void testRemoveMiddle() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        pq.add(4);
        assertTrue(pq.remove(3));
        assertEquals(1, pq.peek());
        assertEquals(3, pq.size());
    }

    @Test
    void testRemoveNonExistent() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(1);
        pq.add(2);
        assertFalse(pq.remove(99));
        assertEquals(2, pq.size());
    }

    @Test
    void testRemoveFromEmpty() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertFalse(pq.remove(1));
    }

    @Test
    void testRemoveDuplicatesOnlyFirst() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        pq.add(2);
        pq.add(2);
        pq.add(2);
        assertTrue(pq.remove(2));
        assertEquals(2, pq.size()); //осталось еще два
        assertEquals(2, pq.poll());
    }

    @Test
    void testRemoveRestoresHeapProperty() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        for (int i = 10; i >= 1; i--) pq.add(i);
        assertTrue(pq.remove(5));
        //после удаления куча должна извлекать элементы по порядку
        for (int i = 1; i <= 10; i++) {
            if (i == 5) continue;
            assertEquals(i, pq.poll());
        }
        assertTrue(pq.isEmpty());
    }

    @Test
    void testRemoveMaxHeap() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>(Comparator.reverseOrder());
        pq.add(5);
        pq.add(10);
        pq.add(7);
        assertTrue(pq.remove(10));
        assertEquals(7, pq.peek());
    }

    @Test
    void testRemoveNullThrows() {
        PriorityQueueCustom<Integer> pq = new PriorityQueueCustom<>();
        assertThrows(NullPointerException.class, () -> pq.remove(null));
    }
}