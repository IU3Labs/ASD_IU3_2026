package ru.bmstu.lab1.task_1;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 1 «Максимум и минимум».
 */
class MinMaxFinderTest {

    @Test
    void shouldFindMinAndMaxInUnsortedArray() {
        MinMax actual = MinMaxFinder.findMinMax(new int[] {4, -7, 15, 0, 3});

        assertEquals(new MinMax(-7, 15), actual);
    }

    @Test
    void shouldReturnSameValueWhenAllElementsEqual() {
        MinMax actual = MinMaxFinder.findMinMax(new int[] {5, 5, 5});

        assertEquals(new MinMax(5, 5), actual);
    }

    @Test
    void shouldHandleExtremeIntValues() {
        MinMax actual = MinMaxFinder.findMinMax(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE});

        assertEquals(new MinMax(Integer.MIN_VALUE, Integer.MAX_VALUE), actual);
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> MinMaxFinder.findMinMax(new int[0]));
    }

    @Test
    void shouldPrintMinAndMaxFromConsoleInput() {
        List<String> actual = ConsoleRunner.execute(MinMaxFinder::run, "5\n3 9 -1 4 2");

        assertEquals(List.of("-1 9"), actual);
    }
}
