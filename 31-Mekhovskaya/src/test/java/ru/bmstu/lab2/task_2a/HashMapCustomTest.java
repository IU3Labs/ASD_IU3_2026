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

/**
 * Тесты задачи 2A — {@link HashMapCustom}.
 */
class HashMapCustomTest {

    /** Ёмкость таблицы по умолчанию. */
    private static final int DEFAULT_CAPACITY = 16;

    /** 12 = 0.75 * 16 — максимум ключей, который помещается в таблицу ёмкости 16 без расширения. */
    private static final int KEYS_BEFORE_RESIZE = 12;

    /** Размер стресс-теста. */
    private static final int STRESS_SIZE = 100_000;

    /** Диапазон ключей в случайном тесте: меньше числа операций, чтобы были повторы и обновления. */
    private static final int RANDOM_KEY_RANGE = 5_000;

    /** Фиксированное зерно, чтобы тест был воспроизводимым. */
    private static final long SEED = 7L;

    @Test
    void shouldBeEmptyWhenCreated() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertTrue(map.isEmpty());
        assertEquals(DEFAULT_CAPACITY, map.capacity());
    }

    @Test
    void shouldReturnNullForMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("one", 1);

        assertNull(map.get("two"));
        assertFalse(map.containsKey("two"));
        assertEquals(-1, map.getOrDefault("two", -1));
    }

    @Test
    void shouldPutAndGetValues() {
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
    void shouldRemoveExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.put("b", 2);

        assertTrue(map.remove("a"));
        assertFalse(map.containsKey("a"));
        assertNull(map.get("a"));
        assertEquals(1, map.size());
        assertEquals(2, map.get("b"));
    }

    @Test
    void shouldReturnFalseWhenRemovingMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertFalse(map.remove("absent"));
        map.put("a", 1);
        assertTrue(map.remove("a"));
        assertFalse(map.remove("a"));
        assertTrue(map.isEmpty());
    }

    @Test
    void shouldFindKeyBehindDeletedSlotInProbeChain() {
        // Ключи 1, 17, 33 при ёмкости 16 попадают в одну ячейку 1 и стоят в ячейках 1, 2, 3.
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "a");
        map.put(17, "b");
        map.put(33, "c");

        map.remove(17);

        assertEquals("c", map.get(33));
        assertEquals("a", map.get(1));
        assertNull(map.get(17));
    }

    @Test
    void shouldReuseDeletedSlotWithoutDuplicatingKey() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(1, "a");
        map.put(17, "b");
        map.put(33, "c");
        map.remove(17);

        map.put(33, "updated");
        map.put(49, "d");

        assertEquals(3, map.size());
        assertEquals("updated", map.get(33));
        assertEquals("d", map.get(49));
        map.remove(33);
        assertNull(map.get(33));
    }

    @Test
    void shouldDoubleCapacityWhenLoadFactorExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < KEYS_BEFORE_RESIZE; i++) {
            map.put(i, i);
        }
        assertEquals(DEFAULT_CAPACITY, map.capacity());

        map.put(KEYS_BEFORE_RESIZE, KEYS_BEFORE_RESIZE);

        assertEquals(DEFAULT_CAPACITY * 2, map.capacity());
        for (int i = 0; i <= KEYS_BEFORE_RESIZE; i++) {
            assertEquals(i, map.get(i));
        }
    }

    @Test
    void shouldKeepLoadFactorAtMostThreeQuarters() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(1);
        for (int i = 0; i < STRESS_SIZE; i++) {
            map.put(i, i);
            assertTrue(map.size() <= map.capacity() * 0.75, "load factor exceeded at size " + map.size());
        }
    }

    @Test
    void shouldNotLoopForeverAfterManyInsertsAndRemovals() {
        // Без учёта надгробий таблица заполнилась бы маркерами DELETED и поиск отсутствующего
        // ключа не нашёл бы пустой ячейки.
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < STRESS_SIZE; i++) {
            map.put(i, i);
            map.remove(i);
        }

        assertTrue(map.isEmpty());
        assertFalse(map.containsKey(-1));
        assertEquals(DEFAULT_CAPACITY, map.capacity());
    }

    @Test
    void shouldStayCorrectWhenKeysAreReplacedNearLoadThreshold() {
        // Таблица заполнена живыми ключами до порога, затем много раз «удалить старый + вставить новый».
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < KEYS_BEFORE_RESIZE; i++) {
            map.put(i, i);
        }
        for (int i = KEYS_BEFORE_RESIZE; i < STRESS_SIZE; i++) {
            assertTrue(map.remove(i - KEYS_BEFORE_RESIZE));
            map.put(i, i);
        }

        assertEquals(KEYS_BEFORE_RESIZE, map.size());
        assertTrue(map.capacity() <= DEFAULT_CAPACITY * 2, "capacity grew to " + map.capacity());
        for (int i = STRESS_SIZE - KEYS_BEFORE_RESIZE; i < STRESS_SIZE; i++) {
            assertEquals(i, map.get(i));
        }
    }

    @Test
    void shouldHandleNegativeAndMinValueHashCodes() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(3);
        map.put(Integer.MIN_VALUE, "min");
        map.put(-5, "minus five");
        map.put(Integer.MAX_VALUE, "max");

        assertEquals("min", map.get(Integer.MIN_VALUE));
        assertEquals("minus five", map.get(-5));
        assertEquals("max", map.get(Integer.MAX_VALUE));
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
    void shouldVisitEveryEntryInForEach() {
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
    void shouldBehaveLikeJavaHashMapOnRandomOperations() {
        Random random = new Random(SEED);
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        Map<Integer, Integer> expected = new HashMap<>();
        for (int i = 0; i < STRESS_SIZE; i++) {
            int key = random.nextInt(RANDOM_KEY_RANGE);
            if (random.nextInt(3) == 0) {
                assertEquals(expected.remove(key) != null, actual.remove(key));
            } else {
                expected.put(key, i);
                actual.put(key, i);
            }
        }

        assertEquals(expected.size(), actual.size());
        for (int key = 0; key < RANDOM_KEY_RANGE; key++) {
            assertEquals(expected.get(key), actual.get(key));
        }
    }
}
