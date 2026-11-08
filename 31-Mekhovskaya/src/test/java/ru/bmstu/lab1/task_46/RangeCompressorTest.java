package ru.bmstu.lab1.task_46;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.Test;
import ru.bmstu.lab1.common.ConsoleRunner;

/**
 * Тесты задачи 46 «Сжатие диапазона».
 */
class RangeCompressorTest {

    @Test
    void shouldReplaceElementsWithRanksForExampleFromTask() {
        int[] actual = RangeCompressor.compress(new int[] {100, 50, 100, 300});

        assertArrayEquals(new int[] {1, 0, 1, 2}, actual);
    }

    @Test
    void shouldHandleNegativeNumbers() {
        int[] actual = RangeCompressor.compress(new int[] {-5, 10, -5, 0});

        assertArrayEquals(new int[] {0, 2, 0, 1}, actual);
    }

    @Test
    void shouldReturnZerosWhenAllElementsEqual() {
        int[] actual = RangeCompressor.compress(new int[] {7, 7, 7});

        assertArrayEquals(new int[] {0, 0, 0}, actual);
    }

    @Test
    void shouldNotModifyInputArray() {
        int[] array = {30, 10, 20};

        RangeCompressor.compress(array);

        assertArrayEquals(new int[] {30, 10, 20}, array);
    }

    @Test
    void shouldThrowWhenArrayIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> RangeCompressor.compress(new int[0]));
    }

    @Test
    void shouldPrintRanksFromConsoleInput() {
        List<String> actual = ConsoleRunner.execute(RangeCompressor::run, "4\n100 50 100 300");

        assertEquals(List.of("1 0 1 2"), actual);
    }
}
