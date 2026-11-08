package ru.bmstu.lab1.task_2;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет первое вхождение, отсутствие числа и формат ввода-вывода задачи 2. */
class MainTest {

    @Test
    void shouldReturnFirstIndexWithoutChangingArray() {
        int[] numbers = {4, 9, 4, 4};
        int[] expectedNumbers = numbers.clone();

        int actual = Main.findFirstIndex(numbers, 4);

        assertEquals(0, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldFindNumberInMiddle() {
        int[] numbers = {2, 4, 8, 6};

        int actual = Main.findFirstIndex(numbers, 8);

        assertEquals(2, actual);
    }

    @Test
    void shouldFindLastElement() {
        int[] numbers = {2, 4, 8};

        int actual = Main.findFirstIndex(numbers, 8);

        assertEquals(2, actual);
    }

    @Test
    void shouldReturnMinusOneWhenNumberIsAbsent() {
        int[] numbers = {2, 4};

        int actual = Main.findFirstIndex(numbers, 7);

        assertEquals(-1, actual);
    }

    @Test
    void shouldFindNegativeNumber() {
        int[] numbers = {6, -2, 0, -2};

        int actual = Main.findFirstIndex(numbers, -2);

        assertEquals(1, actual);
    }

    @Test
    void shouldFindZero() {
        int[] numbers = {7, 0, 9};

        int actual = Main.findFirstIndex(numbers, 0);

        assertEquals(1, actual);
    }

    @Test
    void shouldHandleIntegerLimitsInSmallestArray() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE};

        int actual = Main.findFirstIndex(numbers, Integer.MAX_VALUE);

        assertEquals(1, actual);
    }

    @Test
    void shouldFindLastElementInLargestAllowedArray() {
        int[] numbers = new int[99_999];
        numbers[numbers.length - 1] = 42;

        int actual = Main.findFirstIndex(numbers, 42);

        assertEquals(99_998, actual);
    }

    @Test
    void shouldReadArrayBeforeTargetAndPrintIndex() {
        String expected = "1" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "4\n9 -2\t7 -2\n-2\n");

        assertEquals(expected, actual);
    }
}
