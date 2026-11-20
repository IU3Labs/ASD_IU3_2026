package ru.bmstu.lab2.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Проверки хеш-таблицы: HashMapCustomTest. */
class HashMapCustomTest {

    @Test
    void shouldStartEmptyWithPositiveCapacity() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertTrue(map.capacity() > 0);
    }

    @Test
    void shouldStoreAndRetrieveValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("apple", 3);
        assertEquals(3, map.get("apple"));
        assertTrue(map.containsKey("apple"));
    }

    @Test
    void shouldReturnNullForAbsentKey() {
        assertNull(new HashMapCustom<String, Integer>().get("missing"));
    }

    @Test
    void shouldReturnFalseForAbsentKey() {
        assertFalse(new HashMapCustom<String, Integer>().containsKey("missing"));
    }

    @Test
    void shouldUpdateWithoutIncreasingSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("x", 1);
        map.put(new String("x"), 2);
        assertEquals(1, map.size());
        assertEquals(2, map.get("x"));
    }

    @Test
    void shouldDistinguishNullValueFromAbsentKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("x", null);
        assertNull(map.get("x"));
        assertTrue(map.containsKey("x"));
        assertTrue(map.remove("x"));
        assertFalse(map.containsKey("x"));
    }

    @Test
    void shouldRemoveExistingKeyOnce() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("x", 4);
        assertTrue(map.remove("x"));
        assertFalse(map.remove("x"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingFromEmptyMap() {
        assertFalse(new HashMapCustom<String, Integer>().remove("x"));
    }

    @Test
    void shouldDoubleCapacityOnlyWhenLoadExceedsThreshold() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int capacity = map.capacity();
        int threshold = capacity * 3 / 4;
        for (int key = 0; key < threshold; key++) {
            map.put(key, key);
        }
        assertEquals(capacity, map.capacity());
        map.put(0, -1);
        assertEquals(capacity, map.capacity());
        map.put(threshold, threshold);
        assertEquals(capacity * 2, map.capacity());
        assertEquals(-1, map.get(0));
    }

    @Test
    void shouldRejectNullKeysForEveryOperation() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }
}
