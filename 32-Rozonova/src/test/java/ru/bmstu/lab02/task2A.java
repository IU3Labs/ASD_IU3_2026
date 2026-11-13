package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for HashMapCustom implementation with linear probing.
 */
class HashMapCustomTest {

    private HashMapCustom<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapCustom<>();
    }

    @Test
    void should_be_empty_initially() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    void should_put_and_get_elements() {
        map.put("one", 1);
        map.put("two", 2);

        assertFalse(map.isEmpty());
        assertEquals(2, map.size());
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
    }

    @Test
    void should_update_value_for_existing_key() {
        map.put("key", 100);
        assertEquals(100, map.get("key"));

        map.put("key", 200);
        assertEquals(200, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void should_check_contains_key() {
        map.put("apple", 10);

        assertTrue(map.containsKey("apple"));
        assertFalse(map.containsKey("banana"));
        assertFalse(map.containsKey(null));
    }

    @Test
    void should_remove_elements_correctly() {
        map.put("a", 1);
        map.put("b", 2);

        assertTrue(map.remove("a"));
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
        assertEquals(1, map.size());

        assertFalse(map.remove("nonexistent"));
    }