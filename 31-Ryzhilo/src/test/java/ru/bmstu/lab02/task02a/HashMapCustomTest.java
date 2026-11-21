package ru.bmstu.lab02.task02a;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.bmstu.lab02.task02a.HashMapCustom;
import ru.bmstu.lab02.task02b.QuadraticHashMap;

/** Проверяет решение задачи 02A. */
class HashMapCustomTest {
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Новая таблица пуста")
    void shouldStartEmpty(boolean quadratic) {
        HashMapCustom<String, Integer> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        assertEquals(0, map.size());
        assertNull(map.get("absent"));
        assertFalse(map.containsKey("absent"));
        assertFalse(map.remove("absent"));
        assertEquals(1, map.getProbeCount("absent"));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Обновление на пороге загрузки сохраняет ёмкость")
    void shouldUpdateWithoutGrowingAtThreshold(boolean quadratic) {
        HashMapCustom<Integer, Integer> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        int capacity = map.capacity();
        int limit = capacity * 3 / 4;
        for (int key = 0; key < limit; key++) {
            map.put(key, key);
        }
        map.put(0, 42);
        assertEquals(capacity, map.capacity());
        assertEquals(limit, map.size());
        assertEquals(42, map.get(0));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Таблица удваивает ёмкость и сохраняет записи")
    void shouldDoubleAndPreserveMappings(boolean quadratic) {
        HashMapCustom<Integer, Integer> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        int capacity = map.capacity();
        int count = capacity * 3 / 4 + 1;
        for (int key = 0; key < count; key++) {
            map.put(key, -key);
        }
        assertEquals(capacity * 2, map.capacity());
        for (int key = 0; key < count; key++) {
            assertEquals(-key, map.get(key));
        }
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Поиск проходит через маркер удаления")
    void shouldFindCollidingKeyBeyondDeletedSlot(boolean quadratic) {
        HashMapCustom<Integer, String> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        int capacity = map.capacity();
        map.put(0, "first");
        map.put(capacity, "second");
        assertTrue(map.remove(0));
        assertEquals("second", map.get(capacity));
        assertNull(map.get(0));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Ключ со значением null остаётся доступен")
    void shouldDistinguishNullValueFromMissingKey(boolean quadratic) {
        HashMapCustom<String, Integer> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        map.put("present", null);
        assertTrue(map.containsKey("present"));
        assertNull(map.get("present"));
        assertTrue(map.remove("present"));
        assertFalse(map.remove("present"));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Хеш минимального int даёт допустимый индекс")
    void shouldHashMinimumInteger(boolean quadratic) {
        HashMapCustom<Integer, Integer> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        map.put(Integer.MIN_VALUE, 1);
        assertEquals(1, map.get(Integer.MIN_VALUE));
    }
}
