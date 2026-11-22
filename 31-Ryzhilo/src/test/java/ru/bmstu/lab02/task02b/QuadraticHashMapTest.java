package ru.bmstu.lab02.task02b;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ru.bmstu.lab02.task02a.HashMapCustom;
import ru.bmstu.lab02.task02b.QuadraticHashMap;

/** Проверяет решение задачи 02B. */
class QuadraticHashMapTest {
    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Обновление ключа за маркером сохраняет единственную запись")
    void shouldUpdateBeforeReusingEarlierDeletedSlot(boolean quadratic) {
        HashMapCustom<Integer, String> map = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        int capacity = map.capacity();
        map.put(0, "first");
        map.put(capacity, "second");
        map.remove(0);
        map.put(capacity, "updated");
        assertEquals(1, map.size());
        map.put(2 * capacity, "new");
        assertEquals("updated", map.get(capacity));
        assertEquals("new", map.get(2 * capacity));
        assertEquals(1, map.getProbeCount(2 * capacity));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    @DisplayName("Смешанные операции совпадают с результатами HashMap")
    void shouldMatchStandardMapUnderMixedOperations(boolean quadratic) {
        Map<Integer, Integer> expected = new HashMap<>();
        HashMapCustom<Integer, Integer> actual = quadratic ? new QuadraticHashMap<>() : new HashMapCustom<>();
        Random random = new Random(42);
        for (int step = 0; step < 5_000; step++) {
            int key = random.nextInt(70) - 35;
            switch (random.nextInt(3)) {
                case 0 -> {
                    expected.put(key, step);
                    actual.put(key, step);
                }
                case 1 -> assertEquals(expected.remove(key) != null, actual.remove(key));
                default -> assertEquals(expected.get(key), actual.get(key));
            }
            assertEquals(expected.size(), actual.size());
        }
    }
}
