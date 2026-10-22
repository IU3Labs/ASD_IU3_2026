package ru.bmstu.lab1.task_5;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.ConsoleTestSupport;

/** Проверяет выбор чётных индексов, большие суммы и ввод-вывод задачи 5. */
class MainTest {

    @Test
    void shouldSumEvenIndicesRatherThanEvenValues() {
        int[] numbers = {3, 100, 5, 200};
        int[] expectedNumbers = numbers.clone();

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(8L, actual);
        assertArrayEquals(expectedNumbers, numbers);
    }

    @Test
    void shouldIncludeLastElementWhenLengthIsOdd() {
        int[] numbers = {1, 2, 3, 4, 5};

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(9L, actual);
    }

    @Test
    void shouldUseOnlyFirstElementForTwoNumbers() {
        int[] numbers = {-7, 10};

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(-7L, actual);
    }

    @Test
    void shouldReturnZeroWhenSelectedElementsAreZero() {
        int[] numbers = {0, 8, 0, 9};

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(0L, actual);
    }

    @Test
    void shouldAddPositiveAndNegativeSelectedElements() {
        int[] numbers = {-8, 100, 3, 100, 2};

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(-3L, actual);
    }

    @Test
    void shouldAvoidPositiveIntegerOverflow() {
        int[] numbers = {Integer.MAX_VALUE, 0, Integer.MAX_VALUE};

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(4_294_967_294L, actual);
    }

    @Test
    void shouldAvoidNegativeIntegerOverflow() {
        int[] numbers = {Integer.MIN_VALUE, 0, Integer.MIN_VALUE};

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(-4_294_967_296L, actual);
    }

    @Test
    void shouldHandleLargestAllowedArray() {
        int[] numbers = new int[99_999];
        Arrays.fill(numbers, Integer.MAX_VALUE);
        long expected = 50_000L * Integer.MAX_VALUE;

        long actual = Main.sumEvenIndices(numbers);

        assertEquals(expected, actual);
    }

    @Test
    void shouldReadArrayAndPrintSum() {
        String expected = "9" + System.lineSeparator();

        String actual = ConsoleTestSupport.runProgram(Main::solve, "5\n1 2 3 4 5\n");

        assertEquals(expected, actual);
    }
}
