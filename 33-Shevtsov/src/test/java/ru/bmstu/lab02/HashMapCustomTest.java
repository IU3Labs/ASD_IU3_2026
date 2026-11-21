package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки хеш-таблицы с линейным пробированием. */
class HashMapCustomTest {

    private final HashMapCustom<Integer, String> table = new HashMapCustom<>();

    @Test
    void shouldStartEmpty() {
        assertEquals(0, table.size());
        assertEquals(16, table.capacity());
        assertNull(table.get(1));
        assertFalse(table.containsKey(1));
        assertFalse(table.remove(1));
    }

    @Test
    void shouldInsertAndFindValue() {
        table.put(1, "один");
        assertEquals("один", table.get(1));
        assertTrue(table.containsKey(1));
        assertEquals(1, table.size());
    }

    @Test
    void shouldUpdateAtLoadLimitWithoutGrowing() {
        for (int key = 0; key < 12; key++) {
            table.put(key, "старое");
        }
        table.put(5, "новое");
        assertEquals("новое", table.get(5));
        assertEquals(12, table.size());
        assertEquals(16, table.capacity());
    }

    @Test
    void shouldGrowWhenLoadLimitIsExceeded() {
        for (int key = 0; key < 12; key++) {
            table.put(key, "v" + key);
        }
        assertEquals(16, table.capacity());
        table.put(12, "v12");
        assertEquals(32, table.capacity());
        assertEquals(13, table.size());
        for (int key = 0; key <= 12; key++) {
            assertEquals("v" + key, table.get(key));
        }
    }

    @Test
    void shouldResolveCollisionsAcrossArrayBoundary() {
        table.put(15, "первый");
        table.put(31, "второй");
        table.put(47, "третий");
        assertEquals("первый", table.get(15));
        assertEquals("второй", table.get(31));
        assertEquals("третий", table.get(47));
        assertNull(table.get(63));
    }

    @Test
    void shouldFindKeysBeyondDeletedSlot() {
        table.put(1, "один");
        table.put(17, "семнадцать");
        assertTrue(table.remove(1));
        assertFalse(table.remove(1));
        assertFalse(table.containsKey(1));
        assertEquals("семнадцать", table.get(17));
        assertEquals(1, table.size());
    }

    @Test
    void shouldUpdateKeyBeyondDeletedSlotWithoutDuplicatingIt() {
        table.put(1, "временно");
        table.put(17, "старое");
        table.remove(1);
        table.put(17, "новое");
        assertEquals(1, table.size());
        assertEquals("новое", table.get(17));
        assertTrue(table.remove(17));
        assertFalse(table.containsKey(17));
    }

    @Test
    void shouldReuseFirstDeletedSlotInProbeChain() {
        table.put(1, "первый");
        table.put(17, "второй");
        table.remove(1);
        table.remove(17);
        table.put(33, "третий");
        assertEquals("третий", table.get(33));
        assertEquals(1, table.size());
    }

    @Test
    void shouldCleanDeletedSlotsWithoutGrowing() {
        for (int key = 0; key < 12; key++) {
            table.put(key, "v" + key);
        }
        for (int key = 0; key < 9; key++) {
            table.remove(key);
        }
        for (int key = 12; key <= 14; key++) {
            table.put(key, "v" + key);
        }
        assertEquals(16, table.capacity());
        assertEquals(6, table.size());
        for (int key = 9; key <= 14; key++) {
            assertEquals("v" + key, table.get(key));
        }
        assertFalse(table.containsKey(0));
    }

    @Test
    void shouldHandleNegativeAndMinimumHashCode() {
        table.put(-1, "отрицательный");
        table.put(Integer.MIN_VALUE, "минимальный");
        table.put(0, "нулевой");
        assertEquals("отрицательный", table.get(-1));
        assertEquals("минимальный", table.get(Integer.MIN_VALUE));
        assertEquals("нулевой", table.get(0));
    }

    @Test
    void shouldDistinguishMissingKeyFromNullValue() {
        table.put(1, null);
        assertNull(table.get(1));
        assertTrue(table.containsKey(1));
        assertFalse(table.containsKey(2));
        assertTrue(table.remove(1));
        assertFalse(table.containsKey(1));
    }

    @Test
    void shouldRejectNullKeys() {
        assertThrows(NullPointerException.class, () -> table.put(null, "значение"));
        assertThrows(NullPointerException.class, () -> table.get(null));
        assertThrows(NullPointerException.class, () -> table.containsKey(null));
        assertThrows(NullPointerException.class, () -> table.remove(null));
    }

    @Test
    void shouldMatchStandardMapOnMixedOperations() {
        Random random = new Random(42);
        HashMap<Integer, String> expected = new HashMap<>();
        for (int operation = 0; operation < 5_000; operation++) {
            int key = random.nextInt(500);
            switch (random.nextInt(3)) {
                case 0 -> {
                    String value = "v" + operation;
                    expected.put(key, value);
                    table.put(key, value);
                }
                case 1 -> assertEquals(expected.remove(key) != null, table.remove(key));
                default -> assertEquals(expected.get(key), table.get(key));
            }
            assertEquals(expected.size(), table.size());
        }
        expected.forEach((key, value) -> assertEquals(value, table.get(key)));
    }
}
