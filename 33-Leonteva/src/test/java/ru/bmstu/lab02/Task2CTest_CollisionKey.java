package ru.bmstu.lab02;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task2CTest_CollisionKey {
    @Test void shouldHaveConstantHash() {
        assertEquals(new Task2C_CollisionKey("a").hashCode(), new Task2C_CollisionKey("b").hashCode());
    }
    @Test void shouldBeEqualForEqualValues() {
        assertEquals(new Task2C_CollisionKey("abc"), new Task2C_CollisionKey("abc"));
    }
    @Test void shouldBeDifferentForDifferentValues() {
        assertNotEquals(new Task2C_CollisionKey("a"), new Task2C_CollisionKey("b"));
    }
    @Test void shouldHaveEqualHashForEqualValues() {
        assertEquals(new Task2C_CollisionKey("x").hashCode(), new Task2C_CollisionKey("x").hashCode());
    }
    @Test void shouldEqualItself() {
        var key = new Task2C_CollisionKey("a");
        assertEquals(key, key);
    }
    @Test void shouldNotEqualNull() {
        assertNotEquals(null, new Task2C_CollisionKey("a"));
    }
    @Test void shouldNotEqualString() {
        assertNotEquals("a", new Task2C_CollisionKey("a"));
    }
    @Test void shouldReturnStringValue() {
        assertEquals("abc", new Task2C_CollisionKey("abc").toString());
    }
    @Test void shouldRejectNullString() {
        assertThrows(NullPointerException.class, () -> new Task2C_CollisionKey(null));
    }
    @Test void shouldHandleEmptyString() {
        assertEquals(new Task2C_CollisionKey(""), new Task2C_CollisionKey(""));
    }
    @Test void shouldHandleTenThousandCollisions() {
        var map = new Task2A_HashMap<Task2C_CollisionKey, Integer>();
        for (int i = 0; i < 10000; i++) map.put(new Task2C_CollisionKey("key" + i), i);
        assertEquals(10000, map.size());
        for (int i = 0; i < 10000; i++) assertEquals(i, map.get(new Task2C_CollisionKey("key" + i)).intValue());
        for (int i = 0; i < 10000; i++) assertTrue(map.remove(new Task2C_CollisionKey("key" + i)));
        assertEquals(0, map.size());
    }
}
