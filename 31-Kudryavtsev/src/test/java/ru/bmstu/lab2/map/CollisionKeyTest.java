package ru.bmstu.lab2.map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/** Проверяет контракт equals/hashCode искусственных ключей. */
class CollisionKeyTest {
    @Test
    void shouldCompareStringsAndKeepHashConstant() {
        CollisionKey first = new CollisionKey("one");
        CollisionKey same = new CollisionKey("one");
        CollisionKey other = new CollisionKey("two");
        assertEquals(first, first);
        assertEquals(first, same);
        assertEquals(same, first);
        assertNotEquals(first, other);
        assertNotEquals(first, null);
        assertNotEquals(first, "one");
        assertEquals(first.hashCode(), other.hashCode());
        assertEquals(1, first.hashCode());
        assertEquals("one", first.toString());
        assertEquals("", new CollisionKey("").toString());
        assertThrows(NullPointerException.class, () -> new CollisionKey(null));
    }
}
