package ru.bmstu.lab02.task_2b;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HashMapQuadraticTest {

    @Test
    void testPutAndGet() {
        HashMapQuadratic<String, Integer> map = new HashMapQuadratic<>();
        map.put("one", 1);
        map.put("two", 2);
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
    }

    @Test
    void testPutUpdatesExistingKey() {
        HashMapQuadratic<String, Integer> map = new HashMapQuadratic<>();
        map.put("a", 1);
        map.put("a", 2);
        assertEquals(2, map.get("a"));
        assertEquals(1, map.size());
    }

    @Test
    void testRemove() {
        HashMapQuadratic<String, Integer> map = new HashMapQuadratic<>();
        map.put("a", 1);
        assertTrue(map.remove("a"));
        assertNull(map.get("a"));
        assertEquals(0, map.size());
    }

    @Test
    void testResize() {
        HashMapQuadratic<Integer, Integer> map = new HashMapQuadratic<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }
        assertEquals(100, map.size());
        assertTrue(map.capacity() > 16);
    }

    @Test
    void testCollisions() {
        HashMapQuadratic<Integer, Integer> map = new HashMapQuadratic<>();
        map.put(1, 100);
        map.put(17, 200);
        map.put(33, 300);
        assertEquals(100, map.get(1));
        assertEquals(200, map.get(17));
        assertEquals(300, map.get(33));
    }

    @Test
    void testRemoveAndReinsert() {
        HashMapQuadratic<Integer, Integer> map = new HashMapQuadratic<>();
        map.put(5, 50);
        map.remove(5);
        map.put(5, 500);
        assertEquals(500, map.get(5));
    }

    @Test
    void testManyElements() {
        HashMapQuadratic<Integer, Integer> map = new HashMapQuadratic<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }
        for (int i = 0; i < 1000; i++) {
            assertEquals(i, map.get(i));
        }
    }
}