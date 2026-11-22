package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

/**
 * Тесты задачи 2A: хеш-таблица с линейным пробированием.
 */
class HashMapCustomTest {

    /**
     * Ключ с одинаковым hashCode у всех экземпляров — все ключи попадают в одну цепочку.
     *
     * @param name содержимое ключа
     */
    private record CollisionKey(String name) {
        @Override
        public int hashCode() {
            return 7;
        }
    }

    @Test
    void shouldBeEmptyAfterCreation() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
    }

    @Test
    void shouldPutAndGet() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("one", 1);
        map.put("two", 2);
        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertTrue(map.containsKey("one"));
        assertEquals(2, map.size());
    }

    @Test
    void shouldUpdateValueWithoutChangingSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);
        map.put("key", 2);
        assertEquals(2, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldRemoveAndReinsert() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);
        assertTrue(map.remove("key"));
        assertFalse(map.containsKey("key"));
        assertNull(map.get("key"));
        assertEquals(0, map.size());
        map.put("key", 5);
        assertEquals(5, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertFalse(map.remove("missing"));
        map.put("a", 1);
        assertTrue(map.remove("a"));
        assertFalse(map.remove("a")); // повторное удаление
    }

    @Test
    void shouldFindKeyAfterDeletionInsideChain() {
        // При ёмкости 16 ключи 1, 17, 33 попадают в ячейку 1 и стоят цепочкой 1 -> 2 -> 3
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "a");
        map.put(17, "b");
        map.put(33, "c");
        assertTrue(map.remove(17)); // в середине цепочки остаётся DELETED
        assertEquals("c", map.get(33));
        assertTrue(map.containsKey(33));
        assertEquals("a", map.get(1));
        assertFalse(map.containsKey(17));
    }

    @Test
    void shouldReuseDeletedSlotWithoutDuplicates() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "a");
        map.put(17, "b");
        map.put(33, "c");
        map.remove(17);
        map.put(33, "updated"); // ключ уже есть дальше по цепочке — должен обновиться, а не задвоиться
        assertEquals(2, map.size());
        assertTrue(map.remove(33));
        assertNull(map.get(33));
        map.put(17, "again");
        assertEquals("again", map.get(17));
        assertEquals(2, map.size());
    }

    @Test
    void shouldHandleManyCollisions() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 1_000; i++) {
            map.put(new CollisionKey("k" + i), i);
        }
        assertEquals(1_000, map.size());
        for (int i = 0; i < 1_000; i += 2) {
            assertTrue(map.remove(new CollisionKey("k" + i)));
        }
        for (int i = 0; i < 1_000; i++) {
            assertEquals(i % 2 == 0 ? null : i, map.get(new CollisionKey("k" + i)));
        }
        assertEquals(500, map.size());
    }

    @Test
    void shouldResizeWhenLoadFactorExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 12; i++) {
            map.put(i, i); // 12 / 16 = 0.75 — ещё допустимо
        }
        assertEquals(16, map.capacity());
        map.put(12, 12); // 13 / 16 > 0.75 — удвоение
        assertEquals(32, map.capacity());
        for (int i = 0; i <= 12; i++) {
            assertEquals(i, map.get(i));
        }
    }

    @Test
    void shouldKeepLoadFactorAtMostThreeQuarters() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(1);
        for (int i = 0; i < 10_000; i++) {
            map.put(i, i);
            assertTrue(map.size() <= 0.75 * map.capacity());
        }
        assertEquals(16_384, map.capacity());
    }

    @Test
    void shouldNotGrowFromRepeatedPutAndRemove() {
        // Маркеры DELETED очищаются перехешированием в ту же ёмкость
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 10_000; i++) {
            map.put(i, i);
            map.remove(i);
        }
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        map.put(5, 5);
        assertEquals(5, map.get(5));
    }

    @Test
    void shouldHandleMinValueHashCode() {
        // Math.abs(Integer.MIN_VALUE) отрицателен — индекс должен корректно попасть в массив
        HashMapCustom<Integer, String> map = new HashMapCustom<>(10);
        map.put(Integer.MIN_VALUE, "min");
        map.put(Integer.MAX_VALUE, "max");
        map.put(-1, "minus one");
        assertEquals("min", map.get(Integer.MIN_VALUE));
        assertEquals("max", map.get(Integer.MAX_VALUE));
        assertEquals("minus one", map.get(-1));
        assertTrue(map.remove(Integer.MIN_VALUE));
        assertFalse(map.containsKey(Integer.MIN_VALUE));
    }

    @Test
    void shouldAllowNullValues() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", null);
        assertTrue(map.containsKey("key"));
        assertNull(map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldRejectNullKeyOnPutAndIgnoreItOnLookup() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertNull(map.get(null));
        assertFalse(map.containsKey(null));
        assertFalse(map.remove(null));
    }

    @Test
    void shouldRejectInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(0));
    }

    @Test
    void shouldMatchJavaHashMapOnRandomOperations() {
        Random random = new Random(9);
        HashMapCustom<Integer, Integer> custom = new HashMapCustom<>();
        Map<Integer, Integer> expected = new HashMap<>();
        for (int i = 0; i < 50_000; i++) {
            int key = random.nextInt(2_000) - 1_000;
            switch (random.nextInt(3)) {
                case 0 -> {
                    custom.put(key, i);
                    expected.put(key, i);
                }
                case 1 -> assertEquals(expected.remove(key) != null, custom.remove(key));
                default -> assertEquals(expected.get(key), custom.get(key));
            }
            assertEquals(expected.size(), custom.size());
        }
    }
}
