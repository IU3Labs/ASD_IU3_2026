package ru.bmstu.lab1.task_12;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class PairWithSumTest {

    @Test
    void shouldPreferSmallestFirstIndex() {
        // пары: (0,3) 1+5 и (1,2) 2+4 — выбирается i = 0
        assertArrayEquals(new int[] {0, 3}, PairWithSum.solve(new int[] {1, 2, 4, 5}, 6));
    }

    @Test
    void shouldPreferSmallestSecondIndexForSameFirst() {
        assertArrayEquals(new int[] {0, 1}, PairWithSum.solve(new int[] {3, 3, 3}, 6));
    }

    @Test
    void shouldNotPairElementWithItself() {
        assertArrayEquals(new int[] {-1, -1}, PairWithSum.solve(new int[] {3, 1}, 6));
    }

    @Test
    void shouldSkipFirstIndexThatHasNoPair() {
        // у i = 0 дополнения 7 нет, поэтому ответ начинается с i = 1 (дополнение 5 стоит правее)
        assertArrayEquals(new int[] {1, 3}, PairWithSum.solve(new int[] {1, 3, 9, 5}, 8));
    }

    @Test
    void shouldHandleSumOutsideIntRange() {
        int max = Integer.MAX_VALUE;

        assertArrayEquals(new int[] {0, 1}, PairWithSum.solve(new int[] {max, max}, 2L * max));
    }

    @Test
    void shouldPrintPairFromConsole() {
        assertEquals("-1 -1", ConsoleRunner.run(PairWithSum::main, "3\n1 2 3\n100\n"));
        assertEquals("0 2", ConsoleRunner.run(PairWithSum::main, "3\n-1 2 5\n4\n"));
    }
}
