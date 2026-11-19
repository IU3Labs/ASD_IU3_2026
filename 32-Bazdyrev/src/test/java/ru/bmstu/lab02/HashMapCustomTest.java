package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HashMapCustomTest {

    // Вспомогательный ключ с фиксированным hashCode для проверки коллизий
    private static class FixedHashKey {
        private final String name;
        private final int hash;

        FixedHashKey(String name, int hash) {
            this.name = name;
            this.hash = hash;
        }

        @Override
        public int hashCode() {
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof FixedHashKey)) {
                return false;
            }
            FixedHashKey other = (FixedHashKey) obj;
            return name.equals(other.name);
        }
    }

    // Новая пустая таблица с начальной ёмкостью 16
    @Test
    void shouldBeEmptyWithDefaultCapacityWhenCreated() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
        assertFalse(map.remove("a"));
    }

    // Базовая вставка и получение одного элемента
    @Test
    void shouldPutAndGetSingleElement() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("one", 1);

        assertEquals(1, map.size());
        assertTrue(map.containsKey("one"));
        assertEquals(1, map.get("one"));
    }

    // Повторная вставка по существующему ключу. Должна обновлять значение без роста size
    @Test
    void shouldUpdateValueWhenKeyAlreadyExists() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 10);
        map.put("key", 25);

        assertEquals(1, map.size());
        assertEquals(25, map.get("key"));
    }

    // Поиск и удаление отсутствующего ключа не меняют саму таблицу
    @Test
    void shouldReturnNullAndFalseForMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);

        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
        assertEquals(1, map.size());
    }

    // Вставка ключа null запрещена, а поиск/удаление null безопасны
    @Test
    void shouldHandleNullKeySafely() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertNull(map.get(null));
        assertFalse(map.containsKey(null));
        assertFalse(map.remove(null));
    }

    // Значение null должно корректно храниться и отличаться через containsKey
    @Test
    void shouldAllowNullValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("empty", null);

        assertEquals(1, map.size());
        assertTrue(map.containsKey("empty"));
        assertNull(map.get("empty"));
    }

    // Удаление существующего ключа уменьшает размер и убирает запись
    @Test
    void shouldRemoveElementAndDecreaseSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.put("b", 2);

        assertTrue(map.remove("a"));
        assertEquals(1, map.size());
        assertFalse(map.containsKey("a"));
        assertNull(map.get("a"));
        assertEquals(2, map.get("b"));
    }

    // Маркер DELETED не должен ломать поиск следующих элементов в цепочке коллизий
    @Test
    void shouldPreserveProbeChainAfterDeletion() {
        HashMapCustom<FixedHashKey, Integer> map = new HashMapCustom<>();
        FixedHashKey k1 = new FixedHashKey("k1", 5);
        FixedHashKey k2 = new FixedHashKey("k2", 5);
        FixedHashKey k3 = new FixedHashKey("k3", 5);

        map.put(k1, 1);
        map.put(k2, 2);
        map.put(k3, 3);

        assertTrue(map.remove(k1));
        assertFalse(map.containsKey(k1));
        assertEquals(2, map.get(k2));
        assertEquals(3, map.get(k3));
        assertEquals(2, map.size());
    }

    // Обновление элемента за маркером DELETED не должно создавать дубликат
    @Test
    void shouldUpdateElementAfterDeletedSlotWithoutDuplication() {
        HashMapCustom<FixedHashKey, Integer> map = new HashMapCustom<>();
        FixedHashKey k1 = new FixedHashKey("k1", 7);
        FixedHashKey k2 = new FixedHashKey("k2", 7);

        map.put(k1, 10);
        map.put(k2, 20);
        assertTrue(map.remove(k1));

        map.put(k2, 99);
        assertEquals(1, map.size());
        assertEquals(99, map.get(k2));
    }

    // При превышении загрузки выше, чем 0.75 (13-й элемент при ёмкости 16), ёмкость удваивается
    @Test
    void shouldDoubleCapacityWhenLoadFactorExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 12; i++) {
            map.put(i, i * 10);
        }
        assertEquals(16, map.capacity());

        map.put(12, 120);
        assertEquals(32, map.capacity());
        assertEquals(13, map.size());

        for (int i = 0; i < 13; i++) {
            assertEquals(i * 10, map.get(i));
        }
    }

    // Отрицательные хеш-коды и Integer.MIN_VALUE не должны вызывать ошибку индексации
    @Test
    void shouldHandleNegativeAndMinValueHashCodes() {
        HashMapCustom<FixedHashKey, String> map = new HashMapCustom<>();
        FixedHashKey neg = new FixedHashKey("neg", -42);
        FixedHashKey minInt = new FixedHashKey("min", Integer.MIN_VALUE);

        map.put(neg, "minus");
        map.put(minInt, "minValue");

        assertEquals("minus", map.get(neg));
        assertEquals("minValue", map.get(minInt));
    }

    // Проверка корректности при большом числе вставок, обновлений и удалений
    @Test
    void shouldHandleManyInsertionsAndRemovals() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        int count = 1000;
        for (int i = 0; i < count; i++) {
            map.put(i, "val_" + i);
        }
        assertEquals(count, map.size());

        for (int i = 0; i < count; i += 2) {
            assertTrue(map.remove(i));
        }
        assertEquals(count / 2, map.size());

        for (int i = 0; i < count; i++) {
            if (i % 2 == 0) {
                assertFalse(map.containsKey(i));
            } else {
                assertEquals("val_" + i, map.get(i));
            }
        }
    }
}