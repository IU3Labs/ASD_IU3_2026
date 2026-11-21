package ru.bmstu.lab02;

import java.util.HashMap;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashMapCustomTest {

    @Test
    void shouldReportMissingKeyInAnEmptyTable() {
        HashMapCustom<Integer, String> table = new HashMapCustom<>();
        assertEquals(0, table.size());
        assertEquals(16, table.capacity());
        assertNull(table.get(1));
        assertFalse(table.containsKey(1));
        assertFalse(table.remove(1));
    }

    @Test
    void shouldInsertAndReadAMapping() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();
        table.put("key", 42);
        assertEquals(42, table.get("key"));
        assertTrue(table.containsKey("key"));
        assertEquals(1, table.size());
    }

    @Test
    void shouldUpdateAnEqualKeyWithoutIncreasingSize() {
        HashMapCustom<Key, Integer> table = new HashMapCustom<>();
        table.put(new Key(1, 0), 10);
        table.put(new Key(1, 0), 20);
        assertEquals(20, table.get(new Key(1, 0)));
        assertEquals(1, table.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        HashMapCustom<String, Integer> table = new HashMapCustom<>();
        table.put("key", null);
        assertNull(table.get("key"));
        assertTrue(table.containsKey("key"));
        assertTrue(table.remove("key"));
        assertFalse(table.containsKey("key"));
    }

    @Test
    void shouldRejectNullKeysForAllOperations() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> table.put(null, 1));
        assertThrows(NullPointerException.class, () -> table.get(null));
        assertThrows(NullPointerException.class, () -> table.containsKey(null));
        assertThrows(NullPointerException.class, () -> table.remove(null));
    }

    @Test
    void shouldHandleNegativeAndMinimumIntegerHashes() {
        HashMapCustom<Integer, String> table = new HashMapCustom<>();
        table.put(Integer.MIN_VALUE, "minimum");
        table.put(-17, "negative");
        table.put(17, "positive");
        assertEquals("minimum", table.get(Integer.MIN_VALUE));
        assertEquals("negative", table.get(-17));
        assertEquals("positive", table.get(17));
    }

    @Test
    void shouldProbeAcrossTheEndOfTheArray() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        table.put(15, 1);
        table.put(31, 2);
        table.put(47, 3);
        assertEquals(3, table.get(47));
        assertNull(table.get(63));
    }

    @Test
    void shouldFindKeysPastMultipleDeletedSlots() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        table.put(1, 10);
        table.put(17, 20);
        table.put(33, 30);
        assertTrue(table.remove(1));
        assertTrue(table.remove(17));
        assertFalse(table.remove(17));
        assertEquals(30, table.get(33));
        assertEquals(1, table.size());
    }

    @Test
    void shouldFindExistingKeyBeforeReusingTombstoneForNewKey() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        table.put(1, 10);
        table.put(17, 20);
        table.remove(1);
        table.put(17, 21);
        assertEquals(1, table.size());
        assertEquals(21, table.get(17));
        table.put(33, 30);
        assertEquals(2, table.size());
        assertEquals(30, table.get(33));
        assertFalse(table.containsKey(1));
    }

    @Test
    void shouldDoubleCapacityOnlyAfterExceedingMaximumLoad() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        for (int key = 0; key < 12; key++) {
            table.put(key, key * 10);
        }
        table.put(0, -1);
        assertEquals(16, table.capacity());
        table.put(12, 120);
        assertEquals(32, table.capacity());
        assertEquals(13, table.size());
        assertEquals(-1, table.get(0));
        for (int key = 1; key <= 12; key++) {
            assertEquals(key * 10, table.get(key));
        }
    }

    @Test
    @Timeout(value = 2, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void shouldReclaimAccumulatedTombstonesAtTheSameCapacity() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        table.put(0, 100);
        // Without cleanup, each deleted key would leave a marker in a different slot.
        for (int key = 1; key < table.capacity(); key++) {
            table.put(key, key);
            assertTrue(table.remove(key));
        }
        assertNull(table.get(1));
        table.put(14, 200);
        assertEquals(16, table.capacity());
        assertEquals(2, table.size());
        assertEquals(100, table.get(0));
        assertEquals(200, table.get(14));
    }

    @Test
    void shouldPreserveCollidingKeysThroughRepeatedResizes() {
        HashMapCustom<Key, Integer> table = new HashMapCustom<>();
        for (int key = 0; key < 130; key++) {
            table.put(new Key(key, 15), key);
        }
        for (int key = 0; key < 130; key++) {
            assertEquals(key, table.get(new Key(key, 15)));
        }
        assertEquals(130, table.size());
    }

    @Test
    void shouldMatchStandardMapDuringMixedOperations() {
        HashMapCustom<Integer, Integer> table = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(73);
        for (int attempt = 0; attempt < 4000; attempt++) {
            int key = random.nextInt(200) - 100;
            if (random.nextInt(3) == 0) {
                assertEquals(expected.remove(key) != null, table.remove(key));
            } else {
                expected.put(key, attempt);
                table.put(key, attempt);
            }
            assertEquals(expected.size(), table.size());
            assertEquals(expected.get(key), table.get(key));
            assertEquals(expected.containsKey(key), table.containsKey(key));
        }
    }

    private record Key(int id, int hash) {

        /** Supplies the chosen hash, including collisions between unequal records. */
        @Override
        public int hashCode() {
            return hash;
        }
    }
}
