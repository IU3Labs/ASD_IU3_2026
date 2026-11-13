package ru.bmstu.lab02.task_2a;

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

    private static final long SEED = 20261108L;
    private static final int DEFAULT_CAPACITY = 16;

    @Test
    void shouldBeEmptyWhenCreated() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertEquals(DEFAULT_CAPACITY, map.capacity());
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
    }

    @Test
    void shouldReturnValueAfterPut() {
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
    void shouldStoreNullValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", null);

        assertTrue(map.containsKey("key"));
        assertNull(map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldRemoveExistingKeyOnlyOnce() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);

        assertTrue(map.remove("key"));
        assertFalse(map.remove("key"));
        assertFalse(map.containsKey("key"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldFindKeysAfterDeletedSlotInProbeChain() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        // При ёмкости 16 ключи 1, 17 и 33 попадают в ячейку 1 и выстраиваются в одну цепочку пробирования.
        map.put(1, "a");
        map.put(17, "b");
        map.put(33, "c");

        map.remove(17);

        assertEquals("c", map.get(33));
        assertEquals("a", map.get(1));
        assertNull(map.get(17));
    }

    @Test
    void shouldNotDuplicateKeyWhenDeletedSlotPrecedesIt() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "a");
        map.put(17, "b");
        map.remove(1);

        map.put(17, "updated");

        assertEquals(1, map.size());
        assertEquals("updated", map.get(17));
    }

    @Test
    void shouldReuseDeletedSlot() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "a");
        map.remove(1);
        map.put(17, "b");

        assertEquals("b", map.get(17));
        assertEquals(1, map.size());
        assertEquals(DEFAULT_CAPACITY, map.capacity());
    }

    @Test
    void shouldDoubleCapacityWhenLoadFactorExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int maxBeforeResize = (int) (DEFAULT_CAPACITY * 0.75);
        for (int i = 0; i < maxBeforeResize; i++) {
            map.put(i, i);
        }
        assertEquals(DEFAULT_CAPACITY, map.capacity());

        map.put(maxBeforeResize, maxBeforeResize);

        assertEquals(DEFAULT_CAPACITY * 2, map.capacity());
        for (int i = 0; i <= maxBeforeResize; i++) {
            assertEquals(i, map.get(i));
        }
    }

    @Test
    void shouldKeepLoadFactorAtMostThreeQuarters() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(1);
        for (int i = 0; i < 10_000; i++) {
            map.put(i, i);
            assertTrue(map.size() <= map.capacity() * 0.75);
        }
    }

    @Test
    void shouldNotGrowWhenPutAndRemoveAlternate() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 10_000; i++) {
            map.put(i, i);
            map.remove(i);
        }

        assertEquals(0, map.size());
        assertEquals(DEFAULT_CAPACITY, map.capacity());
    }

    @Test
    void shouldStayCorrectAndBoundedUnderChurnNearThreshold() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        int liveKeys = 11;
        for (int i = 0; i < liveKeys; i++) {
            map.put(i, i);
        }
        // 11 ключей - у самого порога 12 при ёмкости 16: таблица не должна расти из-за маркеров DELETED.
        for (int i = liveKeys; i < 10_000; i++) {
            map.remove(i - liveKeys);
            map.put(i, i);
        }

        assertEquals(liveKeys, map.size());
        assertTrue(map.capacity() <= DEFAULT_CAPACITY * 2);
        for (int i = 10_000 - liveKeys; i < 10_000; i++) {
            assertEquals(i, map.get(i));
        }
        assertFalse(map.containsKey(10_000 - liveKeys - 1));
    }

    @Test
    void shouldHandleNegativeAndMinValueHashCodes() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(10);
        map.put(-7, "negative");
        map.put(Integer.MIN_VALUE, "min");

        assertEquals("negative", map.get(-7));
        assertEquals("min", map.get(Integer.MIN_VALUE));
    }

    @Test
    void shouldRejectNullKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }

    @Test
    void shouldRejectNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(0));
    }

    @Test
    void shouldMatchJavaHashMapOnRandomOperations() {
        Random random = new Random(SEED);
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        Map<Integer, Integer> expected = new HashMap<>();

        for (int step = 0; step < 200_000; step++) {
            // Малый диапазон ключей даёт много обновлений, удалений и повторных вставок одних и тех же ключей.
            int key = random.nextInt(2_000) - 1_000;
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
}
