package ru.bmstu.lab2.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

class HashMapCustomTest {
    private static final double DEFAULT_LOAD_FACTOR = 0.75;

    @Test
    void shouldStartEmpty() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();

        assertEquals(0, table.size());
        assertEquals(16, table.capacity());
        assertNull(table.get("missing"));
        assertFalse(table.containsKey("missing"));
        assertFalse(table.remove("missing"));
    }

    @Test
    void shouldFindAnEqualKey() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();
        String key = "alpha";
        table.put(key, 11);

        assertEquals(11, table.get(new String(key)));
        assertTrue(table.containsKey(key));
        assertEquals(1, table.size());
    }

    @Test
    void shouldUpdateAtTheLoadLimitWithoutGrowing() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        int capacity = table.capacity();
        int limit = (int) (capacity * DEFAULT_LOAD_FACTOR);
        for (int key = 0; key < limit; key++) {
            table.put(key, key);
        }
        table.put(0, 100);

        assertEquals(100, table.get(0));
        assertEquals(limit, table.size());
        assertEquals(capacity, table.capacity());
    }

    @Test
    void shouldDoubleCapacityAndKeepEntries() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        int capacity = table.capacity();
        int count = (int) (capacity * DEFAULT_LOAD_FACTOR) + 1;

        for (int key = 0; key < count; key++) {
            table.put(key, key * 2);
        }

        assertEquals(2 * capacity, table.capacity());
        assertEquals(count, table.size());
        for (int key = 0; key < count; key++) {
            assertEquals(key * 2, table.get(key));
        }
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();
        table.put("present", null);

        assertNull(table.get("present"));
        assertTrue(table.containsKey("present"));
        assertFalse(table.containsKey("missing"));
        assertTrue(table.remove("present"));
        assertFalse(table.containsKey("present"));
    }

    @Test
    void shouldKeepLaterKeysReachableAfterRemovingAConflict() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        int first = 0;
        int second = 16;
        table.put(first, 1);
        table.put(second, 2);

        assertTrue(table.remove(first));
        assertFalse(table.remove(first));
        assertEquals(2, table.get(second));
        assertEquals(1, table.size());
    }

    @Test
    void shouldReuseATombstoneAndUpdateTheKeyBehindIt() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        int first = 0;
        int second = 16;
        int third = 32;
        table.put(first, 1);
        table.put(second, 2);
        table.remove(first);
        table.put(second, 20);
        table.put(third, 3);

        assertEquals(2, table.size());
        assertEquals(20, table.get(second));
        assertEquals(3, table.get(third));
    }

    @Test
    void shouldHandleTheMinimumHashCode() {
        HashMapCustom<Integer, String> table = new HashMapCustom<>();
        table.put(Integer.MIN_VALUE, "minimum");
        table.put(0, "zero");

        assertEquals("minimum", table.get(Integer.MIN_VALUE));
        assertTrue(table.remove(Integer.MIN_VALUE));
        assertEquals("zero", table.get(0));
    }

    @Test
    void shouldKeepLiveKeysDuringRepeatedInsertionsAndRemovals() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        int capacity = table.capacity();
        table.put(-1, 42);
        for (int key = 0; key < 2_048; key++) {
            table.put(key, key);
            assertTrue(table.remove(key));
        }

        assertEquals(1, table.size());
        assertEquals(42, table.get(-1));
        assertEquals(capacity, table.capacity());
    }

    @Test
    void shouldMatchJavaMapDuringMixedOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(2026);
        for (int i = 0; i < 4_000; i++) {
            int key = random.nextInt(-200, 201);
            if (random.nextBoolean()) {
                actual.put(key, i);
                expected.put(key, i);
            } else {
                assertEquals(expected.remove(key) != null, actual.remove(key));
            }
            int query = random.nextInt(-200, 201);
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.get(query), actual.get(query));
            assertEquals(expected.containsKey(query), actual.containsKey(query));
        }
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> table.put(null, 1));
        assertThrows(NullPointerException.class, () -> table.get(null));
        assertThrows(NullPointerException.class, () -> table.containsKey(null));
        assertThrows(NullPointerException.class, () -> table.remove(null));
    }
}
