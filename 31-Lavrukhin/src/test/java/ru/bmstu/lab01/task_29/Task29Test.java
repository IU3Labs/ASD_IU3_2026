package ru.bmstu.lab01.task_29;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import ru.bmstu.lab01.util.ConsoleHelper;

class Task29Test {

    @Test
    void shouldFindMostFrequentElement() {
        assertEquals(4, Task29.findMode(new int[] {1, 4, 2, 4, 3, 4, 1}));
    }

    @Test
    void shouldChooseSmallestWhenFrequenciesAreEqual() {
        // 7 и -2 встречаются по два раза, ответ — меньшее
        assertEquals(-2, Task29.findMode(new int[] {7, -2, 7, 5, -2}));
    }

    @Test
    void shouldReturnSmallestWhenAllElementsAreDifferent() {
        assertEquals(-8, Task29.findMode(new int[] {3, 10, -8, 0}));
    }

    @Test
    void shouldWorkForOneElement() {
        assertEquals(42, Task29.findMode(new int[] {42}));
    }

    @Test
    void shouldFindModeAtTheEndOfSortedArray() {
        assertEquals(9, Task29.findMode(new int[] {9, 1, 9, 2, 9}));
    }

    @Test
    void shouldNotChangeSourceArray() {
        int[] array = {3, 1, 2};

        Task29.findMode(array);

        assertArrayEquals(new int[] {3, 1, 2}, array);
    }

    @Test
    void shouldThrowForEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> Task29.findMode(new int[0]));
    }

    @Test
    void shouldHandleMaxSizeArray() {
        int n = 10_000;
        int[] array = new int[n];
        for (int i = 0; i < n; i++) {
            array[i] = i % 100;
        }
        // все числа от 0 до 99 встречаются по 100 раз, значит ответ 0
        assertEquals(0, Task29.findMode(array));
    }

    @Test
    void shouldReadInputAndPrintMode() {
        String output = ConsoleHelper.run(() -> Task29.main(new String[0]), "5\n7 -2 7 5 -2\n");

        assertEquals("-2", output);
    }
}
