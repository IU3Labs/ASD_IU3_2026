package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет поиск, пробирование, удаление и рост таблицы. */
class HashMapCustomTest {
    @Test
    void shouldReportMissingKeysInEmptyTable() {
        var map = new HashMapCustom<String, Integer>();
        assertEquals(0, map.size());
        assertTrue(map.capacity() > 0);
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
    }

    @Test
    void shouldRetrieveInsertedValueByEqualKey() {
        var map = new HashMapCustom<String, Integer>();
        map.put("key", 18);
        String equalKey = new String(new char[] {'k', 'e', 'y'});
        assertTrue(map.containsKey(equalKey));
        assertEquals(18, map.get(equalKey));
    }

    @Test
    void shouldOverwriteWithoutIncreasingSize() {
        var map = new HashMapCustom<String, Integer>();
        map.put("x", 1);
        map.put("x", 2);
        assertEquals(2, map.get("x"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        var map = new HashMapCustom<String, Integer>();
        map.put("x", null);
        assertNull(map.get("x"));
        assertTrue(map.containsKey("x"));
        assertTrue(map.remove("x"));
        assertFalse(map.containsKey("x"));
    }

    @Test
    void shouldRejectNullKeys() {
        var map = new HashMapCustom<String, Integer>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }

    @Test
    void shouldReportSuccessfulRemovalOnlyOnce() {
        var map = new HashMapCustom<String, Integer>();
        map.put("x", 1);
        assertTrue(map.remove("x"));
        assertFalse(map.remove("x"));
        assertNull(map.get("x"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldFindKeysBeyondDeletedSlotAcrossArrayEnd() {
        var map = new HashMapCustom<Integer, String>();
        int first = map.capacity() - 1;
        int second = first + map.capacity();
        map.put(first, "first");
        map.put(second, "second");
        assertTrue(map.remove(first));
        assertEquals("second", map.get(second));
    }

    @Test
    void shouldUpdateExistingKeyBeyondDeletedSlotAndReuseSlot() {
        var map = new HashMapCustom<Integer, Integer>();
        int capacity = map.capacity();
        map.put(0, 1);
        map.put(capacity, 2);
        assertTrue(map.remove(0));
        map.put(capacity, 3);
        assertEquals(1, map.size());
        assertEquals(3, map.get(capacity));
        map.put(2 * capacity, 4);
        assertEquals(2, map.size());
        assertEquals(4, map.get(2 * capacity));
    }

    @Test
    void shouldHandleMinimumIntegerHashAndNegativeHash() {
        var map = new HashMapCustom<Integer, String>();
        map.put(Integer.MIN_VALUE, "minimum");
        map.put(-7, "negative");
        map.put(7, "positive");
        assertEquals("minimum", map.get(Integer.MIN_VALUE));
        assertEquals("negative", map.get(-7));
        assertEquals("positive", map.get(7));
    }

    @Test
    void shouldDoubleCapacityOnlyWhenInsertingBeyondLoadLimit() {
        var map = new HashMapCustom<Integer, Integer>();
        int capacity = map.capacity();
        int limit = capacity * 3 / 4;
        for (int key = 0; key < limit; key++) {
            map.put(key, -key);
        }
        assertEquals(capacity, map.capacity());
        map.put(0, 7);
        assertEquals(capacity, map.capacity());
        map.put(limit, -limit);
        assertEquals(2 * capacity, map.capacity());
        assertEquals(limit + 1, map.size());
        assertEquals(7, map.get(0));
        for (int key = 1; key <= limit; key++) {
            assertEquals(-key, map.get(key));
        }
    }

    @Test
    void shouldClearDeletedSlotsWithoutGrowingTheTable() {
        var map = new HashMapCustom<Integer, Integer>();
        int capacity = map.capacity();
        int limit = capacity * 7 / 8;
        map.put(0, 0);
        for (int key = 1; key <= limit; key++) {
            map.put(key, key);
            assertTrue(map.remove(key));
        }
        assertEquals(capacity, map.capacity());
        assertEquals(1, map.size());
        assertEquals(0, map.get(0));
        assertNull(map.get(limit));
    }

    @Test
    void shouldMatchStandardMapUnderMixedOperations() {
        var map = new HashMapCustom<Integer, Integer>();
        var expected = new HashMap<Integer, Integer>();
        Random random = new Random(42);
        for (int step = 0; step < 10_000; step++) {
            int key = random.nextInt(500) - 250;
            switch (random.nextInt(3)) {
                case 0 -> {
                    map.put(key, step);
                    expected.put(key, step);
                }
                case 1 -> assertEquals(expected.remove(key) != null, map.remove(key));
                default -> assertEquals(expected.get(key), map.get(key));
            }
            assertEquals(expected.containsKey(key), map.containsKey(key));
            assertEquals(expected.size(), map.size());
        }
        expected.forEach((key, value) -> assertEquals(value, map.get(key)));
    }
}
