
package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

/**
 * Тесты для хеш-таблицы с линейным пробированием.
 */
class HashMapCustomTest {

    @Test
    void newMapIsEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertTrue(map.capacity() > 0);
    }

    @Test
    void putAndGetWork() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("apple", 5);
        map.put("banana", 8);

        assertEquals(5, map.get("apple"));
        assertEquals(8, map.get("banana"));
        assertEquals(2, map.size());
    }

    @Test
    void getMissingKeyReturnsNull() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertNull(map.get("missing"));
    }

    @Test
    void putUpdatesExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("apple", 5);
        map.put("apple", 10);

        assertEquals(10, map.get("apple"));
        assertEquals(1, map.size());
    }

    @Test
    void containsKeyWorks() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("apple", 5);

        assertTrue(map.containsKey("apple"));
        assertFalse(map.containsKey("banana"));
    }

    @Test
    void removeExistingKeyWorks() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("apple", 5);

        assertTrue(map.remove("apple"));
        assertFalse(map.containsKey("apple"));
        assertNull(map.get("apple"));
        assertEquals(0, map.size());
    }

    @Test
    void removeMissingKeyReturnsFalse() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertFalse(map.remove("missing"));
        assertEquals(0, map.size());
    }

    @Test
    void collisionsAreHandled() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        // При начальной ёмкости 16 эти ключи
        // попадают в один начальный индекс.
        map.put(1, "one");
        map.put(17, "seventeen");
        map.put(33, "thirty-three");

        assertEquals("one", map.get(1));
        assertEquals("seventeen", map.get(17));
        assertEquals("thirty-three", map.get(33));
    }

    @Test
    void removingCollidingKeyPreservesSearchChain() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(1, "one");
        map.put(17, "seventeen");
        map.put(33, "thirty-three");

        assertTrue(map.remove(17));

        assertEquals("one", map.get(1));
        assertEquals("thirty-three", map.get(33));
        assertNull(map.get(17));
    }

    @Test
    void deletedSlotCanBeReused() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(1, "one");
        map.put(17, "seventeen");

        map.remove(1);
        map.put(33, "thirty-three");

        assertEquals("seventeen", map.get(17));
        assertEquals("thirty-three", map.get(33));
        assertEquals(2, map.size());
    }

    @Test
    void tableResizesAutomatically() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        int initialCapacity = map.capacity();

        for (int i = 0; i < 100; i++) {
            map.put(i, i * 10);
        }

        assertTrue(map.capacity() > initialCapacity);
        assertEquals(100, map.size());

        for (int i = 0; i < 100; i++) {
            assertEquals(i * 10, map.get(i));
        }
    }

    @Test
    void negativeHashCodesAreHandled() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(-1, "negative");
        map.put(Integer.MIN_VALUE, "minimum");

        assertEquals("negative", map.get(-1));
        assertEquals("minimum", map.get(Integer.MIN_VALUE));
    }

    @Test
    void nullValueIsSupported() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("empty", null);

        assertTrue(map.containsKey("empty"));
        assertNull(map.get("empty"));
        assertEquals(1, map.size());
    }

    @Test
    void nullKeyIsRejected() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }

    @Test
    void manyInsertionsAndRemovalsWork() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }

        for (int i = 0; i < 1000; i += 2) {
            assertTrue(map.remove(i));
        }

        assertEquals(500, map.size());

        for (int i = 0; i < 1000; i++) {
            if (i % 2 == 0) {
                assertFalse(map.containsKey(i));
            } else {
                assertEquals(i, map.get(i));
            }
        }
    }
}
