package ru.bmstu.lab02.task_2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashMapCustomTest {

    @Test
    void shouldHandleMissingKeyInEmptyTable() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertNull(map.get(1));
        assertFalse(map.containsKey(1));
        assertFalse(map.remove(1));
    }

    @Test
    void shouldStoreAndRetrieveEntry() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(1, "one");

        assertEquals("one", map.get(1));
        assertTrue(map.containsKey(1));
        assertEquals(1, map.size());
    }

    @Test
    void shouldReplaceValueWithoutIncreasingSize() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(1, "old");
        map.put(1, "new");

        assertEquals("new", map.get(1));
        assertEquals(1, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(1, null);

        assertNull(map.get(1));
        assertTrue(map.containsKey(1));
        assertFalse(map.containsKey(2));
    }

    @Test
    void shouldProbeFromLastSlotBackToFirst() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(7, "first");
        map.put(15, "second");
        map.put(23, "third");

        assertEquals("first", map.get(7));
        assertEquals("second", map.get(15));
        assertEquals("third", map.get(23));
        assertNull(map.get(31));
    }

    @Test
    void shouldFindAndUpdateKeyBeyondDeletedSlot() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(7, "first");
        map.put(15, "second");

        assertTrue(map.remove(7));
        assertFalse(map.remove(7));
        assertEquals("second", map.get(15));

        map.put(15, "updated");

        assertEquals("updated", map.get(15));
        assertEquals(1, map.size());
    }

    @Test
    void shouldInsertCollidingKeyAfterRemoval() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "first");
        map.put(9, "second");
        map.remove(1);

        map.put(17, "third");

        assertFalse(map.containsKey(1));
        assertEquals("second", map.get(9));
        assertEquals("third", map.get(17));
        assertEquals(2, map.size());
    }

    @Test
    void shouldGrowAboveSeventyFivePercentAndKeepEntries() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int capacity = map.capacity();
        int limit = capacity * 3 / 4;

        for (int key = 0; key < limit; key++) {
            map.put(key, key * 2);
        }
        assertEquals(capacity, map.capacity());

        map.put(limit, limit * 2);

        assertEquals(capacity * 2, map.capacity());
        assertEquals(limit + 1, map.size());
        for (int key = 0; key <= limit; key++) {
            assertEquals(key * 2, map.get(key));
        }
    }

    @Test
    void shouldNotGrowWhenUpdatingAtLoadLimit() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int capacity = map.capacity();
        int limit = capacity * 3 / 4;
        for (int key = 0; key < limit; key++) {
            map.put(key, key);
        }

        map.put(0, 100);

        assertEquals(100, map.get(0));
        assertEquals(limit, map.size());
        assertEquals(capacity, map.capacity());
    }

    @Test
    void shouldPreserveEntriesWhenClearingDeletedSlots() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int capacity = map.capacity();
        map.put(0, 10);
        map.put(1, 11);
        map.put(2, 12);
        map.remove(0);
        map.remove(1);

        map.put(3, 13);

        assertEquals(capacity, map.capacity());
        assertEquals(2, map.size());
        assertFalse(map.containsKey(0));
        assertFalse(map.containsKey(1));
        assertEquals(12, map.get(2));
        assertEquals(13, map.get(3));
    }

    @Test
    void shouldHandleMinimumIntegerHashCode() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        map.put(Integer.MIN_VALUE, "minimum");

        assertEquals("minimum", map.get(Integer.MIN_VALUE));
        assertTrue(map.remove(Integer.MIN_VALUE));
        assertEquals(0, map.size());
    }

    @Test
    void shouldRejectNullKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();

        assertThrows(NullPointerException.class, () -> map.put(null, "value"));
        assertEquals(0, map.size());
    }
}