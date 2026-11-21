package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

class HashMapCustomTest {
    @Test
    void shouldStartWithAnEmptyTable() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get(10));
        assertFalse(map.containsKey(10));
        assertFalse(map.remove(10));
    }

    @Test
    void shouldStoreAndRetrieveAValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("alpha", 10);

        assertEquals(1, map.size());
        assertTrue(map.containsKey("alpha"));
        assertEquals(10, map.get("alpha"));
    }

    @Test
    void shouldUpdateAnEqualKeyWithoutGrowingAtTheThreshold() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        int threshold = map.capacity() * 3 / 4;
        for (int index = 0; index < threshold; index++) {
            map.put(1_000 + index, "old");
        }

        map.put(1_000, "new");

        assertEquals(threshold, map.size());
        assertEquals("new", map.get(1_000));
        assertEquals(16, map.capacity());
    }

    @Test
    void shouldDistinguishNullValueFromAMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("present", null);

        assertNull(map.get("present"));
        assertNull(map.get("absent"));
        assertTrue(map.containsKey("present"));
        assertFalse(map.containsKey("absent"));
        assertTrue(map.remove("present"));
        assertFalse(map.containsKey("present"));
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertThrows(NullPointerException.class, () -> map.put(null, 5));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertEquals(0, map.size());
    }

    @Test
    void shouldWrapLinearProbingAroundTheEndOfTheArray() {
        HashMapCustom<Integer, String> map = collidingMap();

        assertEquals("first", map.get(15));
        assertEquals("second", map.get(31));
        assertEquals("third", map.get(47));
        assertNull(map.get(63));
        assertEquals(3, map.size());
    }

    @Test
    void shouldFindCollidingKeysAfterDeletingAnEarlierKey() {
        HashMapCustom<Integer, String> map = collidingMap();

        assertTrue(map.remove(15));
        assertFalse(map.remove(15));

        assertNull(map.get(15));
        assertFalse(map.containsKey(15));
        assertEquals("second", map.get(31));
        assertEquals("third", map.get(47));
        assertEquals(2, map.size());
    }

    @Test
    void shouldUpdateExistingKeyBeyondATombstone() {
        HashMapCustom<Integer, String> map = collidingMap();
        map.remove(15);

        map.put(47, "updated");

        assertEquals(2, map.size());
        assertEquals("updated", map.get(47));
        assertTrue(map.remove(47));
        assertFalse(map.containsKey(47));
    }

    @Test
    void shouldInsertThroughATombstone() {
        HashMapCustom<Integer, String> map = collidingMap();
        map.remove(15);
        map.remove(31);

        map.put(63, "new");

        assertEquals(2, map.size());
        assertEquals("new", map.get(63));
        assertEquals("third", map.get(47));
    }

    @Test
    void shouldHashNegativeValuesIncludingIntegerMinValue() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(0, "zero");
        map.put(Integer.MIN_VALUE, "minimum");
        map.put(-7, "negative");
        map.put(7, "positive");

        assertEquals("zero", map.get(0));
        assertEquals("minimum", map.get(Integer.MIN_VALUE));
        assertEquals("negative", map.get(-7));
        assertEquals("positive", map.get(7));
    }

    @Test
    void shouldDoubleCapacityOnlyAfterExceedingLoadFactor() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int initialCapacity = map.capacity();
        int threshold = initialCapacity * 3 / 4;
        for (int key = 0; key < threshold; key++) {
            map.put(key, key * 2);
        }
        assertEquals(initialCapacity, map.capacity());

        map.put(threshold, threshold * 2);

        assertEquals(initialCapacity * 2, map.capacity());
        assertEquals(threshold + 1, map.size());
        for (int key = 0; key <= threshold; key++) {
            assertEquals(key * 2, map.get(key));
        }
    }

    @Test
    void shouldDropTombstonesWhenGrowing() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 12; key++) {
            map.put(key, key);
        }
        map.remove(0);
        map.put(12, 12);
        map.put(13, 13);

        assertEquals(32, map.capacity());
        assertEquals(13, map.size());
        assertFalse(map.containsKey(0));
        for (int key = 1; key <= 13; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldClearTombstonesWithoutGrowingWhenLiveLoadIsLow() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 12; key++) {
            map.put(key, key);
        }
        for (int key = 0; key < 4; key++) {
            assertTrue(map.remove(key));
        }
        for (int key = 12; key <= 14; key++) {
            map.put(key, key);
        }

        assertEquals(16, map.capacity());
        assertEquals(11, map.size());
        for (int key = 0; key < 4; key++) {
            assertFalse(map.containsKey(key));
        }
        for (int key = 4; key <= 14; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldRetainAllEntriesAcrossSeveralGrowths() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 1_000; key++) {
            map.put(key * 17, -key);
        }

        assertEquals(1_000, map.size());
        assertTrue((long) map.size() * 4 <= (long) map.capacity() * 3);
        for (int key = 0; key < 1_000; key++) {
            assertEquals(-key, map.get(key * 17));
        }
    }

    @Test
    void shouldMatchStandardMapOnMixedOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(33);
        for (int operation = 0; operation < 5_000; operation++) {
            int key = random.nextInt(500) - 250;
            switch (random.nextInt(4)) {
                case 0 -> {
                    expected.put(key, operation);
                    actual.put(key, operation);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                case 2 -> assertEquals(expected.get(key), actual.get(key));
                default -> assertEquals(expected.containsKey(key), actual.containsKey(key));
            }
            assertEquals(expected.size(), actual.size());
        }
    }

    private static HashMapCustom<Integer, String> collidingMap() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(15, "first");
        map.put(31, "second");
        map.put(47, "third");
        return map;
    }
}
