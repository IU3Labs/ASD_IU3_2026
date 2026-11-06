package ru.bmstu.lab1.task_35;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class PrefixSumsTest {

    private final PrefixSums sums = new PrefixSums(new int[] {3, -1, 4, 1, 5});

    @Test
    void shouldReturnSingleElementForOneCellRange() {
        assertEquals(4, sums.sum(2, 2));
    }

    @Test
    void shouldReturnTotalForWholeArray() {
        assertEquals(12, sums.sum(0, 4));
    }

    @Test
    void shouldReturnSumForInnerRange() {
        assertEquals(4, sums.sum(1, 3));
    }

    @Test
    void shouldRejectInvalidRanges() {
        assertThrows(IndexOutOfBoundsException.class, () -> sums.sum(-1, 2));
        assertThrows(IndexOutOfBoundsException.class, () -> sums.sum(0, 5));
        assertThrows(IndexOutOfBoundsException.class, () -> sums.sum(3, 2));
    }

    @Test
    void shouldNotOverflowOnLargeValues() {
        int max = Integer.MAX_VALUE;

        assertEquals(2L * max, new PrefixSums(new int[] {max, max}).sum(0, 1));
    }

    @Test
    void shouldAnswerQueriesFromConsole() {
        String input = "4\n1 2 3 4\n3\n0 3\n1 2\n3 3\n";

        assertEquals("10\n5\n4", ConsoleRunner.run(PrefixSums::main, input));
    }
}
