package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task07SecondMaxTest {
    @Test
    void shouldReturnSecondLargest() { assertEquals(7, Task07SecondMax.secondMax(new int[]{3, 9, 1, 7, 5})); }
    @Test
    void shouldHandleTwoElements() { assertEquals(-5, Task07SecondMax.secondMax(new int[]{-2, -5})); }
    @Test
    void shouldReturnMinIntIfOnlyOne() { assertEquals(Integer.MIN_VALUE, Task07SecondMax.secondMax(new int[]{42})); }
}