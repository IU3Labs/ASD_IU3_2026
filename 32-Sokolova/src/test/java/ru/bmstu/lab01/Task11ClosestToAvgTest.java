package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task11ClosestToAvgTest {
    @Test
    void shouldReturnClosest() { assertEquals(4, Task11ClosestToAvg.closestToAvg(new int[]{1, 4, 7})); }
    @Test
    void shouldReturnFirstOnTie() { assertEquals(2, Task11ClosestToAvg.closestToAvg(new int[]{1, 2, 3, 4})); } // avg=2.5, |2-2.5|=0.5, |3-2.5|=0.5
    @Test
    void shouldHandleSingle() { assertEquals(10, Task11ClosestToAvg.closestToAvg(new int[]{10})); }
}