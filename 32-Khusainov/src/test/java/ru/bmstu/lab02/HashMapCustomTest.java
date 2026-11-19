package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Проверки контракта HashMapCustom и граничных случаев. */
class HashMapCustomTest {
    private final HashMapCustom<Integer, String> map = new HashMapCustom<>();

    @Test
    void shouldStartEmpty() {
        assertEquals(0, map.size());
        assertNull(map.get(42));
        assertFalse(map.containsKey(42));
    }

    @Test
    void shouldInsertAndFind() {
        map.put(4, "four");
        assertEquals("four", map.get(4));
    }

    @Test
    void shouldUpdateWithoutGrowth() {
        int initial = map.capacity();
        for (int i = 0; i < initial * 3 / 4; i++) {
            map.put(i, "a");
        }
        map.put(0, "b");
        assertEquals(initial, map.capacity());
        assertEquals(initial * 3 / 4, map.size());
        assertEquals("b", map.get(0));
    }

    @Test
    void shouldDoubleAboveThreshold() {
        int initial = map.capacity();
        int threshold = initial * 3 / 4;
        for (int i = 0; i <= threshold; i++) {
            map.put(i * initial, Integer.toString(i));
        }
        assertEquals(2 * initial, map.capacity());
        for (int i = 0; i <= threshold; i++) {
            assertEquals(Integer.toString(i), map.get(i * initial));
        }
    }

    @Test
    void shouldProbeAcrossArrayEnd() {
        int last = map.capacity() - 1;
        map.put(last, "a");
        map.put(last + map.capacity(), "b");
        assertEquals("b", map.get(last + map.capacity()));
    }

    @Test
    void shouldFindKeyBeyondDeletion() {
        int next = map.capacity();
        map.put(0, "a");
        map.put(next, "b");
        assertTrue(map.remove(0));
        assertNull(map.get(0));
        assertEquals("b", map.get(next));
    }

    @Test
    void shouldUpdatePastDeletedSlot() {
        int next = map.capacity();
        map.put(0, "a");
        map.put(next, "b");
        map.remove(0);
        map.put(next, "c");
        assertEquals(1, map.size());
        assertEquals("c", map.get(next));
    }

    @Test
    void shouldTerminateWhenAllSlotsAreDeleted() {
        for (int i = 0; i < map.capacity(); i++) {
            map.put(i, "x");
            map.remove(i);
        }
        assertFalse(map.containsKey(17));
        map.put(17, "new");
        assertEquals("new", map.get(17));
    }

    @Test
    void shouldRemoveOnlyOnce() {
        map.put(1, "x");
        assertTrue(map.remove(1));
        assertFalse(map.remove(1));
        assertEquals(0, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        map.put(5, null);
        assertNull(map.get(5));
        assertTrue(map.containsKey(5));
        assertFalse(map.containsKey(6));
    }

    @Test
    void shouldHandleMinimumHash() {
        map.put(Integer.MIN_VALUE, "min");
        assertEquals("min", map.get(Integer.MIN_VALUE));
    }

    @Test
    void shouldMatchEqualObjects() {
        HashMapCustom<String, Integer> words = new HashMapCustom<>();
        words.put("equal", 1);
        assertEquals(1, words.get(new String("equal")));
    }

    @Test
    void shouldRejectNullKey() {
        assertThrows(NullPointerException.class, () -> map.put(null, "x"));
    }
}
