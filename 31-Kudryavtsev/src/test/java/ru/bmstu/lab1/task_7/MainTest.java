package ru.bmstu.lab1.task_7;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет второй максимум при разном порядке чисел и на границах int. */
class MainTest {

    @Test
    void shouldUpdateSecondMaximumWithoutChangingArray() {
        int[] numbers = {3, 9, 1, 7, 5};
        int[] expectedNumbers = numbers.clone();

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(7, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldMovePreviousMaximumToSecondPlace() {
        int[] numbers = {1, 3, 5, 7};

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(5, actual);
    }

    @Test
    void shouldKeepInitialMaximaInDecreasingArray() {
        int[] numbers = {9, 7, 3, 1};

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(7, actual);
    }

    @Test
    void shouldFindSecondMaximumAmongNegativeNumbers() {
        int[] numbers = {-9, -2, -5};

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(-5, actual);
    }

    @Test
    void shouldAllowZeroAsSecondMaximum() {
        int[] numbers = {-3, 0, 4, -1};

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(0, actual);
    }

    @Test
    void shouldHandleTwoElementsAndIntegerLimits() {
        int[] numbers = {Integer.MIN_VALUE, Integer.MAX_VALUE};

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(Integer.MIN_VALUE, actual);
    }

    @Test
    void shouldHandleTwoElementsInDecreasingOrder() {
        int[] numbers = {8, -4};

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(-4, actual);
    }

    @Test
    void shouldHandleLargestAllowedArrayOfDistinctNumbers() {
        int[] numbers = new int[99_999];
        for (int index = 0; index < numbers.length; index++) {
            numbers[index] = index;
        }

        int actual = Main.findSecondMaximum(numbers);

        assertEquals(99_997, actual);
    }

    @Test
    void shouldReadArrayAndPrintSecondMaximum() {
        String expected = "7" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "5\n3 9 1 7 5\n");

        assertEquals(expected, actual);
    }
}
