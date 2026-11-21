package ru.bmstu.lab2;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashMapCustomTest {
    @Test
    void shouldStartEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertTrue(map.capacity() > 0);
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
    }

    @Test
    void shouldStoreDistinctKeysWithTheSameValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("first", 10);
        map.put("second", 10);

        assertEquals(2, map.size());
        assertEquals(10, map.get("first"));
        assertEquals(10, map.get("second"));
        assertTrue(map.containsKey("first"));
        assertTrue(map.containsKey("second"));
    }

    @Test
    void shouldUpdateEntryUsingEqualKey() {
        HashMapCustom<ProbeKey, String> map = new HashMapCustom<>();
        map.put(new ProbeKey(1, 5), "first");
        map.put(new ProbeKey(1, 5), "updated");

        assertEquals(1, map.size());
        assertEquals("updated", map.get(new ProbeKey(1, 5)));
    }

    @Test
    void shouldRecognizeAndRemoveEntryWithNullValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", null);

        assertTrue(map.containsKey("key"));
        assertNull(map.get("key"));
        assertTrue(map.remove("key"));
        assertFalse(map.containsKey("key"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);

        assertThrows(NullPointerException.class, () -> map.put(null, 2));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertEquals(1, map.size());
        assertEquals(1, map.get("key"));
    }

    @Test
    void shouldWrapAroundWhenLastSlotIsOccupied() {
        HashMapCustom<ProbeKey, Integer> map = new HashMapCustom<>();
        int hash = map.capacity() - 1;
        for (int id = 1; id <= 3; id++) {
            map.put(new ProbeKey(id, hash), id);
        }

        assertEquals(3, map.size());
        for (int id = 1; id <= 3; id++) {
            assertEquals(id, map.get(new ProbeKey(id, hash)));
        }
        assertFalse(map.containsKey(new ProbeKey(4, hash)));
    }

    @Test
    void shouldHandleNegativeAndExtremeHashCodes() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(-7, "negative");
        map.put(7, "positive");
        map.put(Integer.MIN_VALUE, "minimum");
        map.put(0, "zero");

        assertEquals(4, map.size());
        assertEquals("negative", map.get(-7));
        assertEquals("positive", map.get(7));
        assertEquals("minimum", map.get(Integer.MIN_VALUE));
        assertEquals("zero", map.get(0));
    }

    @Test
    void shouldFindCollidingKeysAfterDeletingEarlierEntry() {
        HashMapCustom<ProbeKey, Integer> map = new HashMapCustom<>();
        ProbeKey first = new ProbeKey(1, 5);
        ProbeKey second = new ProbeKey(2, 5);
        ProbeKey third = new ProbeKey(3, 5);
        map.put(first, 10);
        map.put(second, 20);
        map.put(third, 30);

        assertTrue(map.remove(first));
        assertFalse(map.remove(first));
        assertNull(map.get(first));
        assertEquals(20, map.get(second));
        assertEquals(30, map.get(third));
        assertEquals(2, map.size());
    }

    @Test
    void shouldUpdateExistingKeyAfterPassingDeletedSlot() {
        HashMapCustom<ProbeKey, Integer> map = new HashMapCustom<>();
        ProbeKey first = new ProbeKey(1, 5);
        ProbeKey second = new ProbeKey(2, 5);
        map.put(first, 10);
        map.put(second, 20);
        map.remove(first);

        map.put(second, 21);

        assertEquals(1, map.size());
        assertEquals(21, map.get(second));
        assertTrue(map.remove(second));
        assertFalse(map.containsKey(second));
    }

    @Test
    void shouldInsertIntoAProbeChainWithMultipleDeletedSlots() {
        HashMapCustom<ProbeKey, Integer> map = new HashMapCustom<>();
        ProbeKey first = new ProbeKey(1, 5);
        ProbeKey second = new ProbeKey(2, 5);
        ProbeKey third = new ProbeKey(3, 5);
        map.put(first, 10);
        map.put(second, 20);
        map.remove(first);
        map.remove(second);

        map.put(third, 30);

        assertNull(map.get(first));
        assertNull(map.get(second));
        assertEquals(30, map.get(third));
        assertEquals(1, map.size());
    }

    @Test
    void shouldKeepCapacityAtLoadLimitWhenUpdating() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int initialCapacity = map.capacity();
        int limit = initialCapacity * 3 / 4;
        for (int key = 0; key < limit; key++) {
            map.put(key, key);
        }

        map.put(0, -100);

        assertEquals(limit, map.size());
        assertEquals(initialCapacity, map.capacity());
        assertEquals(-100, map.get(0));
    }

    @Test
    void shouldDoubleCapacityAndRehashLiveEntriesWhenLoadExceedsLimit() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int initialCapacity = map.capacity();
        int limit = initialCapacity * 3 / 4;
        map.put(0, 0);
        for (int offset = 0; offset < limit - 1; offset++) {
            map.put(initialCapacity + offset, offset);
        }
        map.remove(0);
        map.put(initialCapacity + limit - 1, limit - 1);
        assertEquals(initialCapacity, map.capacity());

        map.put(initialCapacity + limit, limit);

        assertEquals(initialCapacity * 2, map.capacity());
        assertEquals(limit + 1, map.size());
        assertFalse(map.containsKey(0));
        for (int offset = 0; offset <= limit; offset++) {
            assertEquals(offset, map.get(initialCapacity + offset));
        }
    }

    @Test
    void shouldClearDeletedSlotsAfterRepeatedInsertionsAndRemovals() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int initialCapacity = map.capacity();
        map.put(0, 42);

        assertTimeoutPreemptively(Duration.ofSeconds(3), () -> {
            for (int key = 1; key <= initialCapacity * 4; key++) {
                map.put(key, key);
                assertTrue(map.remove(key));
                assertFalse(map.containsKey(key));
            }
        });

        assertEquals(initialCapacity, map.capacity());
        assertEquals(1, map.size());
        assertEquals(42, map.get(0));
    }

    @Test
    void shouldMatchStandardMapDuringMixedOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(42L);

        for (int step = 0; step < 5_000; step++) {
            int key = random.nextInt(-200, 201);
            int operation = random.nextInt(4);
            if (operation < 2) {
                Integer value = random.nextBoolean() ? step : null;
                expected.put(key, value);
                actual.put(key, value);
            } else if (operation == 2) {
                assertEquals(expected.containsKey(key), actual.remove(key));
                expected.remove(key);
            }

            assertEquals(expected.get(key), actual.get(key));
            assertEquals(expected.containsKey(key), actual.containsKey(key));
            assertEquals(expected.size(), actual.size());
        }

        for (int key = -200; key <= 200; key++) {
            assertEquals(expected.get(key), actual.get(key));
            assertEquals(expected.containsKey(key), actual.containsKey(key));
        }
    }

    private record ProbeKey(int id, int hash) {
        @Override
        public int hashCode() {
            return hash;
        }
    }
}
