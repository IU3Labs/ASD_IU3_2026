package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.HashMapCustom.Probing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HashMapCustomTest {

    // ---------------------------------------------------------- базовые операции (оба вида пробирования)

    @Test
    void linearBasicOperations() {
        basicOperations(new HashMapCustom<>(Probing.LINEAR));
    }

    @Test
    void quadraticBasicOperations() {
        basicOperations(new HashMapCustom<>(Probing.QUADRATIC));
    }

    private void basicOperations(HashMapCustom<String, Integer> map) {
        assertEquals(0, map.size());
        assertNull(map.get("a"));
        assertFalse(map.containsKey("a"));
        map.put("a", 1);
        map.put("b", 2);
        assertEquals(2, map.size());
        assertEquals(1, map.get("a"));
        assertTrue(map.containsKey("b"));
        map.put("a", 10); // обновление
        assertEquals(2, map.size());
        assertEquals(10, map.get("a"));
        assertTrue(map.remove("a"));
        assertFalse(map.remove("a"));
        assertNull(map.get("a"));
        assertEquals(1, map.size());
    }

    @Test
    void removeFromEmptyAndMissing() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        assertFalse(map.remove(1));
        map.put(1, 1);
        assertFalse(map.remove(2));
    }

    @Test
    void nullKeyRejected() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
    }

    @Test
    void nullValueAllowed() {
        HashMapCustom<String, String> map = new HashMapCustom<>();
        map.put("k", null);
        assertTrue(map.containsKey("k"));
        assertNull(map.get("k"));
    }

    @Test
    void negativeAndMinValueHashCodes() {
        for (Probing p : Probing.values()) {
            HashMapCustom<Integer, String> map = new HashMapCustom<>(p);
            map.put(Integer.MIN_VALUE, "min");
            map.put(-5, "neg");
            map.put(Integer.MAX_VALUE, "max");
            assertEquals("min", map.get(Integer.MIN_VALUE));
            assertEquals("neg", map.get(-5));
            assertEquals("max", map.get(Integer.MAX_VALUE));
        }
    }

    // ---------------------------------------------------------- 2A: рост и порог 0.75

    @Test
    void capacityDoublesWhenLoadExceeded() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        assertEquals(16, map.capacity());
        for (int i = 0; i < 12; i++) { // 12 / 16 = 0.75 — ещё не превышает
            map.put(i, i);
        }
        assertEquals(16, map.capacity());
        map.put(12, 12);                // 13 / 16 > 0.75
        assertEquals(32, map.capacity());
    }

    @Test
    void loadNeverExceedsThreshold() {
        for (Probing p : Probing.values()) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(p);
            for (int i = 0; i < 10_000; i++) {
                map.put(i * 31, i);
                assertTrue(map.currentLoad() <= 0.75 + 1e-9);
            }
        }
    }

    @Test
    void allEntriesSurviveResizes() {
        for (Probing p : Probing.values()) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(p);
            for (int i = 0; i < 20_000; i++) {
                map.put(i, i * 2);
            }
            assertEquals(20_000, map.size());
            for (int i = 0; i < 20_000; i++) {
                assertEquals(i * 2, map.get(i));
            }
        }
    }

    @Test
    void deletedMarkerKeepsProbeChainIntact() {
        // ключи, различающиеся на capacity, попадают в одну ячейку и образуют цепочку
        HashMapCustom<Integer, String> map = new HashMapCustom<>(Probing.LINEAR, 0.75, 16);
        map.put(1, "a");
        map.put(17, "b");
        map.put(33, "c");
        assertTrue(map.remove(17));          // дыра в середине цепочки
        assertEquals("c", map.get(33));      // цепочка не должна «разорваться»
        assertTrue(map.containsKey(33));
        assertFalse(map.containsKey(17));
    }

    @Test
    void deletedSlotIsReused() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>(Probing.LINEAR, 0.75, 16);
        map.put(1, "a");
        map.put(17, "b");
        map.remove(1);
        map.put(33, "c");                    // должен занять освободившуюся ячейку
        map.put(17, "b2");                   // обновление, а не дубликат
        assertEquals(2, map.size());
        assertEquals("b2", map.get(17));
        assertEquals("c", map.get(33));
    }

    @Test
    void insertDeleteCyclesDoNotBreakTable() {
        for (Probing p : Probing.values()) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(p);
            for (int round = 0; round < 50; round++) {
                for (int i = 0; i < 100; i++) {
                    map.put(round * 1000 + i, i);
                }
                for (int i = 0; i < 100; i++) {
                    assertTrue(map.remove(round * 1000 + i));
                }
            }
            assertEquals(0, map.size());
            assertFalse(map.containsKey(5));
            map.put(5, 5);
            assertEquals(5, map.get(5));
        }
    }

    @Test
    void randomOperationsMatchJavaHashMap() {
        for (Probing p : Probing.values()) {
            Random rnd = new Random(11);
            HashMapCustom<Integer, Integer> mine = new HashMapCustom<>(p);
            Map<Integer, Integer> ref = new HashMap<>();
            for (int i = 0; i < 100_000; i++) {
                int key = rnd.nextInt(2000);
                switch (rnd.nextInt(3)) {
                    case 0 -> {
                        mine.put(key, i);
                        ref.put(key, i);
                    }
                    case 1 -> assertEquals(ref.remove(key) != null, mine.remove(key));
                    default -> assertEquals(ref.get(key), mine.get(key));
                }
                assertEquals(ref.size(), mine.size());
            }
            ref.forEach((k, v) -> assertEquals(v, mine.get(k)));
        }
    }

    @Test
    void forEachVisitsAllLiveEntries() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < 100; i++) {
            map.put(i, i);
        }
        for (int i = 0; i < 100; i += 2) {
            map.remove(i);
        }
        int[] count = {0};
        long[] sum = {0};
        map.forEach((k, v) -> {
            count[0]++;
            sum[0] += k;
        });
        assertEquals(50, count[0]);
        assertEquals(2500, sum[0]);
    }

    @Test
    void putIfAbsent() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertTrue(map.putIfAbsent("a", 1));
        assertFalse(map.putIfAbsent("a", 2));
        assertEquals(1, map.get("a"));
    }

    @Test
    void invalidConstructorArguments() {
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<>(Probing.LINEAR, 0.0));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<>(Probing.LINEAR, 1.0));
        assertThrows(IllegalArgumentException.class, () -> new HashMapCustom<>(Probing.LINEAR, 0.5, 0));
    }

    // ---------------------------------------------------------- 2B: квадратичное пробирование

    @Test
    void quadraticSequenceIsPermutationForPowerOfTwo() {
        // c1 = 1, c2 = 2: c1 + c2 нечётно => последовательность i + 2 i^2 mod 2^k — перестановка
        for (int cap = 1; cap <= 4096; cap <<= 1) {
            boolean[] seen = new boolean[cap];
            for (int i = 0; i < cap; i++) {
                long idx = ((long) i + 2L * i * i) % cap;
                assertFalse(seen[(int) idx], "cap=" + cap + " i=" + i);
                seen[(int) idx] = true;
            }
        }
    }

    @Test
    void quadraticWithPaperExampleCoefficientsStillCorrect() {
        // c1 = 1, c2 = 3 покрывает лишь часть ячеек; таблица должна всё равно работать без зацикливания
        HashMapCustom<Integer, Integer> map =
                new HashMapCustom<>(Probing.QUADRATIC, 0.75, 16, 1, 3);
        for (int i = 0; i < 5000; i++) {
            map.put(i * 64, i); // много коллизий по начальной ячейке
        }
        assertEquals(5000, map.size());
        for (int i = 0; i < 5000; i++) {
            assertEquals(i, map.get(i * 64));
        }
        assertNull(map.get(-1));
    }

    @Test
    void missingKeyLookupTerminates() {
        for (Probing p : Probing.values()) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(p, 0.9, 16);
            for (int i = 0; i < 1000; i++) {
                map.put(i, i);
            }
            for (int i = 1000; i < 2000; i++) {
                assertNull(map.get(i));
            }
        }
    }

    @Test
    void probeCounterWorks() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        map.put(1, 1);
        map.resetProbeCount();
        map.get(1);
        assertEquals(1, map.probeCount());
    }

    // ---------------------------------------------------------- 2C: искусственные коллизии

    @Test
    void collisionKeysLinear() {
        collisionKeys(Probing.LINEAR);
    }

    @Test
    void collisionKeysQuadratic() {
        collisionKeys(Probing.QUADRATIC);
    }

    private void collisionKeys(Probing probing) {
        int n = 10_000;
        HashMapCustom<Benchmarks.CollisionKey, Integer> map = new HashMapCustom<>(probing);
        for (int i = 0; i < n; i++) {
            map.put(new Benchmarks.CollisionKey("key" + i), i);
        }
        assertEquals(n, map.size());
        for (int i = 0; i < n; i++) {
            assertEquals(i, map.get(new Benchmarks.CollisionKey("key" + i)));
        }
        assertNull(map.get(new Benchmarks.CollisionKey("absent")));
        for (int i = 0; i < n; i += 2) {
            assertTrue(map.remove(new Benchmarks.CollisionKey("key" + i)));
        }
        assertEquals(n / 2, map.size());
        for (int i = 0; i < n; i++) {
            assertEquals(i % 2 == 0, map.get(new Benchmarks.CollisionKey("key" + i)) == null);
        }
        for (int i = 1; i < n; i += 2) {
            assertTrue(map.remove(new Benchmarks.CollisionKey("key" + i)));
        }
        assertEquals(0, map.size());
    }

    @Test
    void collisionKeyBehaviour() {
        assertEquals(new Benchmarks.CollisionKey("a").hashCode(), new Benchmarks.CollisionKey("b").hashCode());
        assertEquals(new Benchmarks.CollisionKey("a"), new Benchmarks.CollisionKey("a"));
        assertNotEquals(new Benchmarks.CollisionKey("a"), new Benchmarks.CollisionKey("b"));
    }

    // ---------------------------------------------------------- 2D: настраиваемый порог

    @Test
    void customLoadFactorsRespected() {
        for (double lf : new double[]{0.5, 0.75, 0.9}) {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>(Probing.LINEAR, lf);
            for (int i = 0; i < 10_000; i++) {
                map.put(i, i);
                assertTrue(map.currentLoad() <= lf + 1e-9, "lf=" + lf);
            }
            assertEquals(lf, map.loadFactorThreshold());
            for (int i = 0; i < 10_000; i++) {
                assertEquals(i, map.get(i));
            }
        }
    }

    @Test
    void lowerThresholdMeansLargerCapacity() {
        HashMapCustom<Integer, Integer> low = new HashMapCustom<>(Probing.LINEAR, 0.5);
        HashMapCustom<Integer, Integer> high = new HashMapCustom<>(Probing.LINEAR, 0.9);
        for (int i = 0; i < 50_000; i++) {
            low.put(i, i);
            high.put(i, i);
        }
        assertTrue(low.capacity() >= high.capacity());
    }

    @Test
    void defaultLoadFactorIs075() {
        assertEquals(0.75, new HashMapCustom<String, String>().loadFactorThreshold());
    }

    // ---------------------------------------------------------- 3B: частота слов

    @Test
    void countWordsIgnoresCaseAndPunctuation() {
        Map<String, Integer> m = HashMapCustom.countWords("Hello, hello! HELLO... world; World? foo-bar \"foo\"");
        assertEquals(3, m.get("hello"));
        assertEquals(2, m.get("world"));
        assertEquals(2, m.get("foo"));
        assertEquals(1, m.get("bar"));
        assertEquals(4, m.size());
    }

    @Test
    void countWordsRussianAndEmpty() {
        Map<String, Integer> m = HashMapCustom.countWords("Привет, ПРИВЕТ мир!");
        assertEquals(2, m.get("привет"));
        assertEquals(1, m.get("мир"));
        assertTrue(HashMapCustom.countWords("").isEmpty());
        assertTrue(HashMapCustom.countWords("  ,.;! ").isEmpty());
    }

    @Test
    void topWordsOrdering() {
        HashMapCustom<String, Integer> counts = HashMapCustom.countWordsCustom("b a b c c c a d");
        List<HashMapCustom.WordCount> top = HashMapCustom.topWords(counts, 3);
        assertEquals(List.of(new HashMapCustom.WordCount("c", 3), new HashMapCustom.WordCount("a", 2), new HashMapCustom.WordCount("b", 2)), top);
    }

    // ---------------------------------------------------------- 3C: distinct

    @Test
    void distinctKeepsFirstOccurrenceOrder() {
        assertEquals(List.of(3, 1, 2), HashMapCustom.distinct(Arrays.asList(3, 1, 3, 2, 1, 2, 3)));
    }

    @Test
    void distinctMatchesLinkedHashSet() {
        Random rnd = new Random(9);
        List<Integer> data = new ArrayList<>();
        for (int i = 0; i < 100_000; i++) {
            data.add(rnd.nextInt(5000));
        }
        assertEquals(new ArrayList<>(new LinkedHashSet<>(data)), HashMapCustom.distinct(data));
    }

    @Test
    void distinctHandlesNullAndEmpty() {
        assertEquals(Arrays.asList(1, null, 2), HashMapCustom.distinct(Arrays.asList(1, null, 2, null, 1)));
        assertTrue(HashMapCustom.distinct(new ArrayList<String>()).isEmpty());
    }

}
