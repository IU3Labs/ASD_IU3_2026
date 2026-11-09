package ru.bmstu.lab01.task_12;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.TestUtils;

class Task12Test {

    @Test
    void shouldFindPair() {
        assertArrayEquals(new int[] {0, 1}, Task12.findPair(new int[] {2, 7, 11, 15}, 9));
    }

    @Test
    void shouldChooseSmallestI() {
        // пара (2, 3) находится раньше, но у (0, 4) меньше i
        assertArrayEquals(new int[] {0, 4}, Task12.findPair(new int[] {1, 0, 4, 6, 9}, 10));
    }

    @Test
    void shouldChooseSmallestJForSameI() {
        assertArrayEquals(new int[] {0, 1}, Task12.findPair(new int[] {5, 5, 5}, 10));
    }

    @Test
    void shouldNotUseElementTwice() {
        assertArrayEquals(new int[] {-1, -1}, Task12.findPair(new int[] {5, 1, 2}, 10));
    }

    @Test
    void shouldPrintAnswer() {
        assertEquals("1 3", TestUtils.runMain(Task12::main, "4\n3 -1 4 2\n1"));
    }
}
