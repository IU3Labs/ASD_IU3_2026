package ru.bmstu.lab1.task_37;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.bmstu.lab1.ConsoleTestSupport.run;

import org.junit.jupiter.api.Test;

/** Проверки алгоритма и консольного интерфейса задачи 37. */
class MainTest {
    @Test
    void shouldReverseEvenArrayInPlace() {
        int[] numbers = {1, 2, 3, 4};
        Main.reverse(numbers);
        assertArrayEquals(new int[]{4, 3, 2, 1}, numbers);
    }

    @Test
    void shouldReverseOddArrayAndKeepMiddleElement() {
        int[] numbers = {-5, 0, 7, 7, Integer.MIN_VALUE};
        Main.reverse(numbers);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, 7, 7, 0, -5}, numbers);
        Main.reverse(numbers);
        assertArrayEquals(new int[]{-5, 0, 7, 7, Integer.MIN_VALUE}, numbers);
    }

    @Test
    void shouldReverseMinimumAllowedSize() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE};
        Main.reverse(numbers);
        assertArrayEquals(new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE}, numbers);
    }

    @Test
    void shouldReadArrayAndPrintReversedArray() {
        assertEquals("5 4 3 2 1\n", run("5\n1 2 3 4 5\n", () -> Main.main(new String[0])));
    }
}
