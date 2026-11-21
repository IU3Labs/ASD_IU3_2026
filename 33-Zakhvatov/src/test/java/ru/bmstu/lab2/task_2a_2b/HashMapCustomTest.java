package ru.bmstu.lab2.task_2a_2b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

/** Одинаковые проверки для 2A и 2B, включая tombstone и полные цепочки пробирования. */
class HashMapCustomTest {
    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldStartEmpty(HashMapCustom.Probing probing) {
        HashMapCustom<String, Integer> map = new HashMapCustom<>(probing);
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldInsertAndRetrieve(HashMapCustom.Probing probing) {
        HashMapCustom<String, Integer> map = new HashMapCustom<>(probing);
        map.put("one", 1);
        map.put("two", 2);
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(2, map.size());
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldUpdateValueWithoutChangingSizeOrCapacity(HashMapCustom.Probing probing) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
        for (int key = 0; key < 12; key++) {
            map.put(key, key);
        }
        map.put(5, 100);
        assertEquals(12, map.size());
        assertEquals(16, map.capacity());
        assertEquals(100, map.get(5));
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldDistinguishNullValueFromAbsentKey(HashMapCustom.Probing probing) {
        HashMapCustom<String, Integer> map = new HashMapCustom<>(probing);
        map.put("null-value", null);
        assertNull(map.get("null-value"));
        assertTrue(map.containsKey("null-value"));
        assertTrue(map.remove("null-value"));
        assertFalse(map.containsKey("null-value"));
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldRejectNullKeys(HashMapCustom.Probing probing) {
        HashMapCustom<String, Integer> map = new HashMapCustom<>(probing);
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertEquals(0, map.size());
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldKeepProbeChainAfterDeletingFirstKey(HashMapCustom.Probing probing) {
        HashMapCustom<Key, Integer> map = collisionMap(probing, 12);
        assertTrue(map.remove(new Key(0, 15)));
        for (int key = 1; key < 12; key++) {
            assertEquals(key, map.get(new Key(key, 15)));
        }
        assertNull(map.get(new Key(100, 15)));
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldUpdateExistingKeyBeyondDeletedSlot(HashMapCustom.Probing probing) {
        HashMapCustom<Key, Integer> map = collisionMap(probing, 12);
        map.remove(new Key(0, 15));
        map.put(new Key(11, 15), 42);
        assertEquals(11, map.size());
        assertEquals(42, map.get(new Key(11, 15)));
        assertEquals(11, map.snapshotKeys().size());
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldReuseDeletedSlot(HashMapCustom.Probing probing) {
        HashMapCustom<Key, Integer> map = collisionMap(probing, 12);
        map.remove(new Key(5, 15));
        map.put(new Key(99, 15), 99);
        assertEquals(12, map.size());
        assertEquals(16, map.capacity());
        assertEquals(99, map.get(new Key(99, 15)));
        assertFalse(map.remove(new Key(5, 15)));
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldDoubleCapacityOnlyAboveLoadThreshold(HashMapCustom.Probing probing) {
        HashMapCustom<Key, Integer> map = collisionMap(probing, 12);
        assertEquals(16, map.capacity());
        map.put(new Key(12, 15), 12);
        assertEquals(32, map.capacity());
        for (int key = 0; key <= 12; key++) {
            assertEquals(key, map.get(new Key(key, 15)));
        }
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldHandleMinimumAndNegativeHashes(HashMapCustom.Probing probing) {
        HashMapCustom<Key, String> map = new HashMapCustom<>(probing);
        map.put(new Key(1, Integer.MIN_VALUE), "min");
        map.put(new Key(2, -17), "negative");
        assertEquals("min", map.get(new Key(1, Integer.MIN_VALUE)));
        assertEquals("negative", map.get(new Key(2, -17)));
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldHandleRepeatedInsertDeleteCycles(HashMapCustom.Probing probing) {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(probing);
        for (int key = 0; key < 16; key++) {
            map.put(key, key);
            assertTrue(map.remove(key));
        }
        assertNull(map.get(100));
        assertFalse(map.containsKey(100));
        assertFalse(map.remove(100));
        map.put(100, 7);
        assertEquals(7, map.get(100));
        assertEquals(16, map.capacity());
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldBoundProbingWithManyCollisions(HashMapCustom.Probing probing) {
        HashMapCustom<Key, Integer> map = collisionMap(probing, 1_000);
        for (int key = 0; key < 1_000; key++) {
            assertEquals(key, map.get(new Key(key, 15)));
        }
        assertNull(map.get(new Key(1_001, 15)));
        for (int key = 0; key < 1_000; key += 2) {
            map.remove(new Key(key, 15));
        }
        for (int key = 1; key < 1_000; key += 2) {
            assertEquals(key, map.get(new Key(key, 15)));
        }
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldMeasureAndResetGetProbeStatistics(HashMapCustom.Probing probing) {
        HashMapCustom<Key, Integer> map = collisionMap(probing, 2);
        assertEquals(0.0, map.averageGetProbes());
        map.get(new Key(0, 15));
        map.get(new Key(1, 15));
        assertEquals(1.5, map.averageGetProbes());
        map.resetProbeStatistics();
        assertEquals(0.0, map.averageGetProbes());
        map.containsKey(new Key(0, 15));
        assertEquals(0.0, map.averageGetProbes());
    }

    @ParameterizedTest
    @EnumSource(HashMapCustom.Probing.class)
    void shouldMatchStandardMapDuringRandomOperations(HashMapCustom.Probing probing) {
        Random random = new Random(42);
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>(probing);
        Map<Integer, Integer> expected = new HashMap<>();
        for (int operation = 0; operation < 20_000; operation++) {
            int key = random.nextInt(1_000) - 500;
            switch (random.nextInt(3)) {
                case 0 -> {
                    Integer value = random.nextInt(5) == 0 ? null : random.nextInt();
                    expected.put(key, value);
                    actual.put(key, value);
                }
                case 1 -> {
                    boolean existed = expected.containsKey(key);
                    expected.remove(key);
                    assertEquals(existed, actual.remove(key));
                }
                default -> {
                    assertEquals(expected.get(key), actual.get(key));
                    assertEquals(expected.containsKey(key), actual.containsKey(key));
                }
            }
            assertEquals(expected.size(), actual.size());
            assertTrue(actual.size() <= actual.capacity() * 0.75);
        }
        for (Map.Entry<Integer, Integer> entry : expected.entrySet()) {
            assertEquals(entry.getValue(), actual.get(entry.getKey()));
        }
    }

    private HashMapCustom<Key, Integer> collisionMap(HashMapCustom.Probing probing, int size) {
        HashMapCustom<Key, Integer> map = new HashMapCustom<>(probing);
        for (int key = 0; key < size; key++) {
            map.put(new Key(key, 15), key);
        }
        return map;
    }

    private record Key(int value, int hash) {
        /** {@inheritDoc} */
        @Override
        public int hashCode() {
            return hash;
        }
    }
}
