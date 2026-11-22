package ru.bmstu.lab2.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import ru.bmstu.lab2.task_2.HashMapCustom.Probing;

class HashMapCustomTest {
    private static final double DEFAULT_LOAD_FACTOR = 0.75;
    private static final int COLLISION_COUNT = 10_000;

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldStartEmpty(Probing probing) {
        HashMapCustom<String, Integer> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);

        assertEquals(0, table.size());
        assertEquals(16, table.capacity());
        assertNull(table.get("missing"));
        assertFalse(table.containsKey("missing"));
        assertFalse(table.remove("missing"));
    }

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldFindAnEqualKey(Probing probing) {
        HashMapCustom<String, Integer> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
        String key = "alpha";
        table.put(key, 11);

        assertEquals(11, table.get(new String(key)));
        assertTrue(table.containsKey(key));
        assertEquals(1, table.size());
    }

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldUpdateAtTheLoadLimitWithoutGrowing(Probing probing) {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
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

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldDoubleCapacityAndKeepEntries(Probing probing) {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
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

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldDistinguishNullValueFromMissingKey(Probing probing) {
        HashMapCustom<String, Integer> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
        table.put("present", null);

        assertNull(table.get("present"));
        assertTrue(table.containsKey("present"));
        assertFalse(table.containsKey("missing"));
        assertTrue(table.remove("present"));
        assertFalse(table.containsKey("present"));
    }

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldKeepLaterKeysReachableAfterRemovingAConflict(Probing probing) {
        HashMapCustom<CollisionKey, Integer> table =
                new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        table.put(first, 1);
        table.put(second, 2);

        assertTrue(table.remove(first));
        assertFalse(table.remove(first));
        assertEquals(2, table.get(second));
        assertEquals(1, table.size());
    }

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldReuseATombstoneAndUpdateTheKeyBehindIt(Probing probing) {
        HashMapCustom<CollisionKey, Integer> table =
                new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        CollisionKey third = new CollisionKey("third");
        table.put(first, 1);
        table.put(second, 2);
        table.remove(first);
        table.put(second, 20);
        table.put(third, 3);

        assertEquals(2, table.size());
        assertEquals(20, table.get(second));
        assertEquals(3, table.get(third));
        assertEquals(1, table.probesForGet(third));
    }

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldHandleTheMinimumHashCode(Probing probing) {
        HashMapCustom<Integer, String> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
        table.put(Integer.MIN_VALUE, "minimum");
        table.put(0, "zero");

        assertEquals("minimum", table.get(Integer.MIN_VALUE));
        assertTrue(table.remove(Integer.MIN_VALUE));
        assertEquals("zero", table.get(0));
    }

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldKeepLiveKeysDuringRepeatedInsertionsAndRemovals(Probing probing) {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
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

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldMatchJavaMapDuringMixedOperations(Probing probing) {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
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

    @ParameterizedTest
    @EnumSource(Probing.class)
    void shouldHandleTenThousandCollidingKeys(Probing probing) {
        HashMapCustom<CollisionKey, Integer> table =
                new HashMapCustom<>(probing, DEFAULT_LOAD_FACTOR);
        CollisionKey[] keys = new CollisionKey[COLLISION_COUNT];
        for (int i = 0; i < keys.length; i++) {
            keys[i] = new CollisionKey("key-" + i);
            table.put(keys[i], i);
        }
        assertEquals(COLLISION_COUNT, table.size());
        for (int i = 0; i < keys.length; i++) {
            assertEquals(i, table.get(keys[i]));
        }
        for (int i = 0; i < keys.length; i += 2) {
            assertTrue(table.remove(keys[i]));
        }
        assertEquals(COLLISION_COUNT / 2, table.size());
        for (int i = 0; i < keys.length; i += 2) {
            assertFalse(table.containsKey(keys[i]));
            assertEquals(i + 1, table.get(keys[i + 1]));
        }
    }

    @Test
    void shouldUseLinearProbingWithTheDefaultLoadFactor() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        table.put(0, 0);
        table.put(16, 16);
        assertEquals(2, table.probesForGet(1));

        for (int key = 1; key <= 10; key++) {
            table.put(key, key);
        }

        assertEquals(16, table.capacity());
        table.put(11, 11);
        assertEquals(32, table.capacity());
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> table.put(null, 1));
        assertThrows(NullPointerException.class, () -> table.get(null));
        assertThrows(NullPointerException.class, () -> table.containsKey(null));
        assertThrows(NullPointerException.class, () -> table.remove(null));
    }

    @Test
    void shouldVisitEveryIndexWithQuadraticProbing() {
        boolean[] seen = new boolean[256];
        for (int attempt = 0; attempt < seen.length; attempt++) {
            int index = (int) (Probing.QUADRATIC.offset(attempt) % seen.length);
            assertFalse(seen[index]);
            seen[index] = true;
        }
    }
}
