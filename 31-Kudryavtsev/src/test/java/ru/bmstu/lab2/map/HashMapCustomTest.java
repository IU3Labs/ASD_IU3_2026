package ru.bmstu.lab2.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверяет вставку, удаление, перехеширование и цепочки пробирования. */
class HashMapCustomTest {
    @Test
    void shouldStartEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertEquals(16, map.capacity());
        assertNull(map.get("missing"));
        assertFalse(map.containsKey("missing"));
        assertFalse(map.remove("missing"));
    }

    @Test
    void shouldPutAndGet() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 7);
        assertEquals(7, map.get(new String("key")));
        assertTrue(map.containsKey("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldUpdateWithoutChangingSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 7);
        map.put("key", 9);
        assertEquals(9, map.get("key"));
        assertEquals(1, map.size());
    }

    @Test
    void shouldDistinguishNullValueFromMissingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", null);
        assertNull(map.get("key"));
        assertTrue(map.containsKey("key"));
        assertTrue(map.remove("key"));
        assertFalse(map.containsKey("key"));
    }

    @Test
    void shouldRejectNullKeysForEveryOperation() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
        assertThrows(NullPointerException.class, () -> map.containsKey(null));
        assertThrows(NullPointerException.class, () -> map.remove(null));
        assertEquals(0, map.size());
    }

    @Test
    void shouldRemoveOnlyOnce() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("key", 1);
        assertTrue(map.remove("key"));
        assertFalse(map.remove("key"));
        assertNull(map.get("key"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldResizeOnlyWhenExceedingThreeQuarters() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 12; key++) {
            map.put(key, key);
        }
        assertEquals(16, map.capacity());
        map.put(0, -1);
        assertEquals(16, map.capacity());
        assertEquals(12, map.size());
        map.put(12, 12);
        assertEquals(32, map.capacity());
        assertEquals(-1, map.get(0));
        for (int key = 1; key <= 12; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldKeepAllEntriesAcrossManyResizes() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 10_000; key++) {
            map.put(key, -key);
            assertTrue(map.size() <= map.capacity() * 3 / 4);
        }
        for (int key = 0; key < 10_000; key++) {
            assertEquals(-key, map.get(key));
        }
        assertEquals(10_000, map.size());
    }

    @Test
    void shouldKeepCollisionChainAfterDeletingHeadAndMiddle() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        map.put(new CollisionKey("a"), 1);
        map.put(new CollisionKey("b"), 2);
        map.put(new CollisionKey("c"), 3);
        assertTrue(map.remove(new CollisionKey("a")));
        assertEquals(2, map.get(new CollisionKey("b")));
        assertEquals(3, map.get(new CollisionKey("c")));
        assertTrue(map.remove(new CollisionKey("b")));
        assertEquals(3, map.get(new CollisionKey("c")));
        assertNull(map.get(new CollisionKey("missing")));
    }

    @Test
    void shouldUpdateBeyondDeletedCellWithoutCreatingDuplicate() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        map.put(new CollisionKey("a"), 1);
        map.put(new CollisionKey("b"), 2);
        map.remove(new CollisionKey("a"));
        map.put(new CollisionKey("b"), 9);
        assertEquals(1, map.size());
        assertEquals(9, map.get(new CollisionKey("b")));
        assertTrue(map.remove(new CollisionKey("b")));
        assertFalse(map.containsKey(new CollisionKey("b")));
    }

    @Test
    void shouldReuseDeletedCells() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        map.put(new CollisionKey("a"), 1);
        map.remove(new CollisionKey("a"));
        map.put(new CollisionKey("b"), 2);
        map.put(new CollisionKey("a"), 3);
        assertEquals(2, map.size());
        assertEquals(2, map.get(new CollisionKey("b")));
        assertEquals(3, map.get(new CollisionKey("a")));
    }

    @Test
    void shouldWrapProbingAroundArrayEnd() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        map.put(15, 1);
        map.put(31, 2);
        map.put(47, 3);
        map.remove(31);
        assertEquals(3, map.get(47));
        assertEquals(1, map.get(15));
        assertNull(map.get(63));
    }

    @Test
    void shouldHandleNegativeHashesAndMinValue() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(Integer.MIN_VALUE, "min");
        map.put(-17, "negative");
        map.put(Integer.MAX_VALUE, "max");
        assertEquals("min", map.get(Integer.MIN_VALUE));
        assertEquals("negative", map.get(-17));
        assertEquals("max", map.get(Integer.MAX_VALUE));
    }

    @Test
    void shouldFinishSearchWhenEveryCellIsDeleted() {
        assertTimeoutPreemptively(Duration.ofSeconds(2), () -> {
            HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
            for (int key = 0; key < 16; key++) {
                map.put(key, key);
                map.remove(key);
            }
            assertNull(map.get(100));
            assertFalse(map.containsKey(100));
            assertFalse(map.remove(100));
            map.put(100, 7);
            assertEquals(7, map.get(100));
        });
    }

    @Test
    void shouldDiscardDeletedMarkersDuringResize() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int key = 0; key < 12; key++) {
            map.put(key, key);
        }
        map.remove(0);
        for (int key = 12; key < 15; key++) {
            map.put(key, key);
        }
        assertEquals(32, map.capacity());
        assertEquals(14, map.size());
        assertNull(map.get(0));
        for (int key = 1; key < 15; key++) {
            assertEquals(key, map.get(key));
        }
    }

    @Test
    void shouldHandleTenThousandCollidingKeysWithoutLoss() {
        HashMapCustom<CollisionKey, Integer> map = new HashMapCustom<>();
        int count = 10_000;
        for (int key = 0; key < count; key++) {
            map.put(new CollisionKey("key-" + key), key);
        }
        assertEquals(count, map.size());
        for (int key = 0; key < count; key++) {
            assertEquals(key, map.get(new CollisionKey("key-" + key)));
        }
        for (int key = 0; key < count; key += 2) {
            assertTrue(map.remove(new CollisionKey("key-" + key)));
        }
        for (int key = 0; key < count; key++) {
            if (key % 2 == 0) {
                assertFalse(map.containsKey(new CollisionKey("key-" + key)));
            } else {
                assertEquals(key, map.get(new CollisionKey("key-" + key)));
                assertTrue(map.remove(new CollisionKey("key-" + key)));
            }
        }
        assertEquals(0, map.size());
    }

    @Test
    void shouldMatchHashMapForRandomMixedOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(2026);
        for (int step = 0; step < 30_000; step++) {
            int key = random.nextInt(500) - 250;
            switch (random.nextInt(4)) {
                case 0 -> {
                    Integer value = step % 7 == 0 ? null : step;
                    actual.put(key, value);
                    expected.put(key, value);
                }
                case 1 -> {
                    boolean existed = expected.containsKey(key);
                    expected.remove(key);
                    assertEquals(existed, actual.remove(key));
                }
                case 2 -> assertEquals(expected.get(key), actual.get(key));
                default -> assertEquals(expected.containsKey(key), actual.containsKey(key));
            }
            assertEquals(expected.size(), actual.size());
        }
        for (int key = -250; key < 250; key++) {
            assertEquals(expected.containsKey(key), actual.containsKey(key));
            assertEquals(expected.get(key), actual.get(key));
        }
    }
}
