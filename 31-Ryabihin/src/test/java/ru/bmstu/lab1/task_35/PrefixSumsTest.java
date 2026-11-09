package ru.bmstu.lab1.task_35;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.MainRunner;

class PrefixSumsTest {

    @Test
    void shouldAnswerQueries() {
        PrefixSums sums = new PrefixSums(new int[] {1, 2, 3, 4, 5});
        assertEquals(6, sums.sum(0, 2));
        assertEquals(9, sums.sum(1, 3));
        assertEquals(15, sums.sum(0, 4));
    }

    @Test
    void shouldReturnElementWhenLeftEqualsRight() {
        PrefixSums sums = new PrefixSums(new int[] {7, -3, 9});
        assertEquals(-3, sums.sum(1, 1));
    }

    @Test
    void shouldThrowOnWrongRange() {
        PrefixSums sums = new PrefixSums(new int[] {1, 2, 3});
        assertThrows(IllegalArgumentException.class, () -> sums.sum(-1, 1));
        assertThrows(IllegalArgumentException.class, () -> sums.sum(0, 3));
        assertThrows(IllegalArgumentException.class, () -> sums.sum(2, 1));
    }

    @Test
    void shouldPrintAnswerForEachQuery() {
        String input = "5\n1 2 3 4 5\n3\n0 2\n1 3\n4 4";
        assertEquals("6\n9\n5", MainRunner.run(Main::run, input));
    }
}
