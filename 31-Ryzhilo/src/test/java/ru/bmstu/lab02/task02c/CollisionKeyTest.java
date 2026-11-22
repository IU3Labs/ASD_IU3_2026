package ru.bmstu.lab02.task02c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.bmstu.lab02.task02a.HashMapCustom;
import ru.bmstu.lab02.task02b.QuadraticHashMap;
import ru.bmstu.lab02.task02c.CollisionKey;

/** Проверяет решение задачи 02C. */
class CollisionKeyTest {
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Таблица обрабатывает 10 000 ключей с одинаковым хешем")
    void shouldHandleTenThousandCollidingKeys(boolean quadratic) {
        int count = 10_000;
        HashMapCustom<CollisionKey, Integer> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        for (int index = 0; index < count; index++) {
            map.put(new CollisionKey("key-" + index), index);
        }
        assertEquals(count, map.size());
        for (int index = 0; index < count; index++) {
            assertEquals(index, map.get(new CollisionKey("key-" + index)));
        }
        for (int index = 0; index < count; index++) {
            assertTrue(map.remove(new CollisionKey("key-" + index)));
        }
        assertEquals(0, map.size());
        assertNull(map.get(new CollisionKey("key-0")));
    }
}
