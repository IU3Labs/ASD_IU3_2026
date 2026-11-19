package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Проверяет операции с ключами, коллизии и сохранность записей при перехешировании.
 */
class HashMapCustomTest {
    @Test
    void shouldStartEmpty() {
        var map = new HashMapCustom<String, Integer>();
        assertEquals(0, map.size());
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
        assertFalse(map.remove("a"));
    }

    @Test
    void shouldFindEqualKey() {
        var map = new HashMapCustom<String, Integer>();
        map.put(new String("a"), 3);
        assertEquals(3, map.get("a"));
        assertTrue(map.containsKey("a"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldUpdateValueWithoutIncreasingSize() {
        var map = new HashMapCustom<String, Integer>();
        map.put("a", 1);
        map.put("a", 2);
        assertEquals(2, map.get("a"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        var map = new HashMapCustom<String, Integer>();
        map.put("a", null);
        assertNull(map.get("a"));
        assertTrue(map.containsKey("a"));
        assertTrue(map.remove("a"));
        assertFalse(map.containsKey("a"));
        assertFalse(map.remove("a"));
    }

    @Test
    void shouldSupportNullKey() {
        var map = new HashMapCustom<Integer, String>();
        map.put(0, "zero");
        map.put(null, "null");
        assertEquals("null", map.get(null));
        assertTrue(map.containsKey(null));
        assertTrue(map.remove(null));
        assertEquals("zero", map.get(0));
        assertFalse(map.containsKey(null));
    }

    @Test
    void shouldHandleExtremeNegativeHash() {
        var map = new HashMapCustom<Integer, Integer>();
        map.put(Integer.MIN_VALUE, 2);
        map.put(0, 1);
        map.put(-1, 3);
        assertEquals(2, map.get(Integer.MIN_VALUE));
        assertEquals(1, map.get(0));
        assertEquals(3, map.get(-1));
    }

    @Test
    void shouldWrapProbeAtEndOfArray() {
        var map = new HashMapCustom<Integer, Integer>();
        int cap = map.capacity();
        map.put(cap - 1, 1);
        map.put(cap * 2 - 1, 2);
        map.put(0, 3);
        assertEquals(1, map.get(cap - 1));
        assertEquals(2, map.get(cap * 2 - 1));
        assertEquals(3, map.get(0));
    }

    @Test
    void shouldFindCollidingKeyAfterRemoval() {
        var map = new HashMapCustom<Integer, Integer>();
        int cap = map.capacity();
        map.put(0, 1);
        map.put(cap, 2);
        assertTrue(map.remove(0));
        assertEquals(2, map.get(cap));
        assertEquals(1, map.size());
    }

    @Test
    void shouldUpdateExistingKeyBeyondDeletedSlot() {
        var map = new HashMapCustom<Integer, Integer>();
        int cap = map.capacity();
        map.put(0, 1);
        map.put(cap, 2);
        map.remove(0);
        map.put(cap, 4);
        assertEquals(1, map.size());
        assertEquals(4, map.get(cap));
        assertTrue(map.remove(cap));
        assertFalse(map.containsKey(cap));
    }

    @Test
    void shouldReuseDeletedSlot() {
        var map = new HashMapCustom<Integer, Integer>();
        int cap = map.capacity();
        map.put(0, 1);
        map.put(cap, 2);
        map.remove(0);
        map.put(-cap, 3);
        assertEquals(2, map.size());
        assertEquals(2, map.get(cap));
        assertEquals(3, map.get(-cap));
    }

    @Test
    void shouldGrowOnlyForNewKeysAboveLoadLimit() {
        var map = new HashMapCustom<Integer, Integer>();
        int cap = map.capacity();
        int limit = cap * 3 / 4;
        for (int i = 0; i < limit; i++) {
            map.put(i, i);
        }
        map.put(0, -1);
        assertEquals(cap, map.capacity());
        assertEquals(limit, map.size());
        map.put(limit, limit);
        assertEquals(cap * 2, map.capacity());
        assertEquals(-1, map.get(0));
        for (int i = 1; i <= limit; i++) {
            assertEquals(i, map.get(i));
        }
    }

    @Test
    void shouldKeepCapacityWhenClearingDeletedSlots() {
        var map = new HashMapCustom<Integer, Integer>();
        int cap = map.capacity();
        int limit = cap * 3 / 4;
        for (int i = 0; i < limit; i++) {
            map.put(i, i);
        }
        for (int i = 0; i < limit - 1; i++) {
            map.remove(i);
        }
        map.put(limit, 42);
        map.put(limit + 1, 43);
        map.put(limit + 2, 44);
        assertEquals(cap, map.capacity());
        assertEquals(4, map.size());
        assertEquals(limit - 1, map.get(limit - 1));
        assertEquals(42, map.get(limit));
        assertEquals(43, map.get(limit + 1));
        assertEquals(44, map.get(limit + 2));
        assertFalse(map.containsKey(0));
    }

    @Test
    void shouldMatchStandardMapUnderMixedOperations() {
        var map = new HashMapCustom<Integer, Integer>();
        var expected = new HashMap<Integer, Integer>();
        var random = new Random(33);
        for (int i = 0; i < 5000; i++) {
            int key = random.nextInt(700) - 350;
            int value = random.nextInt();
            if (random.nextBoolean()) {
                map.put(key, value);
                expected.put(key, value);
            } else {
                assertEquals(expected.remove(key) != null, map.remove(key));
            }
            assertEquals(expected.size(), map.size());
            assertEquals(expected.get(key), map.get(key));
        }
        for (int key = -350; key < 350; key++) {
            assertEquals(expected.containsKey(key), map.containsKey(key));
            assertEquals(expected.get(key), map.get(key));
        }
    }
}
