package ru.bmstu.lab1.task_42;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleSupport;

/** граничные случаи и независимая проверка алгоритма задачи 42 */
class MainTest {
    @Test
    void shouldInsertAtBeginningMiddleAndEnd() {
        int[] values = {1, 3, 5};
        assertArrayEquals(new int[] {0, 1, 3, 5}, Main.insertSorted(values, 0));
        assertArrayEquals(new int[] {1, 3, 4, 5}, Main.insertSorted(values, 4));
        assertArrayEquals(new int[] {1, 3, 5, 6}, Main.insertSorted(values, 6));
        assertArrayEquals(new int[] {1, 3, 5}, values);
    }

    @Test
    void shouldPreserveDuplicatesAndHandleEmptyInput() {
        assertArrayEquals(new int[] {2, 2, 2}, Main.insertSorted(new int[] {2, 2}, 2));
        assertArrayEquals(new int[] {Integer.MIN_VALUE}, Main.insertSorted(new int[0], Integer.MIN_VALUE));
        assertArrayEquals(new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE},
                Main.insertSorted(new int[] {Integer.MIN_VALUE}, Integer.MAX_VALUE));
    }

    @Test
    void shouldMatchSortedCopyOnRandomArrays() {
        Random random = new Random(42);
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] values = random.ints(random.nextInt(50), -20, 21).sorted().toArray();
            int value = random.nextInt(41) - 20;
            int[] expected = Arrays.copyOf(values, values.length + 1);
            expected[values.length] = value;
            Arrays.sort(expected);
            assertArrayEquals(expected, Main.insertSorted(values, value));
        }
    }

    @Test
    void shouldReadAndPrintAnswer() throws Exception {
        assertEquals("1 3 4 5\n", ConsoleSupport.runMain(42, "3 1 3 5 4"));
    }
}
