package ru.bmstu.lab1.task_46;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

class RankCompressionTest {

    @Test
    void shouldCompressExampleFromStatement() {
        assertArrayEquals(new int[] {1, 0, 1, 2}, RankCompression.compress(new int[] {100, 50, 100, 300}));
    }

    @Test
    void shouldGiveZeroToAllEqualElements() {
        assertArrayEquals(new int[] {0, 0, 0}, RankCompression.compress(new int[] {7, 7, 7}));
    }

    @Test
    void shouldHandleNegativeAndExtremeValues() {
        int[] values = {Integer.MAX_VALUE, -5, Integer.MIN_VALUE, -5};

        assertArrayEquals(new int[] {2, 1, 0, 1}, RankCompression.compress(values));
    }

    @Test
    void shouldNotModifyInputArray() {
        int[] values = {3, 1, 2};

        RankCompression.compress(values);

        assertArrayEquals(new int[] {3, 1, 2}, values);
    }

    @Test
    void shouldPrintRanksFromConsole() {
        assertEquals("2 0 1", ConsoleRunner.run(RankCompression::main, "3\n30 -10 20\n"));
    }
}
