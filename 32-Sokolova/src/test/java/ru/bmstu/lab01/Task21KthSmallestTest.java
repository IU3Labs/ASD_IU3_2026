package ru.bmstu.lab01;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class Task21KthSmallestTest {
    @Test void shouldFindKth() { assertEquals(3, Task21KthSmallest.findKth(new int[]{5, 1, 4, 3, 2}, 3)); }
    @Test void shouldFindFirst() { assertEquals(1, Task21KthSmallest.findKth(new int[]{5, 1, 4}, 1)); }
    @Test void shouldFindLast() { assertEquals(5, Task21KthSmallest.findKth(new int[]{1, 2, 5}, 3)); }
}