package ru.bmstu.lab01.task_12;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import org.junit.jupiter.api.Test;

/**
 * Тесты для задачи 12. Пара с заданной суммой.
 */
class Task12Test {

    
    private final Task12 solver = new Task12();

    @Test
    void shouldReturnExamplePair() {
        int[] expected = {0, 1};
        int[] actual = solver.findPair(new int[] {2, 7, 11, 15, 4}, 9);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnNoPairWhenSumUnreachable() {
        int[] expected = {-1, -1};
        int[] actual = solver.findPair(new int[] {1, 2, 3, 4}, 100);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnNoPairForEmptyArray() {
        int[] expected = {-1, -1};
        int[] actual = solver.findPair(new int[0], 0);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldReturnNoPairForSingleElement() {
        int[] expected = {-1, -1};
        int[] actual = solver.findPair(new int[] {5}, 5);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] expected = {0, 1};
        int[] actual = solver.findPair(new int[] {-5, 2, -1, 4}, -3);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldChooseSmallestRightIndexWhenDuplicatesExist() {
        int[] expected = {0, 2};
        int[] actual = solver.findPair(new int[] {2, 1, 2, 3, 4}, 4);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldChooseSmallestLeftIndexWhenSeveralPairsExist() {
        int[] expected = {0, 3};
        int[] actual = solver.findPair(new int[] {1, 2, 3, 4, 4}, 5);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldNotUseSameElementTwice() {
        int[] expected = {0, 1};
        int[] actual = solver.findPair(new int[] {5, 5, 5, 5}, 10);
        assertArrayEquals(expected, actual);
    }

    @Test
    void shouldFindPairAtTail() {
        int[] expected = {3, 4};
        int[] actual = solver.findPair(new int[] {1, 2, 3, 4, 96}, 100);
        assertArrayEquals(expected, actual);
    }
    
    @Test
    void shouldHandleIntegerOverflowInSum() {
        int[] expected = {0, 1};
        int[] actual = solver.findPair(
                new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE},
                2L * Integer.MAX_VALUE);
        assertArrayEquals(expected, actual);
    }
}