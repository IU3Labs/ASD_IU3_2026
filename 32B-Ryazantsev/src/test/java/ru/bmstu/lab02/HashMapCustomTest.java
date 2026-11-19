package ru.bmstu.lab02;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет вставку, коллизии, удаление и расширение хеш-таблицы. */
class HashMapCustomTest {
    private final HashMapCustom<Integer, String> table = new HashMapCustom<>();

    @Test
    void shouldReportMissingKeysInEmptyTable() {
        assertEquals(0, table.size());
        assertNull(table.get(7));
        assertFalse(table.containsKey(7));
        assertFalse(table.remove(7));
    }

    @Test
    void shouldStoreAndFindAValue() {
        table.put(3, "три");
        assertEquals("три", table.get(3));
        assertTrue(table.containsKey(3));
        assertEquals(1, table.size());
    }

    @Test
    void shouldReplaceExistingValueWithoutGrowingAtLoadLimit() {
        for (int i = 0; i < 12; i++) {
            table.put(i, "старое");
        }
        table.put(3, "новое");
        assertEquals("новое", table.get(3));
        assertEquals(12, table.size());
        assertEquals(16, table.capacity());
    }

    @Test
    void shouldDoubleCapacityOnlyWhenLiveLoadExceedsThreshold() {
        for (int i = 0; i < 12; i++) {
            table.put(i, String.valueOf(i));
        }
        assertEquals(16, table.capacity());
        table.put(12, "12");
        assertEquals(32, table.capacity());
        for (int i = 0; i <= 12; i++) {
            assertEquals(String.valueOf(i), table.get(i));
        }
    }

    @Test
    void shouldFindCollidingKeysAfterWraparoundAndDeletion() {
        table.put(15, "первый");
        table.put(31, "второй");
        table.put(47, "третий");
        assertTrue(table.remove(15));
        assertEquals("второй", table.get(31));
        assertEquals("третий", table.get(47));
        assertFalse(table.containsKey(15));
    }

    @Test
    void shouldUpdateKeyBeyondTombstoneWithoutCreatingDuplicate() {
        table.put(1, "первый");
        table.put(17, "второй");
        table.remove(1);
        table.put(17, "обновление");
        assertEquals(1, table.size());
        assertEquals("обновление", table.get(17));
        assertTrue(table.remove(17));
        assertFalse(table.remove(17));
    }

    @Test
    void shouldReuseDeletedSlotForNewCollidingKey() {
        table.put(2, "два");
        table.put(18, "восемнадцать");
        table.remove(2);
        table.put(34, "тридцать четыре");
        assertEquals("тридцать четыре", table.get(34));
        assertEquals("восемнадцать", table.get(18));
        assertEquals(2, table.size());
    }

    @Test
    void shouldRehashTombstonesWithoutGrowingSparseTable() {
        table.put(0, "сохраняем");
        for (int i = 1; i < 16; i++) {
            table.put(i, "временно");
            assertTrue(table.remove(i));
        }
        assertEquals(16, table.capacity());
        assertEquals(1, table.size());
        assertEquals("сохраняем", table.get(0));
        assertFalse(table.containsKey(5));
    }

    @Test
    void shouldDifferentiateNullValueFromMissingKey() {
        table.put(5, null);
        assertNull(table.get(5));
        assertTrue(table.containsKey(5));
        assertTrue(table.remove(5));
        assertFalse(table.containsKey(5));
    }

    @Test
    void shouldHandleNegativeHashIncludingMinimumInt() {
        table.put(Integer.MIN_VALUE, "минимум");
        table.put(-7, "отрицательное");
        table.put(7, "положительное");
        assertEquals("минимум", table.get(Integer.MIN_VALUE));
        assertEquals("отрицательное", table.get(-7));
        assertEquals("положительное", table.get(7));
    }

    @Test
    void shouldRejectNullKeysForAllKeyOperations() {
        assertThrows(NullPointerException.class, () -> table.put(null, "значение"));
        assertThrows(NullPointerException.class, () -> table.get(null));
        assertThrows(NullPointerException.class, () -> table.containsKey(null));
        assertThrows(NullPointerException.class, () -> table.remove(null));
    }

    @Test
    void shouldMatchStandardMapDuringMixedOperations() {
        Random random = new Random(2320);
        HashMap<Integer, String> expected = new HashMap<>();
        for (int i = 0; i < 5000; i++) {
            int key = random.nextInt(200) - 100;
            switch (random.nextInt(3)) {
                case 0 -> {
                    String value = Integer.toString(i);
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
