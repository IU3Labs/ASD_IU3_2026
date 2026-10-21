package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task10LastDivisibleTest {
    @Test
    void shouldReturnLastIndex() { assertEquals(4, Task10LastDivisible.lastDivisibleIndex(new int[]{2, 3, 4, 5, 6}, 2)); }
    @Test
    void shouldReturnMinusOneIfNone() { assertEquals(-1, Task10LastDivisible.lastDivisibleIndex(new int[]{1, 3, 5}, 2)); }
    @Test
    void shouldHandleFirstOnly() { assertEquals(0, Task10LastDivisible.lastDivisibleIndex(new int[]{4, 1, 1}, 2)); }
}