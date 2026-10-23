package ru.bmstu.lab1.task_38;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleSupport;

/** граничные случаи и независимая проверка алгоритма задачи 38 */
class MainTest {
    @Test
    void shouldRotateInPlaceByNormalizedDistance() {
        int[] values = {1, 2, 3, 4, 5};
        Main.rotateLeft(values, 7);
        assertArrayEquals(new int[] {3, 4, 5, 1, 2}, values);
        Main.rotateLeft(values, -2);
        assertArrayEquals(new int[] {1, 2, 3, 4, 5}, values);
    }

    @Test
    void shouldHandleEmptySingletonAndFullTurn() {
        int[] empty = {};
        Main.rotateLeft(empty, 5);
        assertArrayEquals(new int[0], empty);
        int[] single = {7};
        Main.rotateLeft(single, Integer.MIN_VALUE);
        assertArrayEquals(new int[] {7}, single);
        int[] values = {1, 2};
        Main.rotateLeft(values, 0);
        Main.rotateLeft(values, 4);
        assertArrayEquals(new int[] {1, 2}, values);
    }

    @Test
    void shouldMatchIndexMappingOnRandomArrays() {
        Random random = new Random(38);
        for (int attempt = 0; attempt < 200; attempt++) {
            int[] values = random.ints(random.nextInt(50) + 1).toArray();
            int shift = attempt == 0 ? Integer.MIN_VALUE : random.nextInt();
            int distance = Math.floorMod(shift, values.length);
            int[] expected = new int[values.length];
            for (int index = 0; index < values.length; index++) {
                expected[index] = values[(index + distance) % values.length];
            }
            Main.rotateLeft(values, shift);
            assertArrayEquals(expected, values);
        }
    }

    @Test
    void shouldReadAndPrintAnswer() throws Exception {
        assertEquals("3 4 5 1 2\n", ConsoleSupport.runMain(38, "5 1 2 3 4 5 7"));
    }
}
