package ru.bmstu.lab02.task2a;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Тесты задачи 2A: операции таблицы, коллизии, маркер DELETED и рост ёмкости.
 */
class HashMapCustomTest {

    private static final long SEED = 25;

    @Test
    @DisplayName("Новая таблица пустая, ёмкость 16")
    void shouldBeEmptyWhenCreated() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
    }

    @Test
    @DisplayName("put и get возвращают сохранённые значения")
    void shouldPutAndGetValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("ИУ3", 3);
        map.put("группа", 32);

        assertEquals(3, map.get("ИУ3"));
        assertEquals(32, map.get("группа"));
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("put по существующему ключу обновляет значение")
    void shouldUpdateValueForExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("вариант", 1);
        map.put("вариант", 25);

        assertEquals(25, map.get("вариант"));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("containsKey различает добавленные и отсутствующие ключи")
    void shouldContainOnlyAddedKeys() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("cat", 1);

        assertTrue(map.containsKey("cat"));
        assertFalse(map.containsKey("dog"));
    }

    @Test
    @DisplayName("Значение null можно хранить")
    void shouldStoreNullValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("empty", null);

        assertTrue(map.containsKey("empty"));
        assertNull(map.get("empty"));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("remove удаляет существующий ключ")
    void shouldRemoveExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.put("b", 2);

        assertTrue(map.remove("a"));
        assertFalse(map.containsKey("a"));
        assertNull(map.get("a"));
        assertEquals(2, map.get("b"));
        assertEquals(1, map.size());
    }

    @Test
    @DisplayName("Повторное удаление и удаление отсутствующего ключа возвращают false")
    void shouldReturnFalseWhenRemovingMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);

        assertFalse(map.remove("b"));
        assertTrue(map.remove("a"));
        assertFalse(map.remove("a"));
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("Удаление из пустой таблицы возвращает false")
    void shouldReturnFalseWhenRemovingFromEmptyMap() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertFalse(map.remove("a"));
    }

    @Test
    @DisplayName("Ключ null запрещён во всех операциях")
    void shouldThrowWhenKeyIsNull() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();

        assertThrows(IllegalArgumentException.class, () -> map.put(null, 1));
        assertThrows(IllegalArgumentException.class, () -> map.get(null));
        assertThrows(IllegalArgumentException.class, () -> map.containsKey(null));
        assertThrows(IllegalArgumentException.class, () -> map.remove(null));
    }

    @Test
    @DisplayName("Ёмкость меньше 1 запрещена")
    void shouldThrowWhenCapacityIsNotPositive() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(0));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, Integer>(-25));
    }

    @Test
    @DisplayName("Таблица ёмкости 1 сама растёт при вставке")
    void shouldGrowFromCapacityOne() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>(1);
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, 30);

        assertEquals(30, map.get(3));
        assertEquals(3, map.size());
        assertTrue(map.size() <= map.capacity() * 0.75);
    }

    @Test
    @DisplayName("13-й ключ превышает загрузку 0.75 и удваивает ёмкость 16 → 32")
    void shouldDoubleCapacityWhenLoadFactorIsExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 12; i++) {
            map.put(i, i);
        }
        assertEquals(16, map.capacity());

        map.put(12, 12);

        assertEquals(32, map.capacity());
        assertEquals(13, map.size());
    }

    @Test
    @DisplayName("После многих ресайзов все 10 000 ключей на месте")
    void shouldKeepAllElementsAfterResize() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        for (int i = 0; i < 10000; i++) {
            map.put(i, "v" + i);
        }

        assertEquals(10000, map.size());
        for (int i = 0; i < 10000; i++) {
            assertEquals("v" + i, map.get(i));
        }
    }

    @Test
    @DisplayName("Ключ находится за ячейкой DELETED в цепочке пробирования")
    void shouldFindKeyBehindDeletedSlot() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(10);
        map.put(5, "a");
        map.put(15, "b");
        map.put(25, "c");

        assertTrue(map.remove(15));

        assertEquals("a", map.get(5));
        assertEquals("c", map.get(25));
        assertFalse(map.containsKey(15));
    }

    @Test
    @DisplayName("put после DELETED не создаёт дубликат ключа")
    void shouldNotDuplicateKeyWhenDeletedSlotIsBeforeIt() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(10);
        map.put(5, "a");
        map.put(15, "b");
        map.put(25, "c");
        map.remove(15);

        map.put(25, "new");

        assertEquals(2, map.size());
        assertEquals("new", map.get(25));
        assertTrue(map.remove(25));
        assertFalse(map.containsKey(25));
    }

    @Test
    @DisplayName("Пробирование переходит с конца массива в начало")
    void shouldWrapAroundEndOfTable() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(10);
        map.put(9, "a");
        map.put(19, "b");

        assertEquals("b", map.get(19));
        assertTrue(map.remove(9));
        assertEquals("b", map.get(19));
    }

    @Test
    @DisplayName("Отрицательный hashCode и Integer.MIN_VALUE не ломают индекс")
    void shouldWorkWithNegativeHashCodes() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(10);
        map.put(-25, "minus");
        map.put(Integer.MIN_VALUE, "min");

        assertEquals("minus", map.get(-25));
        assertEquals("min", map.get(Integer.MIN_VALUE));
        assertTrue(map.remove(Integer.MIN_VALUE));
        assertFalse(map.containsKey(Integer.MIN_VALUE));
    }

    @Test
    @DisplayName("Цикл put/remove не раздувает ёмкость")
    void shouldNotGrowWhenKeysAreAddedAndRemovedManyTimes() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
            assertTrue(map.remove(i));
        }
        map.put(25, 250);

        assertEquals(16, map.capacity());
        assertEquals(1, map.size());
        assertEquals(250, map.get(25));
    }

    @Test
    @DisplayName("20 000 случайных операций совпадают с java.util.HashMap")
    void shouldMatchJavaHashMapOnRandomOperations() {
        Random random = new Random(SEED);
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        Map<Integer, Integer> expected = new HashMap<>();
        for (int i = 0; i < 20000; i++) {
            int key = random.nextInt(1000);
            int operation = random.nextInt(3);
            if (operation == 0) {
                map.put(key, i);
                expected.put(key, i);
            } else if (operation == 1) {
                assertEquals(expected.remove(key) != null, map.remove(key));
            } else {
                assertEquals(expected.get(key), map.get(key));
                assertEquals(expected.containsKey(key), map.containsKey(key));
            }
        }

        assertEquals(expected.size(), map.size());
    }
}
