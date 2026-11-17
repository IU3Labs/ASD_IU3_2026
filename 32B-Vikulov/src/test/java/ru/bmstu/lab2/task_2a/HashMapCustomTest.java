package ru.bmstu.lab2.task_2a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;

class HashMapCustomTest {

    /** Ключ с заданным хешем: позволяет вручную создавать коллизии. */
    private record FixedHashKey(String name, int hash) {

        @Override
        public int hashCode() {
            return hash;
        }
    }

    @Test
    void shouldReturnNullAndZeroSizeWhenMapIsEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
        assertEquals(HashMapCustom.DEFAULT_CAPACITY, map.capacity());
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
    }

    @Test
    void shouldStoreAndReturnValues() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("one", 1);
        map.put("two", 2);

        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertTrue(map.containsKey("one"));
        assertEquals(2, map.size());
    }

    @Test
    void shouldUpdateValueWithoutChangingSizeWhenKeyExists() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);

        map.put("key", 42);

        assertEquals(42, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldRemoveExistingKeyAndReportMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);

        assertTrue(map.remove("key"));
        assertFalse(map.remove("key"));
        assertFalse(map.containsKey("key"));
        assertTrue(map.isEmpty());
    }

    @Test
    void shouldFindKeyAfterRemovingEarlierKeyInSameProbeChain() {
        // все три ключа начинают пробирование с ячейки 3: цепочка 3 → 4 → 5
        HashMapCustom<FixedHashKey, String> map = new HashMapCustom<>(16);
        FixedHashKey first = new FixedHashKey("a", 3);
        FixedHashKey second = new FixedHashKey("b", 3);
        FixedHashKey third = new FixedHashKey("c", 3);
        map.put(first, "A");
        map.put(second, "B");
        map.put(third, "C");

        map.remove(second);

        // без маркера DELETED поиск остановился бы на освободившейся ячейке 4
        assertEquals("C", map.get(third));
        assertEquals("A", map.get(first));
        assertNull(map.get(second));
    }

    @Test
    void shouldReuseDeletedCellOnInsert() {
        HashMapCustom<FixedHashKey, String> map = new HashMapCustom<>(16);
        FixedHashKey first = new FixedHashKey("a", 5);
        FixedHashKey second = new FixedHashKey("b", 5);
        map.put(first, "A");
        map.remove(first);

        map.put(second, "B");
        map.put(first, "A2");

        assertEquals("B", map.get(second));
        assertEquals("A2", map.get(first));
        assertEquals(2, map.size());
    }

    @Test
    void shouldDoubleCapacityWhenLoadFactorExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(4);

        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        assertEquals(4, map.capacity());

        // четвёртый элемент дал бы загрузку 4/4 > 0.75
        map.put(4, 4);
        assertEquals(8, map.capacity());
        for (int key = 1; key <= 4; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldKeepLoadFactorBelowLimitForManyKeys() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(1);

        for (int key = 0; key < 10_000; key++) {
            map.put(key, key * 2);
            assertTrue(map.size() <= map.capacity() * HashMapCustom.MAX_LOAD_FACTOR);
        }
        for (int key = 0; key < 10_000; key++) {
            assertEquals(key * 2, map.get(key));
        }
    }

    @Test
    void shouldHandleNegativeAndMinValueHashCodes() {
        HashMapCustom<FixedHashKey, Integer> map = new HashMapCustom<>(7);
        FixedHashKey minHash = new FixedHashKey("min", Integer.MIN_VALUE);
        FixedHashKey negative = new FixedHashKey("neg", -12_345);

        map.put(minHash, 1);
        map.put(negative, 2);

        assertEquals(1, map.get(minHash));
        assertEquals(2, map.get(negative));
    }

    @Test
    void shouldNotGrowForeverUnderInsertRemoveChurn() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(16);

        // каждый remove оставляет маркер DELETED; таблица должна их вычищать, а не расти
        for (int key = 0; key < 100_000; key++) {
            map.put(key, key);
            map.remove(key);
        }

        assertTrue(map.isEmpty());
        assertEquals(16, map.capacity());
    }

    @Test
    void shouldStoreNullValueAndDistinguishItFromMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        map.put("key", null);

        assertNull(map.get("key"));
        assertTrue(map.containsKey("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldVisitEveryLiveEntryInForEach() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.put("b", 2);
        map.put("c", 3);
        map.remove("b");
        Map<String, Integer> visited = new HashMap<>();

        map.forEach(visited::put);

        assertEquals(Map.of("a", 1, "c", 3), visited);
    }

    @Test
    void shouldRejectNullKeyAndNonPositiveCapacity() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(0));
    }

    @Test
    void shouldMatchJavaHashMapOnRandomOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        Map<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(2026);

        for (int i = 0; i < 50_000; i++) {
            int key = random.nextInt(2_000) - 1_000;
            switch (random.nextInt(3)) {
                case 0 -> {
                    actual.put(key, i);
                    expected.put(key, i);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                default -> assertEquals(expected.get(key), actual.get(key));
            }
            assertEquals(expected.size(), actual.size());
        }
    }
}
