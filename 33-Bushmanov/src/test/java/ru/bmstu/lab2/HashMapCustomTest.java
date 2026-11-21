package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

class HashMapCustomTest {
    @Test
    void shouldStartEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    void shouldInsertAndFindValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("apple", 3);
        assertEquals(3, map.get("apple"));
        assertTrue(map.containsKey("apple"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldUpdateWithoutChangingSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("apple", 3);
        map.put("apple", 8);
        assertEquals(8, map.get("apple"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("present", null);
        assertNull(map.get("present"));
        assertTrue(map.containsKey("present"));
        assertFalse(map.containsKey("missing"));
        assertTrue(map.remove("present"));
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertEquals(0, map.size());
    }

    @Test
    void shouldRemoveExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("apple", 3);
        assertTrue(map.remove("apple"));
        assertNull(map.get("apple"));
        assertFalse(map.containsKey("apple"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldReturnFalseForMissingRemoval() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        assertFalse(map.remove(1));
        map.put(2, 3);
        assertFalse(map.remove(1));
        assertEquals(1, map.size());
    }

    @Test
    void shouldRemoveKeyOnlyOnce() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        map.put(1, 3);
        assertTrue(map.remove(1));
        assertFalse(map.remove(1));
        assertEquals(0, map.size());
    }

    @Test
    void shouldKeepCapacityAtLoadLimit() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 12; key++) {
            map.put(key, key);
        }
        assertEquals(16, map.capacity());
        map.put(0, 100);
        assertEquals(16, map.capacity());
        assertEquals(12, map.size());
    }

    @Test
    void shouldDoubleCapacityWhenLoadExceedsLimit() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 13; key++) {
            map.put(key, key * 10);
        }
        assertEquals(32, map.capacity());
        for (int key = 0; key < 13; key++) {
            assertEquals(key * 10, map.get(key));
        }
    }

    @Test
    void shouldPreserveValuesAfterSeveralResizes() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 1000; key++) {
            map.put(key, -key);
        }
        assertEquals(1000, map.size());
        assertTrue(map.size() <= map.capacity() * 0.75);
        for (int key = 0; key < 1000; key++) {
            assertEquals(-key, map.get(key));
        }
    }

    @Test
    void shouldFindEqualButDifferentKeyObjects() {
        HashMapCustom<Key, Integer> map = new HashMapCustom<>();
        map.put(new Key("same", 7), 8);
        assertEquals(8, map.get(new Key("same", 7)));
    }

    @Test
    void shouldHandleNegativeAndMinimumHashCodes() {
        HashMapCustom<Key, Integer> map = new HashMapCustom<>();
        map.put(new Key("negative", -17), 1);
        map.put(new Key("minimum", Integer.MIN_VALUE), 2);
        assertEquals(1, map.get(new Key("negative", -17)));
        assertEquals(2, map.get(new Key("minimum", Integer.MIN_VALUE)));
    }

    @Test
    void shouldKeepCollisionChainAfterDeletingFirstKey() {
        HashMapCustom<Key, Integer> map = collisionMap();
        assertTrue(map.remove(new Key("a", 15)));
        assertEquals(2, map.get(new Key("b", 15)));
        assertEquals(3, map.get(new Key("c", 15)));
    }

    @Test
    void shouldKeepCollisionChainAfterDeletingMiddleKey() {
        HashMapCustom<Key, Integer> map = collisionMap();
        assertTrue(map.remove(new Key("b", 15)));
        assertEquals(1, map.get(new Key("a", 15)));
        assertEquals(3, map.get(new Key("c", 15)));
    }

    @Test
    void shouldReuseDeletedSlot() {
        HashMapCustom<Key, Integer> map = collisionMap();
        map.remove(new Key("a", 15));
        map.put(new Key("d", 15), 4);
        assertEquals(4, map.get(new Key("d", 15)));
        assertEquals(2, map.get(new Key("b", 15)));
        assertEquals(3, map.size());
    }

    @Test
    void shouldUpdateKeyBeyondDeletedSlotWithoutDuplicatingIt() {
        HashMapCustom<Key, Integer> map = collisionMap();
        map.remove(new Key("a", 15));
        map.put(new Key("c", 15), 30);
        assertEquals(2, map.size());
        assertEquals(30, map.get(new Key("c", 15)));
        assertTrue(map.remove(new Key("c", 15)));
        assertFalse(map.containsKey(new Key("c", 15)));
    }

    @Test
    void shouldFinishSearchWhenEverySlotIsDeleted() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 16; key++) {
            map.put(key, key);
            map.remove(key);
        }
        assertNull(map.get(100));
        assertFalse(map.containsKey(100));
        assertFalse(map.remove(100));
        map.put(100, 7);
        assertEquals(7, map.get(100));
    }

    @Test
    void shouldRehashCollisionsAndSkipDeletedEntries() {
        HashMapCustom<Key, Integer> map = collisionMap();
        map.remove(new Key("b", 15));
        for (int key = 0; key < 50; key++) {
            map.put(new Key("key" + key, 15), key);
        }
        assertEquals(52, map.size());
        assertFalse(map.containsKey(new Key("b", 15)));
        assertEquals(3, map.get(new Key("c", 15)));
        for (int key = 0; key < 50; key++) {
            assertEquals(key, map.get(new Key("key" + key, 15)));
        }
    }

    @Test
    void shouldMatchStandardMapOnMixedOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        Map<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(42);
        for (int step = 0; step < 10_000; step++) {
            int key = random.nextInt(400) - 200;
            int operation = random.nextInt(3);
            if (operation == 0) {
                int value = random.nextInt();
                actual.put(key, value);
                expected.put(key, value);
            } else if (operation == 1) {
                boolean existed = expected.containsKey(key);
                expected.remove(key);
                assertEquals(existed, actual.remove(key));
            } else {
                assertEquals(expected.get(key), actual.get(key));
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.containsKey(key), actual.containsKey(key));
        }
        for (int key = -200; key < 200; key++) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }

    private HashMapCustom<Key, Integer> collisionMap() {
        HashMapCustom<Key, Integer> map = new HashMapCustom<>();
        map.put(new Key("a", 15), 1);
        map.put(new Key("b", 15), 2);
        map.put(new Key("c", 15), 3);
        return map;
    }

    private record Key(String value, int hash) {
        @Override
        public int hashCode() {
            return hash;
        }
    }
}
