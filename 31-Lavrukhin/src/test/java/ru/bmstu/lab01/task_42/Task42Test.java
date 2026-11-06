package ru.bmstu.lab01.task_42;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task42Test {

    @Test
    void shouldInsertIntoMiddle() {
        assertArrayEquals(new int[] {1, 3, 4, 7}, Task42.insert(new int[] {1, 3, 7}, 4));
    }

    @Test
    void shouldInsertAtBeginning() {
        assertArrayEquals(new int[] {-5, 1, 2}, Task42.insert(new int[] {1, 2}, -5));
    }

    @Test
    void shouldInsertAtEnd() {
        assertArrayEquals(new int[] {1, 2, 100}, Task42.insert(new int[] {1, 2}, 100));
    }

    @Test
    void shouldInsertIntoEmptyArray() {
        assertArrayEquals(new int[] {8}, Task42.insert(new int[0], 8));
    }

    @Test
    void shouldInsertAfterEqualElements() {
        assertEquals(4, Task42.findPosition(new int[] {1, 2, 2, 2, 5}, 2));
        assertArrayEquals(new int[] {1, 2, 2, 2, 2, 5}, Task42.insert(new int[] {1, 2, 2, 2, 5}, 2));
    }

    @Test
    void shouldFindPositionForEveryPlace() {
        int[] sorted = {10, 20, 30};

        assertEquals(0, Task42.findPosition(sorted, 5));
        assertEquals(1, Task42.findPosition(sorted, 15));
        assertEquals(2, Task42.findPosition(sorted, 25));
        assertEquals(3, Task42.findPosition(sorted, 35));
    }

    @Test
    void shouldNotChangeSourceArray() {
        int[] sorted = {1, 2, 3};

        Task42.insert(sorted, 0);

        assertArrayEquals(new int[] {1, 2, 3}, sorted);
    }

    @Test
    void shouldThrowWhenArrayIsNotSorted() {
        assertThrows(IllegalArgumentException.class, () -> Task42.insert(new int[] {3, 1, 2}, 0));
    }

    @Test
    void shouldReadInputAndPrintNewArray() {
        String output = ConsoleHelper.run(() -> Task42.main(new String[0]), "3\n1 3 7\n4\n");

        assertEquals("1 3 4 7", output);
    }
}
