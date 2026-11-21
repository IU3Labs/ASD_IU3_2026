
package ru.bmstu.lab2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Модульные тесты хеш-таблицы для задания 2A.
 */
class HashMapCustomTest {

    @Test
    void newMapIsEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
    }

    @Test
    void putAndGetOneElement() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertNull(map.put("apple", 10));

        assertEquals(10, map.get("apple"));
        assertEquals(1, map.size());
    }

    @Test
    void putSeveralElements() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("one", 1);
        map.put("two", 2);
        map.put("three", 3);

        assertEquals(3, map.size());
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(3, map.get("three"));
    }

    @Test
    void putExistingKeyUpdatesValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 10);

        assertEquals(10, map.put("key", 20));
        assertEquals(20, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void getMissingKeyReturnsNull() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertNull(map.get("missing"));
    }

    @Test
    void containsKeyFindsExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("exists", 42);

        assertTrue(map.containsKey("exists"));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    void removeExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 15);

        assertEquals(15, map.remove("key"));
        assertNull(map.get("key"));
        assertFalse(map.containsKey("key"));
        assertEquals(0, map.size());
    }

    @Test
    void removeMissingKeyReturnsNull() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertNull(map.remove("missing"));
        assertEquals(0, map.size());
    }

    @Test
    void collisionsAreResolvedByLinearProbing() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();

        map.put(new CollisionKey(1), 100);
        map.put(new CollisionKey(2), 200);
        map.put(new CollisionKey(3), 300);

        assertEquals(100, map.get(new CollisionKey(1)));
        assertEquals(200, map.get(new CollisionKey(2)));
        assertEquals(300, map.get(new CollisionKey(3)));
        assertEquals(3, map.size());
    }

    @Test
    void removingCollisionDoesNotBreakSearch() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();

        map.put(new CollisionKey(1), 100);
        map.put(new CollisionKey(2), 200);
        map.put(new CollisionKey(3), 300);

        assertEquals(200, map.remove(new CollisionKey(2)));

        assertEquals(100, map.get(new CollisionKey(1)));
        assertEquals(300, map.get(new CollisionKey(3)));
        assertEquals(2, map.size());
    }

    @Test
    void deletedCellCanBeReused() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();

        map.put(new CollisionKey(1), 10);
        map.put(new CollisionKey(2), 20);
        map.remove(new CollisionKey(1));
        map.put(new CollisionKey(3), 30);

        assertEquals(20, map.get(new CollisionKey(2)));
        assertEquals(30, map.get(new CollisionKey(3)));
        assertEquals(2, map.size());
    }

    @Test
    void capacityDoublesWhenLoadFactorExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (int i = 0; i < 13; i++) {
            map.put(i, i * 10);
        }

        assertEquals(32, map.capacity());
        assertEquals(13, map.size());

        for (int i = 0; i < 13; i++) {
            assertEquals(i * 10, map.get(i));
        }
    }

    @Test
    void manyElementsSurviveRepeatedResizing() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();

        for (int i = 0; i < 1000; i++) {
            map.put(i, i + 1);
        }

        assertEquals(1000, map.size());

        for (int i = 0; i < 1000; i++) {
            assertEquals(i + 1, map.get(i));
        }
    }

    @Test
    void nullValuesAreSupported() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("empty", null);

        assertTrue(map.containsKey("empty"));
        assertNull(map.get("empty"));
        assertEquals(1, map.size());
    }

    @Test
    void nullKeysAreRejected() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
    }

    /**
     * Ключ с постоянным хеш-кодом для проверки коллизий.
     */
    private static final class CollisionKey {

        private final int id;

        private CollisionKey(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {
            return 5;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }

            if (!(object instanceof CollisionKey other)) {
                return false;
            }

            return id == other.id;
        }
    }
}
