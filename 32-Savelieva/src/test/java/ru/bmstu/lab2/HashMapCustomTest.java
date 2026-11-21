package ru.bmstu.lab2;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HashMapCustomTest {

    @Test
    void emptyMap() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        assertEquals(0, m.size());
        assertNull(m.get("x"));
        assertFalse(m.containsKey("x"));
        assertFalse(m.remove("x"));
    }

    @Test
    void putAndGet() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        m.put("a", 1);
        m.put("b", 2);
        assertEquals(1, m.get("a"));
        assertEquals(2, m.get("b"));
        assertEquals(2, m.size());
    }

    @Test
    void putOverwritesExisting() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        m.put("a", 1);
        m.put("a", 99);
        assertEquals(99, m.get("a"));
        assertEquals(1, m.size());
    }

    @Test
    void getMissingReturnsNull() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        m.put("a", 1);
        assertNull(m.get("zzz"));
    }

    @Test
    void containsKey() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        m.put("k", 0);
        assertTrue(m.containsKey("k"));
        assertFalse(m.containsKey("nope"));
    }

    @Test
    void removeExisting() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        m.put("a", 1);
        assertTrue(m.remove("a"));
        assertFalse(m.containsKey("a"));
        assertEquals(0, m.size());
    }

    @Test
    void removeMissingReturnsFalse() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        m.put("a", 1);
        assertFalse(m.remove("b"));
        assertEquals(1, m.size());
    }

    @Test
    void resizeKeepsAllEntries() {
        HashMapCustom<Integer, Integer> m = new HashMapCustom<>();
        for (int i = 0; i < 1000; i++) m.put(i, i * 2);
        assertEquals(1000, m.size());
        assertTrue(m.capacity() >= 1000);
        for (int i = 0; i < 1000; i++) assertEquals(i * 2, m.get(i));
    }

    @Test
    void deletedSlotsDoNotBreakProbing() {
        HashMapCustom<Integer, Integer> m = new HashMapCustom<>();
        // искусственно создаём кластер
        for (int i = 0; i < 20; i++) m.put(i, i);
        // удаляем середину кластера
        for (int i = 5; i < 15; i++) assertTrue(m.remove(i));
        // остальные должны находиться
        for (int i = 0; i < 5; i++) assertEquals(i, m.get(i));
        for (int i = 15; i < 20; i++) assertEquals(i, m.get(i));
        // и удалённые — не находиться
        for (int i = 5; i < 15; i++) assertNull(m.get(i));
    }

    @Test
    void reuseOfDeletedSlots() {
        HashMapCustom<Integer, Integer> m = new HashMapCustom<>();
        for (int i = 0; i < 30; i++) m.put(i, i);
        for (int i = 0; i < 10; i++) m.remove(i);
        int capBefore = m.capacity();
        for (int i = 100; i < 120; i++) m.put(i, i);
        assertEquals(40, m.size());
        assertEquals(capBefore, m.capacity()); // без лишнего ресайза
        for (int i = 100; i < 120; i++) assertEquals(i, m.get(i));
    }

    @Test
    void nullKeyThrows() {
        HashMapCustom<String, Integer> m = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> m.put(null, 1));
        assertThrows(NullPointerException.class, () -> m.get(null));
        assertThrows(NullPointerException.class, () -> m.containsKey(null));
        assertThrows(NullPointerException.class, () -> m.remove(null));
    }

    @Test
    void randomStressAgainstReference() {
        HashMapCustom<Integer, Integer> m = new HashMapCustom<>();
        java.util.HashMap<Integer, Integer> ref = new java.util.HashMap<>();
        Random rnd = new Random(7);
        for (int step = 0; step < 10_000; step++) {
            int key = rnd.nextInt(500);
            int op = rnd.nextInt(3);
            if (op == 0) {
                int v = rnd.nextInt();
                m.put(key, v);
                ref.put(key, v);
            } else if (op == 1) {
                assertEquals(ref.remove(key) != null, m.remove(key));
            } else {
                assertEquals(ref.get(key), m.get(key));
            }
            assertEquals(ref.size(), m.size());
        }
    }
    @Test
void repeatedRemoveAndInsertDoesNotGrowUnnecessarily() {
    HashMapCustom<Integer, Integer> m =
            new HashMapCustom<>();

    for (int i = 0; i < 20; i++) {
        m.put(i, i);
    }

    int initialCapacity = m.capacity();

    for (int round = 0; round < 100; round++) {
        for (int i = 0; i < 20; i++) {
            assertTrue(m.remove(i));
        }

        for (int i = 0; i < 20; i++) {
            m.put(i, i + round);
        }
    }

    assertEquals(20, m.size());
    assertEquals(initialCapacity, m.capacity());

    for (int i = 0; i < 20; i++) {
        assertEquals(i + 99, m.get(i));
    }
}
}