package ru.bmstu.lab02.task02d;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task02a.HashMapCustom;
import ru.bmstu.lab02.task02d.LoadThresholdHashMap;

/** Проверяет решение задачи 02D. */
class LoadThresholdHashMapTest {
    @Test
    @DisplayName("Порог по умолчанию равен 0,75")
    void shouldGrowAtDefaultLoadThreshold() {
        HashMapCustom<Integer, Integer> map = new LoadThresholdHashMap<>();
        int capacity = map.capacity();
        for (int key = 0; key < capacity * 3 / 4; key++) {
            map.put(key, key);
        }
        assertEquals(capacity, map.capacity());
        map.put(capacity, capacity);
        assertEquals(2 * capacity, map.capacity());
    }

    @Test
    @DisplayName("Таблица растёт при заданном пороге загрузки")
    void shouldUseConfiguredLoadThreshold() {
        HashMapCustom<Integer, Integer> map = new LoadThresholdHashMap<>(0.5);
        int capacity = map.capacity();
        for (int key = 0; key <= capacity / 2; key++) {
            map.put(key, key);
        }
        assertEquals(capacity * 2, map.capacity());
    }

    @Test
    @DisplayName("Некорректные пороги загрузки отклоняются")
    void shouldRejectInvalidLoadThresholds() {
        assertThrows(IllegalArgumentException.class, () -> new LoadThresholdHashMap<>(0.0));
        assertThrows(IllegalArgumentException.class, () -> new LoadThresholdHashMap<>(1.0));
        assertThrows(IllegalArgumentException.class, () -> new LoadThresholdHashMap<>(Double.NaN));
    }
}
