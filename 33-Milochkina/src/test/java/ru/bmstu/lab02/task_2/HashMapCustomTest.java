package ru.bmstu.lab02.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет пробирование, удаление и изменение ёмкости */
class HashMapCustomTest {
    @Test
    void shouldStartEmpty() {
        var map = new HashMapCustom<Integer, String>();
        assertEquals(0, map.size());
        assertNull(map.get(3));
        assertFalse(map.containsKey(3));
        assertFalse(map.remove(3));
    }

    @Test
    void shouldUpdateExistingValue() {
        var map = new HashMapCustom<Integer, String>();
        map.put(4, "old");
        map.put(4, "new");
        assertEquals("new", map.get(4));
        assertEquals(1, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromAbsentKey() {
        var map = new HashMapCustom<Integer, String>();
        map.put(1, null);
        assertTrue(map.containsKey(1));
        assertNull(map.get(1));
        assertFalse(map.containsKey(2));
    }

    @Test
    void shouldHandleNullKey() {
        var map = new HashMapCustom<String, Integer>();
        map.put(null, 7);
        assertEquals(7, map.get(null));
        assertTrue(map.remove(null));
        assertFalse(map.containsKey(null));
    }

    @Test
    void shouldHandleNegativeHashAndMinimumInteger() {
        var map = new HashMapCustom<Integer, Integer>();
        map.put(Integer.MIN_VALUE, 1);
        map.put(-1, 2);
        map.put(1, 3);
        assertEquals(1, map.get(Integer.MIN_VALUE));
        assertEquals(2, map.get(-1));
        assertEquals(3, map.get(1));
    }

    @Test
    void shouldWrapAtEndOfArray() {
        var map = new HashMapCustom<Integer, Integer>();
        int last = map.capacity() - 1;
        map.put(last, 1);
        map.put(last + map.capacity(), 2);
        assertEquals(2, map.lastProbes());
        assertEquals(2, map.get(last + map.capacity()));
    }

    @Test
    void shouldSearchPastDeletedSlotAndUpdateWithoutDuplicating() {
        var map = new HashMapCustom<CollisionKey, Integer>();
        CollisionKey first = new CollisionKey("a");
        CollisionKey second = new CollisionKey("b");
        map.put(first, 1);
        map.put(second, 2);
        assertTrue(map.remove(first));
        map.put(new CollisionKey("b"), 3);
        assertEquals(1, map.size());
        assertEquals(3, map.get(second));
        assertFalse(map.remove(first));
    }

    @Test
    void shouldReuseDeletedSlot() {
        var map = new HashMapCustom<CollisionKey, Integer>();
        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        map.put(first, 1);
        map.remove(first);
        map.put(second, 2);
        assertEquals(2, map.get(second));
        assertEquals(1, map.lastProbes());
    }

    @Test
    void shouldGrowOnlyAfterCrossingThreshold() {
        var map = new HashMapCustom<Integer, Integer>();
        int initialCapacity = map.capacity();
        int limit = initialCapacity * 3 / 4;
        for (int key = 0; key < limit; key++) {
            map.put(key, key);
        }
        map.put(0, 0);
        assertEquals(initialCapacity, map.capacity());
        map.put(limit, limit);
        assertEquals(initialCapacity * 2, map.capacity());
        for (int key = 0; key <= limit; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldUseDifferentCapacityAtDifferentThresholds() {
        var low = new HashMapCustom<Integer, Integer>(false, 0.5);
        var high = new HashMapCustom<Integer, Integer>(false, 0.9);
        for (int key = 0; key < 10; key++) {
            low.put(key, key);
            high.put(key, key);
        }
        assertTrue(low.capacity() > high.capacity());
        assertEquals(10, high.size());
    }

    @Test
    void shouldRejectInvalidThresholds() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<>(true, 0));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<>(false, 1));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<>(false, Double.NaN));
    }

    @Test
    void shouldProbeQuadratically() {
        var map = new HashMapCustom<Integer, String>(true);
        int capacity = map.capacity();
        map.put(0, "a");
        map.put(capacity, "b");
        map.put(capacity * 2, "c");
        map.put(2, "d");
        assertEquals("c", map.get(capacity * 2));
        assertEquals(3, map.lastProbes());
        assertEquals("d", map.get(2));
        assertEquals(1, map.lastProbes());
    }

    @Test
    void shouldHandleTenThousandIdenticalHashesInBothModes() {
        for (boolean quadratic : new boolean[]{false, true}) {
            var map = new HashMapCustom<CollisionKey, Integer>(quadratic);
            int count = 10_000;
            for (int key = 0; key < count; key++) {
                map.put(new CollisionKey("key-" + key), key);
            }
            assertEquals(count, map.size());
            for (int key = 0; key < count; key++) {
                assertEquals(key, map.get(new CollisionKey("key-" + key)));
            }
            for (int key = 0; key < count; key += 2) {
                assertTrue(map.remove(new CollisionKey("key-" + key)));
            }
            assertEquals(count / 2, map.size());
            for (int key = 0; key < count; key++) {
                assertEquals(key % 2 == 0 ? null : key, map.get(new CollisionKey("key-" + key)));
            }
            for (int key = 1; key < count; key += 2) {
                assertTrue(map.remove(new CollisionKey("key-" + key)));
            }
            assertEquals(0, map.size());
        }
    }

    @Test
    void shouldMatchStandardMapInBothModes() {
        for (boolean quadratic : new boolean[]{false, true}) {
            compareMixedOperations(quadratic);
        }
    }

    private static void compareMixedOperations(boolean quadratic) {
        var actual = new HashMapCustom<Integer, Integer>(quadratic, 0.9);
        var expected = new HashMap<Integer, Integer>();
        Random random = new Random(33);
        for (int step = 0; step < 20_000; step++) {
            int key = random.nextInt(1_000) - 500;
            switch (random.nextInt(3)) {
                case 0 -> {
                    actual.put(key, step);
                    expected.put(key, step);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                default -> assertEquals(expected.get(key), actual.get(key));
            }
            assertEquals(expected.size(), actual.size());
            assertEquals(expected.containsKey(key), actual.containsKey(key));
        }
    }
}
