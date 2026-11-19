package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.HashMapCustom.Probing;

/** Настройки таблицы (2D) и статистика проб (2B). */
class HashMapCustomConfigurationTest {

    @Test
    void shouldUseLinearProbingAndDefaultLoadFactorByDefault() {
        HashMapCustom<String, String> map = new HashMapCustom<>();

        assertEquals(Probing.LINEAR, map.probing());
        assertEquals(0.75, map.loadFactor());
        assertEquals(16, map.capacity());
    }

    @Test
    void shouldRoundInitialCapacityUpToPowerOfTwo() {
        assertEquals(64, new HashMapCustom<String, String>(Probing.LINEAR, 33, 0.75).capacity());
        assertEquals(4, new HashMapCustom<String, String>(Probing.LINEAR, 1, 0.75).capacity());
    }

    @Test
    void shouldRejectInvalidLoadFactor() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, String>(Probing.LINEAR, 16, 0));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, String>(Probing.LINEAR, 16, 1));
        assertThrows(IllegalArgumentException.class,
                () -> new HashMapCustom<String, String>(Probing.LINEAR, 16, Double.NaN));
    }

    @Test
    void shouldRejectInvalidCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<String, String>(Probing.LINEAR, 0, 0.5));
    }

    @Test
    void shouldGrowEarlierWithLowerLoadFactor() {
        HashMapCustom<Integer, Integer> sparse = new HashMapCustom<>(Probing.LINEAR, 16, 0.5);
        HashMapCustom<Integer, Integer> dense = new HashMapCustom<>(Probing.LINEAR, 16, 0.9);

        for (int i = 0; i < 900; i++) {
            sparse.put(i, i);
            dense.put(i, i);
        }

        assertEquals(900, sparse.size());
        assertEquals(900, dense.size());
        assertEquals(2048, sparse.capacity());
        assertEquals(1024, dense.capacity());
    }

    @Test
    void shouldCountProbesOfLookupsOnly() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        map.put(1, 1);
        assertEquals(0.0, map.averageLookupProbes());

        map.get(1);

        assertEquals(1.0, map.averageLookupProbes());
    }

    @Test
    void shouldNeedMoreProbesForCollidingKeys() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 10; i++) {
            map.put(new CollisionKey("k" + i), i);
        }
        map.resetProbeStatistics();

        map.get(new CollisionKey("k9"));

        assertEquals(10.0, map.averageLookupProbes());
    }

    @Test
    void shouldResetProbeStatistics() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        map.put(1, 1);
        map.get(1);

        map.resetProbeStatistics();

        assertEquals(0.0, map.averageLookupProbes());
    }
}
