package ru.bmstu.lab02;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HashMapCustomTest {

    private HashMapCustom<String, Integer> map;

    @BeforeEach
    void setUp() {
        map = new HashMapCustom<>();
    }

    /** Ключ с заданным хешем: позволяет строить коллизии вручную. */
    private record BadKey(String name, int hash) {
        @Override
        public int hashCode() {
            return hash;
        }
    }

    // 1. Новая таблица пуста
    @Test
    void shouldBeEmptyWhenCreated() {
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("anything"));
        assertFalse(map.containsKey("anything"));
    }

    // 2. put + get
    @Test
    void shouldReturnValueAfterPut() {
        map.put("one", 1);
        map.put("two", 2);

        assertEquals(1, map.get("one"));
        assertEquals(2, map.get("two"));
        assertEquals(2, map.size());
    }

    // 3. get отсутствующего ключа возвращает null
    @Test
    void shouldReturnNullForMissingKey() {
        map.put("a", 1);
        assertNull(map.get("b"));
    }

    // 4. Обновление значения не увеличивает size
    @Test
    void shouldUpdateValueWhenKeyExists() {
        map.put("key", 1);
        map.put("key", 2);

        assertEquals(2, map.get("key"));
        assertEquals(1, map.size());
    }

    // 5. containsKey
    @Test
    void shouldReportContainedKeys() {
        map.put("present", 1);
        assertTrue(map.containsKey("present"));
        assertFalse(map.containsKey("absent"));
    }

    // 6. remove существующего и несуществующего ключа
    @Test
    void shouldReturnTrueOnlyWhenRemovingExistingKey() {
        map.put("a", 1);

        assertTrue(map.remove("a"));
        assertFalse(map.containsKey("a"));
        assertNull(map.get("a"));
        assertEquals(0, map.size());

        assertFalse(map.remove("a"));
        assertFalse(map.remove("never-existed"));
    }

    // 7. Повторная вставка после удаления
    @Test
    void shouldReinsertKeyAfterRemove() {
        map.put("k", 1);
        map.remove("k");
        map.put("k", 2);

        assertEquals(2, map.get("k"));
        assertEquals(1, map.size());
    }

    // 8. Коллизии: ключи с одним хешем лежат в одной цепочке и все находятся
    @Test
    void shouldStoreAllCollidingKeys() {
        BadKey a = new BadKey("a", 5);
        BadKey b = new BadKey("b", 5);
        BadKey c = new BadKey("c", 5);
        HashMapCustom<BadKey, Integer> m = new HashMapCustom<>();

        m.put(a, 1);
        m.put(b, 2);
        m.put(c, 3);

        assertEquals(1, m.get(a));
        assertEquals(2, m.get(b));
        assertEquals(3, m.get(c));
        assertEquals(3, m.size());
    }

    // 9. Маркер DELETED: удаление в середине цепочки не теряет следующие ключи
    @Test
    void shouldKeepLaterKeysWhenRemovingFromMiddleOfChain() {
        BadKey a = new BadKey("a", 3);
        BadKey b = new BadKey("b", 3);
        BadKey c = new BadKey("c", 3);
        HashMapCustom<BadKey, Integer> m = new HashMapCustom<>();
        m.put(a, 1);
        m.put(b, 2);
        m.put(c, 3);

        assertTrue(m.remove(b));

        assertEquals(1, m.get(a));
        assertNull(m.get(b));
        assertEquals(3, m.get(c)); // c лежит за ячейкой DELETED и должна находиться
        assertEquals(2, m.size());
    }

    // 10. Вставка в освободившуюся DELETED-ячейку не создаёт дубликатов
    @Test
    void shouldReuseDeletedSlotWithoutDuplicates() {
        BadKey a = new BadKey("a", 7);
        BadKey b = new BadKey("b", 7);
        BadKey c = new BadKey("c", 7);
        HashMapCustom<BadKey, Integer> m = new HashMapCustom<>();
        m.put(a, 1);
        m.put(b, 2);
        m.remove(a);

        m.put(c, 3);          // займёт ячейку a
        m.put(b, 20);         // b уже есть: должно обновиться, а не добавиться

        assertEquals(2, m.size());
        assertEquals(20, m.get(b));
        assertEquals(3, m.get(c));
        assertNull(m.get(a));
    }

    // 11. Заворот цепочки через конец массива
    @Test
    void shouldWrapProbingAroundArrayEnd() {
        int last = map.capacity() - 1;
        HashMapCustom<BadKey, Integer> m = new HashMapCustom<>();
        BadKey a = new BadKey("a", last);
        BadKey b = new BadKey("b", last);
        BadKey c = new BadKey("c", last);

        m.put(a, 1);
        m.put(b, 2);
        m.put(c, 3);

        assertEquals(1, m.get(a));
        assertEquals(2, m.get(b));
        assertEquals(3, m.get(c));
    }

    // 12. Ёмкость удваивается, когда загрузка превышает 0.75
    @Test
    void shouldDoubleCapacityWhenLoadFactorExceeded() {
        for (int i = 0; i < 12; i++) {   // 12 / 16 = 0.75, ещё допустимо
            map.put("k" + i, i);
        }
        assertEquals(16, map.capacity());

        map.put("k12", 12);              // 13 / 16 > 0.75
        assertEquals(32, map.capacity());
        assertEquals(13, map.size());
    }

    // 13. Данные сохраняются после нескольких перехеширований
    @Test
    void shouldKeepAllEntriesAfterRehashing() {
        int n = 10_000;
        for (int i = 0; i < n; i++) {
            map.put("key" + i, i);
        }
        assertEquals(n, map.size());
        for (int i = 0; i < n; i++) {
            assertEquals(i, map.get("key" + i));
        }
    }

    // 14. Коэффициент загрузки не превышает 0.75 после каждой вставки
    @Test
    void shouldNeverExceedLoadFactorThreshold() {
        for (int i = 0; i < 5_000; i++) {
            map.put("k" + i, i);
            assertTrue((double) map.size() / map.capacity() <= 0.75,
                    "Load factor exceeded at size " + map.size());
        }
    }

    // 15. Отрицательные хеши и Integer.MIN_VALUE
    @Test
    void shouldHandleNegativeAndMinValueHashCodes() {
        HashMapCustom<BadKey, Integer> m = new HashMapCustom<>();
        BadKey negative = new BadKey("neg", -17);
        BadKey min = new BadKey("min", Integer.MIN_VALUE);
        BadKey max = new BadKey("max", Integer.MAX_VALUE);

        m.put(negative, 1);
        m.put(min, 2);
        m.put(max, 3);

        assertEquals(1, m.get(negative));
        assertEquals(2, m.get(min));
        assertEquals(3, m.get(max));
    }

    // 16. null-ключ запрещён
    @Test
    void shouldThrowOnNullKey() {
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
    }

    // 17. null-значение допустимо, containsKey отличает его от отсутствия ключа
    @Test
    void shouldAllowNullValue() {
        map.put("k", null);

        assertNull(map.get("k"));
        assertTrue(map.containsKey("k"));
        assertEquals(1, map.size());
    }

    // 18. Некорректная начальная ёмкость
    @Test
    void shouldThrowWhenInitialCapacityIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(-5));
    }

    // 19. Маленькая начальная ёмкость (1) растёт корректно
    @Test
    void shouldWorkWithInitialCapacityOne() {
        HashMapCustom<Integer, Integer> m = new HashMapCustom<>(1);
        for (int i = 0; i < 100; i++) {
            m.put(i, i * 10);
        }
        assertEquals(100, m.size());
        for (int i = 0; i < 100; i++) {
            assertEquals(i * 10, m.get(i));
        }
    }

    // 20. Много циклов put/remove не раздувают таблицу и не приводят к зависанию
    @Test
    void shouldNotGrowTableOnPutRemoveChurn() {
        for (int i = 0; i < 100_000; i++) {
            map.put("temp" + i, i);
            assertTrue(map.remove("temp" + i));
        }
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("temp-missing")); // поиск должен завершиться
        assertFalse(map.containsKey("another-missing"));
    }

    // 21. Случайные операции, сверка с java.util.HashMap
    @Test
    void shouldMatchJavaHashMapOnRandomOperations() {
        Random random = new Random(2026);
        Map<Integer, Integer> reference = new HashMap<>();
        HashMapCustom<Integer, Integer> custom = new HashMapCustom<>();

        for (int step = 0; step < 50_000; step++) {
            int key = random.nextInt(2_000);
            switch (random.nextInt(3)) {
                case 0 -> {
                    int value = random.nextInt();
                    reference.put(key, value);
                    custom.put(key, value);
                }
                case 1 -> assertEquals(reference.remove(key) != null, custom.remove(key));
                default -> {
                    assertEquals(reference.get(key), custom.get(key));
                    assertEquals(reference.containsKey(key), custom.containsKey(key));
                }
            }
            assertEquals(reference.size(), custom.size());
        }

        for (Map.Entry<Integer, Integer> entry : reference.entrySet()) {
            assertEquals(entry.getValue(), custom.get(entry.getKey()));
        }
    }

    // 22. Таблица почти заполнена, мешают маркеры DELETED: она растёт, а не перестраивается вхолостую
    @Test
    void shouldGrowInsteadOfRehashingInPlaceWhenTableIsMostlyFull() {
        for (int i = 0; i < 11; i++) {
            map.put("base" + i, i);
        }
        assertEquals(16, map.capacity());

        for (int i = 0; i < 100; i++) {
            map.put("tmp" + i, i);
            map.remove("tmp" + i);
        }

        assertEquals(32, map.capacity());
        assertEquals(11, map.size());
    }
}
