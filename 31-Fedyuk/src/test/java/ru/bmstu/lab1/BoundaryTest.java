package ru.bmstu.lab1;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.task_1.MinMax;
import ru.bmstu.lab1.task_10.LastMultiple;
import ru.bmstu.lab1.task_11.ClosestToAverage;
import ru.bmstu.lab1.task_2.FirstOccurrence;
import ru.bmstu.lab1.task_3.OccurrenceCount;
import ru.bmstu.lab1.task_4.AllIndices;
import ru.bmstu.lab1.task_5.EvenIndexSum;
import ru.bmstu.lab1.task_6.AboveAverageCount;
import ru.bmstu.lab1.task_7.SecondMaximum;
import ru.bmstu.lab1.task_8.SignFilter;
import ru.bmstu.lab1.task_9.SortedCheck;

/** Проверки максимально допустимой длины и арифметики на границах int. */
class BoundaryTest {
    private static final int MAX_VALID_SIZE = 99_999;

    @Test
    void shouldHandleLargeEqualValuesWithoutSumOverflow() {
        int[] values = new int[MAX_VALID_SIZE];
        Arrays.fill(values, Integer.MAX_VALUE);
        assertArrayEquals(new int[] {Integer.MAX_VALUE, Integer.MAX_VALUE}, MinMax.solve(values));
        assertEquals(-1, FirstOccurrence.solve(values, 0));
        assertEquals(MAX_VALID_SIZE, OccurrenceCount.solve(values, Integer.MAX_VALUE));
        assertEquals(0, AboveAverageCount.solve(values));
        assertEquals(Integer.MAX_VALUE, ClosestToAverage.solve(values));
        long evenIndexCount = (MAX_VALID_SIZE + 1L) / 2;
        assertEquals(evenIndexCount * Integer.MAX_VALUE, EvenIndexSum.solve(values));
    }

    @Test
    void shouldReturnEveryMatchingIndexInLargeArray() {
        int[] expected = increasingArray();
        assertArrayEquals(expected, AllIndices.solve(new int[MAX_VALID_SIZE], 0));
    }

    @Test
    void shouldHandleLargeOrderedDistinctArray() {
        int[] values = increasingArray();
        assertEquals(MAX_VALID_SIZE - 2, SecondMaximum.solve(values));
        assertTrue(SortedCheck.solve(values));
        assertEquals(MAX_VALID_SIZE - 1, LastMultiple.solve(values, 1));
    }

    @Test
    void shouldPreserveAllPositiveElementsWhenFilteringLargeArray() {
        int[] values = new int[MAX_VALID_SIZE];
        Arrays.fill(values, 1);
        int[][] actual = SignFilter.solve(values);
        assertArrayEquals(values, actual[0]);
        assertEquals(0, actual[1].length);
    }

    @Test
    void shouldRejectZeroDivisorExplicitly() {
        assertThrows(IllegalArgumentException.class, () -> LastMultiple.solve(new int[] {0, 2}, 0));
    }

    private static int[] increasingArray() {
        int[] values = new int[MAX_VALID_SIZE];
        for (int index = 0; index < values.length; index++) {
            values[index] = index;
        }
        return values;
    }
}
