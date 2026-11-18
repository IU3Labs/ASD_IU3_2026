package ru.bmstu.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты хеш-таблицы.
 */
class HashMapCustomTest {

    @Test
    void shouldBeEmptyAfterCreation() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
    }

    @Test
    void shouldAddElement() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "one");

        assertEquals("one", map.get(1));
        assertEquals(1, map.size());
    }

    @Test
    void shouldUpdateExistingValue() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "one");
        map.put(1, "new");

        assertEquals("new", map.get(1));
        assertEquals(1, map.size());
    }

    @Test
    void shouldReturnNullForMissingKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        assertNull(map.get(10));
    }

    @Test
    void shouldCheckExistingKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "one");

        assertTrue(map.containsKey(1));
        assertFalse(map.containsKey(2));
    }

    @Test
    void shouldRemoveElement() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "one");

        assertTrue(map.remove(1));
        assertEquals(0, map.size());
        assertNull(map.get(1));
        assertFalse(map.containsKey(1));
    }

    @Test
    void shouldNotRemoveMissingKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "one");

        assertFalse(map.remove(2));
        assertEquals(1, map.size());
    }

    @Test
    void shouldHandleCollisions() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(2, "first");
        map.put(18, "second");
        map.put(34, "third");

        assertEquals("first", map.get(2));
        assertEquals("second", map.get(18));
        assertEquals("third", map.get(34));
        assertEquals(3, map.size());
    }

    @Test
    void shouldFindKeyAfterCollisionRemoval() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(2, "first");
        map.put(18, "second");
        map.put(34, "third");

        map.remove(18);

        assertEquals("third", map.get(34));
        assertFalse(map.containsKey(18));
    }

    @Test
    void shouldReuseDeletedCell() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(2, "first");
        map.put(18, "second");

        map.remove(2);
        map.put(34, "third");

        assertEquals("third", map.get(34));
        assertEquals("second", map.get(18));
        assertEquals(2, map.size());
    }

    @Test
    void shouldUpdateKeyAfterDeletedCell() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(2, "first");
        map.put(18, "second");

        map.remove(2);
        map.put(18, "new");

        assertEquals("new", map.get(18));
        assertEquals(1, map.size());
    }

    @Test
    void shouldResizeAutomatically() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (int i = 0; i < 12; i++) {
            map.put(i, i);
        }

        assertEquals(16, map.capacity());

        map.put(12, 12);

        assertEquals(32, map.capacity());
        assertEquals(13, map.size());

        for (int i = 0; i < 13; i++) {
            assertEquals(i, map.get(i));
        }
    }

    @Test
    void shouldHandleNegativeKeys() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(-5, "negative");
        map.put(Integer.MIN_VALUE, "minimum");

        assertEquals("negative", map.get(-5));
        assertEquals("minimum", map.get(Integer.MIN_VALUE));
    }

    @Test
    void shouldHandleManyElements() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (int i = 0; i < 1000; i++) {
            map.put(i, i * 2);
        }

        assertEquals(1000, map.size());

        for (int i = 0; i < 1000; i++) {
            assertEquals(i * 2, map.get(i));
        }
    }

    @Test
    void shouldHandleStringKeys() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("apple", 5);
        map.put("banana", 10);

        assertEquals(5, map.get("apple"));
        assertEquals(10, map.get("banana"));
    }
}