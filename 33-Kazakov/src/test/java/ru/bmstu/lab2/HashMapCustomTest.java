package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет пробирование, удаление и расширение хеш-таблицы */
class HashMapCustomTest {

    @Test
    void shouldStartEmpty() {
        HashMapCustom<Integer, String> actual = new HashMapCustom<>();
        assertEquals(0, actual.size());
        assertTrue(actual.capacity() > 0);
        assertNull(actual.get(1));
        assertFalse(actual.containsKey(1));
        assertFalse(actual.remove(1));
    }

    @Test
    void shouldFindKeyByEquality() {
        HashMapCustom<Key, String> actual = new HashMapCustom<>();
        actual.put(new Key(1, 3), "one");
        assertEquals("one", actual.get(new Key(1, 3)));
        assertTrue(actual.containsKey(new Key(1, 3)));
        assertEquals(1, actual.size());
    }

    @Test
    void shouldUpdateValueWithoutIncreasingSize() {
        HashMapCustom<Integer, String> actual = new HashMapCustom<>();
        actual.put(1, "old");
        actual.put(1, "new");
        assertEquals("new", actual.get(1));
        assertEquals(1, actual.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        HashMapCustom<Integer, String> actual = new HashMapCustom<>();
        actual.put(1, null);
        assertNull(actual.get(1));
        assertTrue(actual.containsKey(1));
        assertFalse(actual.containsKey(2));
        assertTrue(actual.remove(1));
        assertFalse(actual.containsKey(1));
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<Integer, String> actual = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> actual.put(null, "value"));
        assertThrows(NullPointerException.class, () -> actual.get(null));
        assertThrows(NullPointerException.class, () -> actual.containsKey(null));
        assertThrows(NullPointerException.class, () -> actual.remove(null));
    }

    @Test
    void shouldHandleMinimumIntegerHash() {
        HashMapCustom<Key, String> actual = new HashMapCustom<>();
        actual.put(new Key(1, Integer.MIN_VALUE), "min");
        actual.put(new Key(2, -13), "negative");
        assertEquals("min", actual.get(new Key(1, Integer.MIN_VALUE)));
        assertEquals("negative", actual.get(new Key(2, -13)));
    }

    @Test
    void shouldProbeAcrossEndOfArray() {
        HashMapCustom<Key, Integer> actual = new HashMapCustom<>();
        int hash = actual.capacity() - 1;
        actual.put(new Key(1, hash), 10);
        actual.put(new Key(2, hash), 20);
        actual.put(new Key(3, hash), 30);
        assertEquals(10, actual.get(new Key(1, hash)));
        assertEquals(20, actual.get(new Key(2, hash)));
        assertEquals(30, actual.get(new Key(3, hash)));
        assertNull(actual.get(new Key(4, hash)));
    }

    @Test
    void shouldFindCollidingKeyAfterRemovingEarlierKey() {
        HashMapCustom<Key, Integer> actual = new HashMapCustom<>();
        Key first = new Key(1, 7);
        Key second = new Key(2, 7);
        actual.put(first, 10);
        actual.put(second, 20);
        assertTrue(actual.remove(first));
        assertFalse(actual.remove(first));
        assertNull(actual.get(first));
        assertEquals(20, actual.get(second));
        assertEquals(1, actual.size());
    }

    @Test
    void shouldUpdateKeyBeyondDeletedSlotWithoutAddingDuplicate() {
        HashMapCustom<Key, Integer> actual = new HashMapCustom<>();
        Key first = new Key(1, 7);
        Key second = new Key(2, 7);
        actual.put(first, 10);
        actual.put(second, 20);
        actual.remove(first);
        actual.put(second, 21);
        assertEquals(1, actual.size());
        assertEquals(21, actual.get(second));
        assertTrue(actual.remove(second));
        assertFalse(actual.containsKey(second));
    }

    @Test
    void shouldInsertCollidingKeyAfterDeletion() {
        HashMapCustom<Key, Integer> actual = new HashMapCustom<>();
        Key first = new Key(1, 7);
        Key second = new Key(2, 7);
        Key third = new Key(3, 7);
        actual.put(first, 10);
        actual.put(second, 20);
        actual.remove(first);
        actual.put(third, 30);
        assertEquals(2, actual.size());
        assertEquals(20, actual.get(second));
        assertEquals(30, actual.get(third));
    }

    @Test
    void shouldDoubleCapacityOnlyWhenLiveLoadExceedsThreshold() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        int initialCapacity = actual.capacity();
        int limit = 3 * initialCapacity / 4;
        for (int key = 0; key < limit; key++) {
            actual.put(key, key);
        }
        assertEquals(initialCapacity, actual.capacity());
        actual.put(0, -1);
        assertEquals(initialCapacity, actual.capacity());
        actual.put(limit, limit);
        assertEquals(initialCapacity * 2, actual.capacity());
        assertEquals(limit + 1, actual.size());
        assertEquals(-1, actual.get(0));
        for (int key = 1; key <= limit; key++) {
            assertEquals(key, actual.get(key));
        }
    }

    @Test
    void shouldKeepCapacityAndEntriesAfterAccumulatingDeletedSlots() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        int capacity = actual.capacity();
        int initialCount = 3 * capacity / 4;
        for (int key = 0; key < initialCount; key++) {
            actual.put(key, key);
        }
        for (int key = 0; key < capacity / 2; key++) {
            actual.remove(key);
        }
        for (int key = initialCount; key < capacity; key++) {
            actual.put(key, key);
        }
        assertEquals(capacity, actual.capacity());
        assertEquals(capacity / 2, actual.size());
        assertFalse(actual.containsKey(0));
        for (int key = capacity / 2; key < capacity; key++) {
            assertEquals(key, actual.get(key));
        }
    }

    @Test
    void shouldMatchStandardMapOnMixedOperations() {
        HashMapCustom<Key, Integer> actual = new HashMapCustom<>();
        HashMap<Key, Integer> expected = new HashMap<>();
        Random random = new Random(19);
        for (int iteration = 0; iteration < 8_000; iteration++) {
            int id = random.nextInt(128);
            Key key = new Key(id, id % 17);
            switch (random.nextInt(4)) {
                case 0 -> {
                    int value = random.nextInt();
                    expected.put(key, value);
                    actual.put(key, value);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                case 2 -> assertEquals(expected.get(key), actual.get(key));
                default -> assertEquals(expected.containsKey(key), actual.containsKey(key));
            }
            assertEquals(expected.size(), actual.size());
        }
        expected.forEach((key, value) -> assertEquals(value, actual.get(key)));
    }

    private record Key(int id, int hash) {

        /** Возвращает заданный хеш для проверки коллизий */
        @Override
        public int hashCode() {
            return hash;
        }
    }
}
