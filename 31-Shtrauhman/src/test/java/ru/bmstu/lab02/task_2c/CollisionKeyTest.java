package ru.bmstu.lab02.task_2c;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab02.task_2a.HashMapCustom;

/**
 * Тесты задачи 2C: таблица из задачи 2A на 10 000 ключах с одинаковым хеш-кодом.
 */
class CollisionKeyTest {

    private static final int KEY_COUNT = 10_000;

    @Test
    void shouldReturnSameHashCodeForDifferentStrings() {
        assertEquals(new CollisionKey("a").hashCode(), new CollisionKey("completely different").hashCode());
        assertEquals(CollisionKey.CONSTANT_HASH, new CollisionKey("").hashCode());
    }

    @Test
    void shouldCompareByWrappedString() {
        CollisionKey key = new CollisionKey("x");

        assertEquals(new CollisionKey("x"), key);
        assertEquals(key, key);
        assertNotEquals(new CollisionKey("y"), key);
        // Вызываем equals именно у CollisionKey: assertNotEquals("x", key) проверил бы String.equals.
        assertFalse(key.equals("x"));
        assertFalse(key.equals(null));
        assertEquals("x", key.getValue());
        assertEquals("CollisionKey[x]", key.toString());
    }

    @Test
    void shouldRejectNullString() {
        assertThrows(NullPointerException.class, () -> new CollisionKey(null));
    }

    @Test
    void shouldInsertAllCollidingKeysWithoutLoss() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);

        assertEquals(KEY_COUNT, map.size());
        for (int i = 0; i < KEY_COUNT; i++) {
            assertEquals(i, map.get(key(i)));
        }
    }

    @Test
    void shouldKeepLoadFactorWithCollidingKeys() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);

        assertTrue(map.size() <= map.capacity() * 0.75);
    }

    @Test
    void shouldNotFindAbsentCollidingKey() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);

        assertFalse(map.containsKey(key(KEY_COUNT)));
        assertNull(map.get(new CollisionKey("absent")));
    }

    @Test
    void shouldUpdateCollidingKeysWithoutDuplicates() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);
        for (int i = 0; i < KEY_COUNT; i++) {
            map.put(key(i), -i);
        }

        assertEquals(KEY_COUNT, map.size());
        assertEquals(-(KEY_COUNT - 1), map.get(key(KEY_COUNT - 1)));
    }

    @Test
    void shouldRemoveAllCollidingKeys() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);
        for (int i = 0; i < KEY_COUNT; i++) {
            assertTrue(map.remove(key(i)));
        }

        assertEquals(0, map.size());
        assertFalse(map.containsKey(key(0)));
    }

    @Test
    void shouldKeepRemainingKeysAfterRemovingEveryOther() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);
        for (int i = 0; i < KEY_COUNT; i += 2) {
            map.remove(key(i));
        }

        assertEquals(KEY_COUNT / 2, map.size());
        for (int i = 0; i < KEY_COUNT; i++) {
            assertEquals(i % 2 == 1, map.containsKey(key(i)));
        }
    }

    @Test
    void shouldReinsertKeysAfterRemoval() {
        HashMapCustom<CollisionKey, Integer> map = filledMap(KEY_COUNT);
        for (int i = 0; i < KEY_COUNT; i++) {
            map.remove(key(i));
        }
        for (int i = 0; i < KEY_COUNT; i++) {
            map.put(key(i), i * 2);
        }

        assertEquals(KEY_COUNT, map.size());
        assertEquals(2 * (KEY_COUNT - 1), map.get(key(KEY_COUNT - 1)));
    }

    private static CollisionKey key(int index) {
        return new CollisionKey("key-" + index);
    }

    private static HashMapCustom<CollisionKey, Integer> filledMap(int count) {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        for (int i = 0; i < count; i++) {
            map.put(key(i), i);
        }
        return map;
    }
}
