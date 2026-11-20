package ru.bmstu.lab2.task_2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/** Задача 2C: десять тысяч разных ключей с одним хешем не должны терять записи. */
class CollisionKeyTest {
    private static final int COLLISION_COUNT = 10_000;

    @Test
    void shouldUseStringEqualityAndConstantHash() {
        CollisionKey first = new CollisionKey("alpha");
        CollisionKey same = new CollisionKey(new String("alpha"));
        CollisionKey different = new CollisionKey("beta");
        assertEquals(first, same);
        assertNotEquals(first, different);
        assertEquals(first.hashCode(), different.hashCode());
        assertEquals("alpha", first.value());
    }

    @Test
    void shouldRejectNullString() {
        assertThrows(NullPointerException.class, () -> new CollisionKey(null));
    }

    @Test
    void shouldStoreFindAndRemoveTenThousandCollidingKeys() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        for (int index = 0; index < COLLISION_COUNT; index++) {
            map.put(key(index), index);
        }
        assertEquals(COLLISION_COUNT, map.size());
        for (int index = 0; index < COLLISION_COUNT; index++) {
            assertEquals(index, map.get(key(index)));
        }
        for (int index = 0; index < COLLISION_COUNT; index += 2) {
            assertTrue(map.remove(key(index)));
        }
        verifyAlternatingDeletion(map);
        for (int index = 1; index < COLLISION_COUNT; index += 2) {
            assertTrue(map.remove(key(index)));
        }
        assertEquals(0, map.size());
        assertFalse(map.containsKey(key(COLLISION_COUNT)));
    }

    private static void verifyAlternatingDeletion(HashMapCustom<CollisionKey, Integer> map) {
        for (int index = 0; index < COLLISION_COUNT; index++) {
            if (index % 2 == 0) {
                assertNull(map.get(key(index)));
            } else {
                assertEquals(index, map.get(key(index)));
            }
        }
    }

    private static CollisionKey key(int index) {
        return new CollisionKey("key-" + index);
    }
}
