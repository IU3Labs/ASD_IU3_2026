package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task2DTest_LoadFactorHashMap {
    @Test void shouldStartEmpty() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        assertEquals(0, map.size());
    }
    @Test void shouldInsertAndFind() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        map.put("one", 1);
        assertEquals(1, map.get("one").intValue());
    }
    @Test void shouldUpdateValue() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        map.put("x", 1); map.put("x", 2);
        assertEquals(1, map.size());
        assertEquals(2, map.get("x").intValue());
    }
    @Test void shouldReturnNullForUnknownKey() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        assertNull(map.get("missing"));
    }
    @Test void shouldRecognizeNullValues() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        map.put("x", null);
        assertTrue(map.containsKey("x"));
        assertNull(map.get("x"));
    }
    @Test void shouldRemoveExistingKey() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        map.put("x", 10);
        assertTrue(map.remove("x"));
        assertFalse(map.containsKey("x"));
    }
    @Test void shouldIgnoreMissingRemove() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        assertFalse(map.remove("x"));
    }
    @Test void shouldKeepProbeChainAfterDelete() {
        var map = new Task2D_LoadFactorHashMap<Integer, Integer>();
        map.put(1, 1); map.put(17, 17); map.put(33, 33);
        assertTrue(map.remove(17));
        assertEquals(33, map.get(33).intValue());
    }
    @Test void shouldResizeAndKeepValues() {
        var map = new Task2D_LoadFactorHashMap<Integer, Integer>();
        for (int i = 0; i < 150; i++) map.put(i, i * 2);
        assertTrue(map.capacity() > 16);
        for (int i = 0; i < 150; i++) assertEquals(i * 2, map.get(i).intValue());
    }
    @Test void shouldReuseDeletedSlots() {
        var map = new Task2D_LoadFactorHashMap<Integer, Integer>();
        map.put(1, 1); map.put(17, 17);
        map.remove(1); map.put(33, 33);
        assertEquals(33, map.get(33).intValue());
        assertEquals(17, map.get(17).intValue());
    }
    @Test void shouldRejectNullKeys() {
        var map = new Task2D_LoadFactorHashMap<String, Integer>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
    }
    @Test void shouldHandleMinimumHashCode() {
        var map = new Task2D_LoadFactorHashMap<Integer, Integer>();
        map.put(Integer.MIN_VALUE, 5);
        assertEquals(5, map.get(Integer.MIN_VALUE).intValue());
    }
    @Test void shouldResizeAtDifferentThresholds() {
        var low = new Task2D_LoadFactorHashMap<Integer, Integer>(0.5);
        var high = new Task2D_LoadFactorHashMap<Integer, Integer>(0.9);
        for (int i = 0; i < 100; i++) { low.put(i, i); high.put(i, i); }
        assertTrue(low.capacity() >= high.capacity());
    }
    @Test void shouldRejectInvalidThreshold() {
        assertThrows(IllegalArgumentException.class, () -> new Task2D_LoadFactorHashMap<>(0.0));
        assertThrows(IllegalArgumentException.class, () -> new Task2D_LoadFactorHashMap<>(1.0));
    }

}
