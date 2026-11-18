package ru.bmstu.lab02.task_2a;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HashMapCustomTest {

    @Test
    void testPutAndGet() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("one", 1);
        map.put("two", 2);
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
    }

    @Test
    void testPutUpdatesExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.put("a", 2);
        assertEquals(2, map.get("a"));
        assertEquals(1, map.size());
    }

    @Test
    void testGetMissingKeyReturnsNull() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertNull(map.get("missing"));
    }

    @Test
    void testContainsKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("x", 10);
        assertTrue(map.containsKey("x"));
        assertFalse(map.containsKey("y"));
    }

    @Test
    void testRemove() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        assertTrue(map.remove("a"));
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
        assertEquals(0, map.size());
    }

    @Test
    void testRemoveMissingKeyReturnsFalse() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertFalse(map.remove("nothing"));
    }

    @Test
    void testRemoveAndReinsert() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.remove("a");
        map.put("a", 2);
        assertEquals(2, map.get("a"));
    }

    @Test
    void testResizeOnHighLoad() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int n = 100;
        for (int i = 0; i < n; i++) map.put(i, i * 10);
        assertEquals(n, map.size());
        for (int i = 0; i < n; i++) assertEquals(i * 10, map.get(i));
        assertTrue(map.capacity() > 16); //емкость должна была вырасти
    }

    @Test
    void testDeletedMarkerDoesNotBreakProbing() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        //эти ключи попадают в одну цепочку пробирования
        map.put(1, 100);
        map.put(17, 200); 
        map.remove(1);    //ставим DELETED
        map.put(33, 300); //должен найтись слот после DELETED
        assertEquals(200, map.get(17));
        assertEquals(300, map.get(33));
        assertNull(map.get(1));
    }

    @Test
    void testSizeAndCapacity() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        map.put("a", 1);
        assertEquals(1, map.size());
    }

    @Test
    void testNullKeyThrows() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
    }

    @Test
    void testManyElementsStress() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int n = 10_000;
        for (int i = 0; i < n; i++) map.put(i, i);
        assertEquals(n, map.size());
        for (int i = 0; i < n; i++) assertEquals(i, map.get(i));
    }
}