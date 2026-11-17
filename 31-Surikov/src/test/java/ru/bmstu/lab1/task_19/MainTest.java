package ru.bmstu.lab1.task_19;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleSupport;

/** граничные случаи и независимая проверка алгоритма задачи 19 */
class MainTest {
    @Test
    void shouldMergeSortedArraysWithoutChangingInputs() {
        int[] first = {Integer.MIN_VALUE, -2, 3, 3};
        int[] second = {-2, 0, Integer.MAX_VALUE};
        int[] originalFirst = first.clone();
        int[] originalSecond = second.clone();
        assertArrayEquals(new int[] {Integer.MIN_VALUE, -2, -2, 0, 3, 3, Integer.MAX_VALUE},
                Main.merge(first, second));
        assertArrayEquals(originalFirst, first);
        assertArrayEquals(originalSecond, second);
    }

    @Test
    void shouldMergeEmptyArraysAndRemainingTails() {
        assertArrayEquals(new int[0], Main.merge(new int[0], new int[0]));
        assertArrayEquals(new int[] {1, 2}, Main.merge(new int[] {1, 2}, new int[0]));
        assertArrayEquals(new int[] {1, 2}, Main.merge(new int[0], new int[] {1, 2}));
        assertArrayEquals(new int[] {1, 2, 3, 4}, Main.merge(new int[] {3, 4}, new int[] {1, 2}));
    }

    @Test
    void shouldMatchReferenceOnRandomArrays() {
        Random random = new Random(19);
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] first = random.ints(random.nextInt(50), -20, 21).sorted().toArray();
            int[] second = random.ints(random.nextInt(50), -20, 21).sorted().toArray();
            int[] expected = new int[first.length + second.length];
            System.arraycopy(first, 0, expected, 0, first.length);
            System.arraycopy(second, 0, expected, first.length, second.length);
            Arrays.sort(expected);
            assertArrayEquals(expected, Main.merge(first, second));
        }
    }

    @Test
    void shouldReadAndPrintAnswer() throws Exception {
        assertEquals("1 2 3 3 4 6\n", ConsoleSupport.runMain(19, "3 1 3 4 3 2 3 6"));
    }

    @Test
    void shouldPrintEmptyArray() throws Exception {
        assertEquals("\n", ConsoleSupport.runMain(19, "0\n0\n"));
    }
}
