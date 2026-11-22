package ru.bmstu.lab2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Random;
import org.junit.jupiter.api.Test;

/** Проверки линейного пробирования, коллизий и удаления. */
class HashMapCustomTest {
    private record BadHash(int number) {
        @Override
        public int hashCode() {
            return 7;
        }
    }

    @Test
    void shouldStartEmpty() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertEquals(0, map.size());
        assertTrue(map.capacity() > 0);
    }

    @Test
    void shouldPutAndGetValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        assertEquals(1, map.get("a"));
    }

    @Test
    void shouldUpdateExistingKeyWithoutGrowingSize() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        map.put("a", 2);
        assertEquals(1, map.size());
        assertEquals(2, map.get("a"));
    }

    @Test
    void shouldReturnNullForMissingKey() {
        assertNull(new HashMapCustom<String, Integer>().get("none"));
    }

    @Test
    void shouldDistinguishPresentNullValue() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", null);
        assertTrue(map.containsKey("a"));
        assertNull(map.get("a"));
    }

    @Test
    void shouldRemoveExistingKey() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        map.put("a", 1);
        assertTrue(map.remove("a"));
        assertFalse(map.containsKey("a"));
        assertEquals(0, map.size());
    }

    @Test
    void shouldReturnFalseWhenRemovingMissingKey() {
        assertFalse(new HashMapCustom<String, Integer>().remove("a"));
    }

    @Test
    void shouldKeepCollisionChainAfterDelete() {
        HashMapCustom<BadHash, Integer> map = new HashMapCustom<>();
        for (int index = 0; index < 6; index++) {
            map.put(new BadHash(index), index);
        }
        assertTrue(map.remove(new BadHash(2)));
        assertEquals(5, map.get(new BadHash(5)));
        assertFalse(map.containsKey(new BadHash(2)));
    }

    @Test
    void shouldReuseDeletedSlots() {
        HashMapCustom<BadHash, Integer> map = new HashMapCustom<>();
        for (int index = 0; index < 6; index++) {
            map.put(new BadHash(index), index);
        }
        map.remove(new BadHash(1));
        map.put(new BadHash(99), 99);
        assertEquals(99, map.get(new BadHash(99)));
        assertEquals(6, map.size());
    }

    @Test
    void shouldGrowAndRehashWithoutLosingData() {
        HashMapCustom<Integer, Integer> map = new HashMapCustom<>();
        for (int index = 0; index < 3000; index++) {
            map.put(index, index * 2);
        }
        assertTrue(map.capacity() > 16);
        for (int index = 0; index < 3000; index++) {
            assertEquals(index * 2, map.get(index));
        }
    }

    @Test
    void shouldHandleNegativeHashIncludingMinimumInteger() {
        HashMapCustom<Integer, String> map = new HashMapCustom<>();
        map.put(Integer.MIN_VALUE, "minimum");
        assertEquals("minimum", map.get(Integer.MIN_VALUE));
    }

    @Test
    void shouldRejectNullKeys() {
        HashMapCustom<String, Integer> map = new HashMapCustom<>();
        assertThrows(NullPointerException.class, () -> map.put(null, 1));
        assertThrows(NullPointerException.class, () -> map.get(null));
    }

    @Test
    void shouldMatchStandardMapInRandomOperations() {
        HashMapCustom<Integer, Integer> actual = new HashMapCustom<>();
        HashMap<Integer, Integer> expected = new HashMap<>();
        Random random = new Random(31);
        for (int index = 0; index < 10_000; index++) {
            int key = random.nextInt(300);
            int operation = random.nextInt(3);
            if (operation == 0) {
                int value = random.nextInt();
                actual.put(key, value);
                expected.put(key, value);
            } else if (operation == 1) {
                assertEquals(expected.remove(key) != null, actual.remove(key));
            } else {
                assertEquals(expected.get(key), actual.get(key));
                assertEquals(expected.containsKey(key), actual.containsKey(key));
            }
            assertEquals(expected.size(), actual.size());
        }
    }
}
