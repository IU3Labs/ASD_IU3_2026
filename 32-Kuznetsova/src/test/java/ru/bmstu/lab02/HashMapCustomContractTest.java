package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.HashMapCustom.Probing;

/** Тесты, общие для обоих способов пробирования; конкретные классы задают {@link #probing()}. */
abstract class HashMapCustomContractTest {

    private static final long SEED = 2024L;
    private static final int COLLISION_KEYS = 10_000;

    abstract Probing probing();

    private <K, V> HashMapCustom<K, V> newMap() {
        return new HashMapCustom<>(probing());
    }

    @Test
    void shouldBeEmptyInitially() {
        HashMapCustom<String, Integer> map = newMap();

        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
        assertNull(map.get("missing"));
    }

    @Test
    void shouldPutAndGetValues() {
        HashMapCustom<String, Integer> map = newMap();

        map.put("one", 1);
        map.put("two", 2);

        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(2, map.size());
    }

    @Test
    void shouldReturnNullForMissingKey() {
        HashMapCustom<String, Integer> map = newMap();
        map.put("present", 1);

        assertNull(map.get("absent"));
        assertFalse(map.containsKey("absent"));
    }

    @Test
    void shouldUpdateValueOfExistingKeyWithoutChangingSize() {
        HashMapCustom<String, Integer> map = newMap();
        map.put("key", 1);

        map.put("key", 2);

        assertEquals(2, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldReportContainedKeys() {
        HashMapCustom<Integer, String> map = newMap();
        map.put(7, "seven");

        assertTrue(map.containsKey(7));
        assertFalse(map.containsKey(8));
    }

    @Test
    void shouldRemoveExistingKeyAndReportMissingOne() {
        HashMapCustom<String, Integer> map = newMap();
        map.put("a", 1);

        assertTrue(map.remove("a"));
        assertFalse(map.remove("a"));
        assertFalse(map.containsKey("a"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldStoreNullValueAndStillContainKey() {
        HashMapCustom<String, Integer> map = newMap();

        map.put("nothing", null);

        assertNull(map.get("nothing"));
        assertTrue(map.containsKey("nothing"));
    }

    @Test
    void shouldRejectNullKey() {
        HashMapCustom<String, Integer> map = newMap();

        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }

    @Test
    void shouldGrowCapacityOnlyAfterLoadFactorIsExceeded() {
        HashMapCustom<Integer, Integer> map = newMap();
        int initialCapacity = map.capacity();
        int threshold = (int) (initialCapacity * map.loadFactor());

        for (int i = 0; i < threshold; i++) {
            map.put(i, i);
        }
        assertEquals(initialCapacity, map.capacity());

        map.put(threshold, threshold);
        assertEquals(initialCapacity * 2, map.capacity());
    }

    @Test
    void shouldKeepAllEntriesAfterManyResizes() {
        HashMapCustom<Integer, Integer> map = newMap();
        int count = 20_000;

        for (int i = 0; i < count; i++) {
            map.put(i, i * 2);
        }

        assertEquals(count, map.size());
        for (int i = 0; i < count; i++) {
            assertEquals(i * 2, map.get(i));
        }
    }

    @Test
    void shouldKeepChainReachableAfterRemovingMiddleOfCollisionChain() {
        HashMapCustom<CollisionKey, Integer> map = newMap();
        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        CollisionKey third = new CollisionKey("third");
        map.put(first, 1);
        map.put(second, 2);
        map.put(third, 3);

        map.remove(second);

        assertEquals(1, map.get(first));
        assertNull(map.get(second));
        assertEquals(3, map.get(third));
    }

    @Test
    void shouldReuseDeletedSlotWithoutGrowing() {
        HashMapCustom<CollisionKey, Integer> map = newMap();
        CollisionKey first = new CollisionKey("first");
        CollisionKey second = new CollisionKey("second");
        map.put(first, 1);
        map.put(second, 2);
        int capacity = map.capacity();

        map.remove(first);
        map.put(new CollisionKey("third"), 3);

        assertEquals(capacity, map.capacity());
        assertEquals(2, map.get(second));
        assertEquals(2, map.size());
    }

    @Test
    void shouldNotBlowUpWhenInsertingAndRemovingManyKeysOneByOne() {
        HashMapCustom<Integer, Integer> map = newMap();

        for (int i = 0; i < 100_000; i++) {
            map.put(i, i);
            assertTrue(map.remove(i));
        }

        assertTrue(map.isEmpty());
        assertTrue(map.capacity() <= 64, "маркеры DELETED должны вычищаться перестройкой: " + map.capacity());
        map.put(1, 1);
        assertEquals(1, map.get(1));
    }

    @Test
    void shouldHandleKeyWithMinIntegerHashCode() {
        HashMapCustom<FixedHashKey, String> map = newMap();
        FixedHashKey key = new FixedHashKey("min", Integer.MIN_VALUE);

        map.put(key, "value");

        assertEquals("value", map.get(key));
        assertTrue(map.remove(key));
    }

    @Test
    void shouldPlaceCollidingKeysUpToHighLoadFactor() {
        // Если бы последовательность проб не обходила все ячейки, часть ключей не нашла бы себе места.
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>(probing(), 16, 0.9);
        int capacity = map.capacity();
        int count = (int) (capacity * 0.9);

        for (int i = 0; i < count; i++) {
            map.put(new CollisionKey("k" + i), i);
        }

        assertEquals(capacity, map.capacity());
        for (int i = 0; i < count; i++) {
            assertEquals(i, map.get(new CollisionKey("k" + i)));
        }
    }

    @Test
    void shouldVisitEveryEntryInForEach() {
        HashMapCustom<Integer, Integer> map = newMap();
        for (int i = 0; i < 100; i++) {
            map.put(i, i + 1);
        }
        map.remove(50);
        Map<Integer, Integer> visited = new HashMap<>();

        map.forEach(visited::put);

        assertEquals(99, visited.size());
        assertEquals(50, visited.get(49));
        assertFalse(visited.containsKey(50));
    }

    @Test
    void shouldMatchJavaHashMapOnRandomOperations() {
        Random random = new Random(SEED);
        HashMapCustom<Integer, Integer> actual = newMap();
        Map<Integer, Integer> expected = new HashMap<>();

        for (int step = 0; step < 50_000; step++) {
            int key = random.nextInt(2000);
            switch (random.nextInt(3)) {
                case 0 -> {
                    actual.put(key, step);
                    expected.put(key, step);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                default -> assertEquals(expected.get(key), actual.get(key));
            }
            assertEquals(expected.size(), actual.size());
        }
    }

    // ---------- 2C: искусственные коллизии ----------

    @Test
    void shouldHandleTenThousandCollidingKeys() {
        HashMapCustom<CollisionKey, Integer> map = newMap();

        for (int i = 0; i < COLLISION_KEYS; i++) {
            map.put(new CollisionKey("key-" + i), i);
        }
        assertEquals(COLLISION_KEYS, map.size());

        for (int i = 0; i < COLLISION_KEYS; i++) {
            assertEquals(i, map.get(new CollisionKey("key-" + i)));
        }
        assertFalse(map.containsKey(new CollisionKey("absent")));

        for (int i = 0; i < COLLISION_KEYS; i++) {
            assertTrue(map.remove(new CollisionKey("key-" + i)));
        }
        assertTrue(map.isEmpty());
        assertNull(map.get(new CollisionKey("key-0")));
    }

    /** Ключ с заданным хеш-кодом для проверки граничных значений хеша. */
    private record FixedHashKey(String name, int hash) {

        @Override
        public int hashCode() {
            return hash;
        }
    }
}
