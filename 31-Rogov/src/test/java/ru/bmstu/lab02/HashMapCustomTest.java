package ru.bmstu.lab02;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/** Проверяет коллизии, удаление и перехеширование таблицы с линейным пробированием. */
class HashMapCustomTest {
    @Test
    void shouldReturnMissingResultsOnEmptyMap() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertTrue(map.capacity() > 0);
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
    }

    @Test
    void shouldInsertAndRetrievePair() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 5);

        assertTrue(map.containsKey("key"));
        assertEquals(5, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldUpdateExistingKeyWithoutChangingSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 5);
        map.put("key", 8);

        assertEquals(8, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldRemoveExistingKeyOnlyOnce() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 5);

        assertTrue(map.remove("key"));
        assertFalse(map.remove("key"));
        assertFalse(map.containsKey("key"));
        assertNull(map.get("key"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldWrapProbingAndFindKeyBeyondDeletedSlot() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        int capacity = map.capacity();
        int lastSlot = capacity - 1;
        map.put(lastSlot, "first");
        map.put(lastSlot + capacity, "second");
        map.put(lastSlot + 2 * capacity, "third");

        assertTrue(map.remove(lastSlot));
        assertEquals("second", map.get(lastSlot + capacity));
        assertEquals("third", map.get(lastSlot + 2 * capacity));
        assertFalse(map.containsKey(lastSlot + 3 * capacity));
    }

    @Test
    void shouldFindExistingKeyBeforeReusingDeletedSlot() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        int capacity = map.capacity();
        map.put(0, "first");
        map.put(capacity, "second");
        map.remove(0);
        map.put(capacity, "updated");

        assertEquals(1, map.size());
        assertEquals("updated", map.get(capacity));
        assertTrue(map.remove(capacity));
        assertFalse(map.containsKey(capacity));
    }

    @Test
    void shouldReuseDeletedSlotForCollidingKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        int capacity = map.capacity();
        map.put(1, "first");
        map.put(1 + capacity, "second");
        map.remove(1);
        map.put(1 + 2 * capacity, "third");

        assertEquals("second", map.get(1 + capacity));
        assertEquals("third", map.get(1 + 2 * capacity));
        assertFalse(map.containsKey(1));
        assertEquals(2, map.size());
    }

    @Test
    void shouldSupportNullKeyAndValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put(null, 7);
        map.put("nullable", null);

        assertEquals(7, map.get(null));
        assertTrue(map.containsKey("nullable"));
        assertNull(map.get("nullable"));
        assertTrue(map.remove("nullable"));
        assertTrue(map.remove(null));
        assertEquals(0, map.size());
    }

    @Test
    void shouldHandleMinimumIntHashAndOrdinaryNegativeHashes() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(Integer.MIN_VALUE, "min");
        map.put(0, "zero");
        map.put(-7, "negative");
        map.put(7, "positive");

        assertEquals("min", map.get(Integer.MIN_VALUE));
        assertEquals("zero", map.get(0));
        assertEquals("negative", map.get(-7));
        assertEquals("positive", map.get(7));
    }

    @Test
    void shouldDoubleCapacityOnlyWhenNewKeyExceedsLoadLimit() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int initialCapacity = map.capacity();
        int limit = 3 * initialCapacity / 4;
        for (int key = 0; key < limit; key++) {
            map.put(key, key);
        }
        map.put(0, -1);
        assertEquals(initialCapacity, map.capacity());

        map.put(limit, limit);

        assertEquals(2 * initialCapacity, map.capacity());
        assertEquals(limit + 1, map.size());
        assertEquals(-1, map.get(0));
        for (int key = 1; key <= limit; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldRehashWithoutRestoringDeletedPairs() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int capacity = map.capacity();
        map.put(0, 0);
        map.put(capacity, capacity);
        map.remove(0);

        for (int key = 1; key <= 3 * capacity / 4; key++) {
            map.put(key, key);
        }

        assertEquals(2 * capacity, map.capacity());
        assertEquals(capacity, map.get(capacity));
        assertFalse(map.containsKey(0));
    }

    @Test
    void shouldKeepCapacityAndLivePairsWhenCleaningManyDeletedSlots() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int capacity = map.capacity();
        for (int key = 0; key < 3 * capacity / 4; key++) {
            map.put(key, key);
        }
        for (int key = 0; key < capacity / 2; key++) {
            map.remove(key);
        }
        for (int key = 3 * capacity / 4; key < capacity; key++) {
            map.put(key, key);
        }

        assertEquals(capacity, map.capacity());
        assertEquals(capacity / 2, map.size());
        for (int key = 0; key < capacity / 2; key++) {
            assertFalse(map.containsKey(key));
        }
        for (int key = capacity / 2; key < capacity; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldMatchStandardMapDuringMixedOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(42);

        for (int step = 0; step < 20_000; step++) {
            int key = random.nextInt(-256, 257);
            switch (random.nextInt(4)) {
                case 0 -> {
                    int value = random.nextInt();
                    actual.put(key, value);
                    expected.put(key, value);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                case 2 -> assertEquals(expected.get(key), actual.get(key));
                default -> assertEquals(expected.containsKey(key), actual.containsKey(key));
            }
            assertEquals(expected.size(), actual.size());
        }
        expected.forEach((key, value) -> assertEquals(value, actual.get(key)));
    }
}
