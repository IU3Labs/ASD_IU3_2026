package ru.bmstu.lab2.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки хеш-таблицы: HashMapProbingTest. */
class HashMapProbingTest {
    private static final int LARGE_SIZE = 100_000;
    private static final int RANDOM_OPERATIONS = 10_000;
    private static final int KEY_BOUND = 200;
    private static final long SEED = 20261120L;

    @Test
    void shouldFindKeyBeyondDeletedSlot() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        map.put(new CollisionKey("first"), 1);
        map.put(new CollisionKey("second"), 2);
        map.put(new CollisionKey("third"), 3);
        assertTrue(map.remove(new CollisionKey("second")));
        assertEquals(3, map.get(new CollisionKey("third")));
        assertFalse(map.containsKey(new CollisionKey("absent")));
    }

    @Test
    void shouldReuseDeletedSlotWithoutLosingFollowingKeys() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        map.put(new CollisionKey("a"), 1);
        map.put(new CollisionKey("b"), 2);
        map.remove(new CollisionKey("a"));
        map.put(new CollisionKey("c"), 3);
        assertEquals(2, map.size());
        assertEquals(2, map.get(new CollisionKey("b")));
        assertEquals(3, map.get(new CollisionKey("c")));
    }

    @Test
    void shouldUpdateExistingKeyBeyondDeletedSlot() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        map.put(new CollisionKey("a"), 1);
        map.put(new CollisionKey("b"), 2);
        map.remove(new CollisionKey("a"));
        map.put(new CollisionKey("b"), 9);
        assertEquals(1, map.size());
        assertEquals(9, map.get(new CollisionKey("b")));
    }

    @Test
    void shouldPreserveEntriesAcrossRepeatedGrowth() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < LARGE_SIZE; key++) {
            map.put(key, -key);
        }
        for (int key = 0; key < LARGE_SIZE; key++) {
            assertEquals(-key, map.get(key));
        }
        assertEquals(LARGE_SIZE, map.size());
        assertTrue(map.size() <= map.capacity() * 3 / 4);
    }

    @Test
    void shouldHandleNegativeAndMinimumHashCodes() {
        HashMapCustom<FixedHashKey, String> map = new HashMapCustom<>();
        FixedHashKey minimum = new FixedHashKey("minimum", Integer.MIN_VALUE);
        FixedHashKey negative = new FixedHashKey("negative", -7);
        map.put(minimum, "a");
        map.put(negative, "b");
        assertEquals("a", map.get(minimum));
        assertEquals("b", map.get(negative));
    }

    @Test
    void shouldWrapProbeAcrossEndOfArray() {
        HashMapCustom<FixedHashKey, Integer> map = new HashMapCustom<>();
        int hash = map.capacity() - 1;
        map.put(new FixedHashKey("a", hash), 1);
        map.put(new FixedHashKey("b", hash), 2);
        map.put(new FixedHashKey("c", hash), 3);
        map.remove(new FixedHashKey("a", hash));
        assertEquals(3, map.get(new FixedHashKey("c", hash)));
    }

    @Test
    void shouldClearAccumulatedDeletedSlotsWithoutGrowing() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int initialCapacity = map.capacity();
        map.put(-1, 99);
        for (int key = 0; key < RANDOM_OPERATIONS; key++) {
            map.put(key, key);
            assertTrue(map.remove(key));
        }
        assertEquals(initialCapacity, map.capacity());
        assertEquals(1, map.size());
        assertEquals(99, map.get(-1));
    }

    @Test
    void shouldNotResurrectDeletedValuesWhenGrowing() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        map.put(0, 5);
        map.remove(0);
        for (int key = 1; key <= KEY_BOUND; key++) {
            map.put(key, key);
        }
        assertFalse(map.containsKey(0));
        assertEquals(KEY_BOUND, map.size());
    }

    @Test
    void shouldMatchStandardMapOnRandomOperations() {
        Random random = new Random(SEED);
        Map<Integer, Integer> expected = new HashMap<>();
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        for (int step = 0; step < RANDOM_OPERATIONS; step++) {
            int key = random.nextInt(KEY_BOUND);
            int operation = random.nextInt(3);
            if (operation == 0) {
                int value = random.nextInt();
                expected.put(key, value);
                actual.put(key, value);
            } else if (operation == 1) {
                assertEquals(expected.containsKey(key), actual.remove(key));
                expected.remove(key);
            } else {
                assertEquals(expected.get(key), actual.get(key));
            }
            assertEquals(expected.containsKey(key), actual.containsKey(key));
            assertEquals(expected.size(), actual.size());
        }
    }
}
