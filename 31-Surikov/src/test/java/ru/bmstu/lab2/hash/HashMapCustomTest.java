package ru.bmstu.lab2.hash;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** проверка границ, основных операций и сравнение с библиотечным аналогом */
class HashMapCustomTest {
    private HashMapCustom<Integer, Integer> subject = new HashMapCustom<>();

    @Test
    void shouldStartEmpty() {
        assertEquals(0, subject.size());
        assertFalse(subject.containsKey(4));
    }

    @Test
    void shouldFindAbsent() {
        assertNull(subject.get(3));
    }

    @Test
    void shouldInsert() {
        subject.put(4, 9);
        assertEquals(9, subject.get(4));
    }

    @Test
    void shouldUpdate() {
        subject.put(4, 0);
        subject.put(4, 8);
        assertEquals(1, subject.size());
        assertEquals(8, subject.get(4));
    }

    @Test
    void shouldRemoveOnce() {
        subject.put(3, 8);
        assertTrue(subject.remove(3));
        assertFalse(subject.remove(3));
        assertEquals(0, subject.size());
    }

    @Test
    void shouldAcceptNullValue() {
        subject.put(0, null);
        assertTrue(subject.containsKey(0));
        assertNull(subject.get(0));
    }

    @Test
    void shouldRejectNullKey() {
        assertThrows(NullPointerException.class, () -> subject.put(null, 1));
    }

    @Test
    void shouldHandleMinimumHash() {
        subject.put(Integer.MIN_VALUE, 7);
        assertEquals(7, subject.get(Integer.MIN_VALUE));
    }

    @Test
    void shouldWrapAndReuseDeleted() {
        int step = subject.capacity();
        subject.put(step - 1, 1);
        subject.put(2 * step - 1, 2);
        subject.remove(step - 1);
        subject.put(2 * step - 1, 3);
        subject.put(3 * step - 1, 4);
        assertEquals(2, subject.size());
        assertEquals(3, subject.get(2 * step - 1));
        assertEquals(4, subject.get(3 * step - 1));
    }

    @Test
    void shouldDoubleCapacity() {
        int initial = subject.capacity();
        for (int i = 0; i < initial * 3 / 4; i++) {
            subject.put(i, i);
        }
        assertEquals(initial, subject.capacity());
        subject.put(initial, 0);
        assertEquals(2 * initial, subject.capacity());
        assertEquals(0, subject.get(0));
    }

    @Test
    void shouldCleanTombstones() {
        int initial = subject.capacity();
        for (int i = 0; i < 1000; i++) {
            subject.put(i, i);
            assertTrue(subject.remove(i));
        }
        assertEquals(initial, subject.capacity());
    }

    @Test
    void shouldMatchReference() {
        Random random = new Random(23);
        HashMap<Integer, Integer> expected = new HashMap<>();
        for (int i = 0; i < 5000; i++) {
            int key = random.nextInt(400) - 200;
            if (random.nextBoolean()) {
                expected.put(key, i);
                subject.put(key, i);
            } else {
                assertEquals(expected.remove(key) != null, subject.remove(key));
            }
            assertEquals(expected.get(key), subject.get(key));
            assertEquals(expected.size(), subject.size());
        }
    }
}
